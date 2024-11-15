package com.petproject.workflow.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.petproject.workflow.R

@BindingAdapter("errorInputEmail")
fun bindErrorInputName(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_email)
    } else {
        null
    }
    textInputLayout.error = message
}


@BindingAdapter("errorInputPassword")
fun bindErrorInputCount(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_password)
    } else {
        null
    }
    textInputLayout.error = message
}