package com.mny.mojito.flow

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.mny.mojito.base.BaseVBActivity
import com.mny.mojito.entension.observe
import com.mny.mojito.flow.databinding.ActivityFlowDemoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlowDemoActivity : BaseVBActivity<ActivityFlowDemoBinding>() {
    companion object {
        private const val TAG = "FlowDemoActivity"
    }

    private val mContentData = MutableLiveData<String>("初始控制")
    private val mContentFlow = MutableStateFlow<String>("")

    private val mBackpressureLiveData = MutableLiveData(0)
    private val mBackpressureFlow = MutableStateFlow(0)
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        with(mBinding) {
            btnLiveDataThreadUpdate.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val msg = "LiveData 在 ${Thread.currentThread().name} 中更新数据"
                    try {
                        mContentData.value = msg
                    } catch (e: Exception) {
                        Log.e(TAG, "initView: ", e)
                    }
                    delay(2000)
                    mContentData.postValue(msg)
                }
            }
            btnFlowThreadUpdate.setOnClickListener {
                lifecycleScope.launch(Dispatchers.Main) {
                    val msg = "Flow 在 ${Thread.currentThread().name} 中更新数据"
                    mContentFlow.value = msg
                }
            }

            btnLiveDataThreadChange.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    mContentData.postValue("Post 以后的数据")
                    withContext(Dispatchers.Main) {
                        Log.d(TAG, "initView: ${mContentData.value}")
                    }
                }
            }

            btnFlowThreadChange.setOnClickListener {
                lifecycleScope.launch(Dispatchers.Main) {
                    flow<Int> {
                        repeat(10) {
                            Log.d(TAG, "initView: Flow 在 ${Thread.currentThread().name} 中初始化数据 $it")
                            emit(it)
                        }
                    }
                        .flowOn(Dispatchers.IO)
                        .map {
                            val result = it * 2
                            Log.d(
                                TAG,
                                "initView: Flow 在 ${Thread.currentThread().name} 中转换数据 $result"
                            )
                            result
                        }
                        .flowOn(Dispatchers.Main)
                        .collect {
                            updateContent("$it")
                        }
                }
            }

            btnLiveDataBackPressure.setOnClickListener {
                mInitSum = 0
                lifecycleScope.launch(Dispatchers.IO) {
                    repeat(10) {
                        val msg =
                            "LiveData 在 ${Thread.currentThread().name} 中更新数据 $it"
                        Log.d(TAG, "initView: LiveData 背压 $msg")
                        delay(1)
                        mBackpressureLiveData.postValue(it)
                    }
                }

            }
            btnFlowBackPressure.setOnClickListener {
                mInitSum = 0
                lifecycleScope.launch(Dispatchers.IO) {
                    repeat(10) {
                        val msg =
                            "Flow 在 ${Thread.currentThread().name} 中更新数据 $it"
                        Log.d(TAG, "initView: Flow 背压 $msg")
                        delay(100)
                        mBackpressureFlow.value = it
                    }
                }
            }

            btnFlowUsefulOp.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                }
            }
        }
    }

    private var mInitSum = 0
    override fun initObserver() {
        super.initObserver()
        observe(mContentData) {
            updateContent(it)
        }

        observe(mBackpressureLiveData) {
            Thread.sleep(500)
            mInitSum += it
            updateContent("$mInitSum")
        }

        lifecycleScope.launch(Dispatchers.Main) {
            mContentFlow
                .collect {
                    delay(500)
                    updateContent(it)
                }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            mBackpressureFlow
                .buffer()
                .collect {
                    delay(500)
                    mInitSum += it
                    updateContent("$mInitSum")
                }
        }
    }

    private fun updateContent(it: String) {
        Log.d(TAG, "updateContent: $it")
        mBinding.tvContent.text = "$it"
    }
}