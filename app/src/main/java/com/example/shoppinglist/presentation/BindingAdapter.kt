package com.example.shoppinglist.presentation

import androidx.databinding.BindingAdapter
import com.example.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorInputName")
fun bindErrorInputName(TextInputLayout: TextInputLayout, isError: Boolean){
    val message = if (isError) {
        TextInputLayout.context.getString(R.string.error_input_name)
    } else {
        null
    }
    TextInputLayout.error = message
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(TextInputLayout: TextInputLayout, isError: Boolean){
    val message = if (isError) {
        TextInputLayout.context.getString(R.string.error_input_count)
    } else {
        null
    }
    TextInputLayout.error = message
}

