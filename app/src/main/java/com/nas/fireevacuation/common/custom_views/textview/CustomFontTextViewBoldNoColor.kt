package com.nas.fireevacuation.common.custom_views.textview

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

class CustomFontTextViewBoldNoColor : androidx.appcompat.widget.AppCompatTextView{
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        // ...
        val type= Typeface.createFromAsset(context.assets,"fonts/SourceSansPro-Bold.otf")
        this.setTypeface(type)

    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        val type =
            Typeface.createFromAsset(context.assets, "fonts/SourceSansPro-Bold.otf")
        this.setTypeface(type)

    }
}