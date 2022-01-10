package com.mny.mojito.widget.pkg.presentation.motto

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.lxj.xpopup.impl.FullScreenPopupView
import com.mny.mojito.entension.afterTextChanged
import com.mny.mojito.entension.setOnDebouncedClickListener
import com.mny.mojito.widget.pkg.R

/**
 * MottoEditView
 * @author caicai
 * Created on 2021-09-07 10:44
 * Desc:
 */
class MottoEditView(context: Context, val mViewModel: MottoViewModel) :
    FullScreenPopupView(context) {

    override fun getImplLayoutId(): Int = R.layout.layout_motto_edit

    override fun onCreate() {
        super.onCreate()
        findViewById<View>(R.id.cl_container).setOnDebouncedClickListener {
            dismiss()
        }
        val etContent = findViewById<AppCompatEditText>(R.id.et_content)
      val content =  mViewModel.editMotto.value?.content ?: ""
        etContent.setText(content)
        etContent.setSelection(content.length)
        etContent.afterTextChanged {
            mViewModel.updateMotto(it)
        }
    }

}