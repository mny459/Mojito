package com.mny.widget.ktx

import android.content.Context
import android.content.res.Resources.Theme
import android.content.res.TypedArray
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import java.lang.Exception

/**
 * ViewHelper
 * Desc:
 */
object ViewHelper {
    private var sTmpValue: TypedValue? = null
    fun getAttrDimen(context: Context, attrRes: Int): Int {
        if (sTmpValue == null) {
            sTmpValue = TypedValue()
        }
        val temValue = sTmpValue!!
        return if (!context.theme.resolveAttribute(attrRes, sTmpValue, true)) 0
        else TypedValue.complexToDimensionPixelSize(temValue.data, getDisplayMetrics(context))
    }

    fun getAttrDrawable(context: Context, attr: Int): Drawable? {
        return getAttrDrawable(context, context.theme, attr)
    }

    fun getAttrDrawable(context: Context, theme: Theme, attr: Int): Drawable? {
        if (attr == 0) {
            return null
        }
        if (sTmpValue == null) {
            sTmpValue = TypedValue()
        }
        val tmpValue = sTmpValue!!
        if (!theme.resolveAttribute(attr, tmpValue, true)) {
            return null
        }
        if (tmpValue.type >= TypedValue.TYPE_FIRST_COLOR_INT
            && tmpValue.type <= TypedValue.TYPE_LAST_COLOR_INT
        ) {
            return ColorDrawable(tmpValue.data)
        }
        if (tmpValue.type == TypedValue.TYPE_ATTRIBUTE) {
            return getAttrDrawable(
                context,
                theme,
                tmpValue.data
            )
        }
        return if (tmpValue.resourceId != 0) {
            getVectorDrawable(
                context,
                tmpValue.resourceId
            )
        } else null
    }

    fun getAttrDrawable(context: Context, typedArray: TypedArray, index: Int): Drawable? {
        val value = typedArray.peekValue(index)
        if (value != null) {
            if (value.type != TypedValue.TYPE_ATTRIBUTE && value.resourceId != 0) {
                return getVectorDrawable(context, value.resourceId)
            }
        }
        return null
    }

    /////////////// VectorDrawable /////////////////////
    fun getVectorDrawable(context: Context, @DrawableRes resVector: Int): Drawable? {
        return try {
            AppCompatResources.getDrawable(context, resVector)
        } catch (e: Exception) {
            null
        }
    }


    fun getDisplayMetrics(context: Context): DisplayMetrics = context.resources.displayMetrics
}