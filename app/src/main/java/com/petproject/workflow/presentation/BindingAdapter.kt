package com.petproject.workflow.presentation

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.petproject.workflow.R
import com.petproject.workflow.domain.entities.TaskStatus

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

@BindingAdapter("taskStatusColor")
fun bindTaskStatusColor(view: View, status: TaskStatus) {
    val backgroundId: Int = when(status) {
        TaskStatus.NEW -> R.drawable.circle_green
        TaskStatus.IN_PROGRESS -> R.drawable.circle_blue
        TaskStatus.COMPLETED -> R.drawable.circle_green
        TaskStatus.FAILED -> R.drawable.circle_red
        TaskStatus.ON_APPROVAL -> R.drawable.circle_yellow
    }
    val drawable = ContextCompat.getDrawable(view.context, backgroundId)
    view.background = drawable
}

@BindingAdapter("taskStatusText")
fun bindTaskStatusText(textView: TextView, status: TaskStatus) {
    with(textView) {
        text = when(status) {
            TaskStatus.NEW -> context.resources.getString(R.string.new_task)
            TaskStatus.IN_PROGRESS -> context.resources.getString(R.string.in_progress)
            TaskStatus.COMPLETED -> context.resources.getString(R.string.completed)
            TaskStatus.FAILED -> context.resources.getString(R.string.faild)
            TaskStatus.ON_APPROVAL -> context.resources.getString(R.string.on_approval)
        }
    }
}