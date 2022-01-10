package com.mojito.base

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.LogUtils
import com.mny.mojito.base.BaseVBActivity

abstract class BaseToolbarActivity<VB : ViewBinding> : BaseVBActivity<VB>() {

    fun initToolbar(toolbar: Toolbar, title: String = "") {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            // 添加默认的返回图标
            setDisplayHomeAsUpEnabled(true)
            // 设置返回键可用
            setHomeButtonEnabled(true)
            if (title.isNotEmpty()) {
                LogUtils.d("${this.javaClass.simpleName} - initToolbar title: $title")
                // 一个小坑：标题需要使用 supportActionBar 进行设置
                // 而不能使用 toolbar
                this.title = title
            }
        }
    }

    /**
     * Toolbar 返回键 finish Activity
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}