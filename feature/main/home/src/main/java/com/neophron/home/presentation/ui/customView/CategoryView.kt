package com.neophron.home.presentation.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.neophron.home.R
import com.neophron.home.databinding.CategotyItemBinding


class CategoryView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {


    private val binding by lazy {
        LayoutInflater
            .from(context)
            .inflate(R.layout.categoty_item, this, true)
        CategotyItemBinding.bind(this)
    }

    init {
        setupLayout()
        setupUi(attrs, defStyleAttr, defStyleRes)
    }

    private fun setupLayout() {
        gravity = Gravity.CENTER
        orientation = VERTICAL

    }

    private fun setupUi(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CategoryView,
            defStyleAttr,
            defStyleRes
        )

        binding.apply {
            val fetchedTitle = typedArray.getString(R.styleable.CategoryView_title)
            title.text = fetchedTitle
            val fetchedIcon = typedArray.getDrawable(R.styleable.CategoryView_icon)
            icon.setImageDrawable(fetchedIcon)
        }
        typedArray.recycle()
    }

}