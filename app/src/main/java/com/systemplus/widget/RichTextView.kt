package com.systemplus.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.systemplus.R

class RichTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        init(context, attrs)
    }

    private var typefaceType = 0 // 0 - none, 1 - regular, 2 - bold

    private fun init(context: Context, attrs: AttributeSet?) {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.RichTextView, 0, 0)

        try {
            typefaceType = attrs?.let {
                array.getInteger(R.styleable.RichTextView_font_type, 1)
            } ?: 1
        } finally {
            array.recycle()
        }

        var fontPath = ""
        when (typefaceType) {
            1 -> fontPath = "OpenSans-Regular.ttf"
            2 -> fontPath = "OpenSans-Bold.ttf"
        }

        val typeFace = Typeface.createFromAsset(getContext().assets, "fonts/$fontPath")
        typeface = typeFace
    }
}