package com.petproject.workflow.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.petproject.workflow.R
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.domain.entities.Role
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import java.time.LocalDate
import java.time.LocalDateTime


@BindingAdapter("safeText")
fun bindSafeText(
    editText: TextInputEditText,
    liveData: MutableLiveData<String>
) {
    var previousValue = liveData.value

    editText.doAfterTextChanged { text ->
        if (text?.toString() != previousValue) {
            liveData.value = text?.toString()
            previousValue = text?.toString()
        }
    }

//    liveData?.observe((editText.context as? Fragment)?.viewLifecycleOwner ?: return) { value ->
//        if (editText.text?.toString() != value) {
//            editText.setText(value)
//            previousValue = value
//        }
//    }
}

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

@BindingAdapter("errorInputText")
fun bindErrorInputText(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_text)
    } else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("onlyDirectorAndHrView")
fun bindOnlyDirectorAndHrView(view: View, role: Role) {
    if (role != Role.ROLE_DIRECTOR && role != Role.ROLE_HR) {
        view.visibility = View.GONE
    }
}

@BindingAdapter("exceptDirectorView")
fun bindExceptDirectorView(view: View, role: Role) {
    if (role == Role.ROLE_DIRECTOR) {
        view.visibility = View.GONE
    }
}

@BindingAdapter("onlyIndustrialSecurityView")
fun bindOnlyIndustrialSecurityView(view: View, role: Role) {
    if (role != Role.ROLE_INDUSTRIAL_SECURITY) {
        view.visibility = View.GONE
    }
}

@BindingAdapter("onlyInspectorView")
fun bindOnlyInspectorView(view: View, role: Role) {
    if (role == Role.ROLE_DRIVER) {
        view.visibility = View.GONE
    }
}

@BindingAdapter("onlyDriverView")
fun bindOnlyDriverView(view: View, role: Role) {
    if (role != Role.ROLE_DRIVER) {
        view.visibility = View.GONE
    }
}

@BindingAdapter("onlyOperatorView")
fun bindOnlyOperatorView(view: View, role: Role) {
    if (role != Role.ROLE_OPERATOR) {
        view.visibility = View.GONE
    }
}

@BindingAdapter("onlyLogistView")
fun bindOnlyLogistView(view: View, role: Role) {
    if (role != Role.ROLE_LOGIST) {
        view.visibility = View.GONE
    }
}

@BindingAdapter("taskStatusColor")
fun bindTaskStatusColor(view: View, status: TaskStatus?) {
    status?.let {
        val backgroundId: Int = when (it) {
            TaskStatus.NEW -> R.drawable.circle_green
            TaskStatus.IN_PROGRESS -> R.drawable.circle_blue
            TaskStatus.COMPLETED -> R.drawable.circle_green
            TaskStatus.FAILED -> R.drawable.circle_red
            TaskStatus.ON_APPROVAL -> R.drawable.circle_yellow
            TaskStatus.NOT_APPROVAL -> R.drawable.circle_orange
            TaskStatus.CANCELED -> R.drawable.circle_red
        }
        val drawable = ContextCompat.getDrawable(view.context, backgroundId)
        view.background = drawable
    }

}

@BindingAdapter("taskStatusText")
fun bindTaskStatusText(textView: TextView, status: TaskStatus?) {
    status?.let {
        with(textView) {
            text = when (it) {
                TaskStatus.NEW -> context.resources.getString(R.string.new_task)
                TaskStatus.IN_PROGRESS -> context.resources.getString(R.string.in_progress)
                TaskStatus.COMPLETED -> context.resources.getString(R.string.completed)
                TaskStatus.FAILED -> context.resources.getString(R.string.faild)
                TaskStatus.ON_APPROVAL -> context.resources.getString(R.string.on_approval)
                TaskStatus.NOT_APPROVAL -> context.resources.getString(R.string.not_approval)
                TaskStatus.CANCELED -> context.resources.getString(R.string.canceled)
            }
        }
    }

}

@BindingAdapter("localDateTime")
fun bindLocalDateTime(textView: TextView, date: LocalDateTime?) {
    date?.let {
        textView.text = it.toString()
    }
}

@BindingAdapter("localDate")
fun bindLocalDate(textView: TextView, date: LocalDate?) {
    date?.let {
        textView.text = it.toString()
    }
}

@BindingAdapter("taskDestination")
fun bindTaskDestination(textView: TextView, destination: String?) {
    if (destination == null) {
        textView.visibility = View.GONE
    } else {
        textView.text = destination
    }
}

@BindingAdapter("taskDestination")
fun bindTaskDestination(imageView: ImageView, destination: String?) {
    if (destination == null) {
        imageView.visibility = View.GONE
    }
}

@BindingAdapter("countTaskComments")
fun bindTaskPriority(textView: TextView, count: Int) {
    val resId = if (count in 11..19) {
        R.string.comments_count_plu
    } else {
        val temp = count % 10
        when (temp) {
            1 -> R.string.comments_count_nom
            in 2..4 -> R.string.comments_count_gen
            else -> R.string.comments_count_plu
        }
    }
    textView.text = String.format(
        textView.context.resources.getString(resId),
        count
    )
}

@BindingAdapter("taskPriority")
fun bindTaskPriority(textView: TextView, priority: TaskPriority?) {
    priority?.let {
        when (it) {
            TaskPriority.COMMON -> {
                textView.text = textView.context.resources.getString(R.string.common)
                textView.setTextColor(
                    ContextCompat.getColor(textView.context, R.color.green)
                )
            }

            TaskPriority.URGENT -> {
                textView.text = textView.context.resources.getString(R.string.urgent)
                textView.setTextColor(
                    ContextCompat.getColor(textView.context, R.color.red)
                )
            }
        }
    }

}

@BindingAdapter("taskPriority")
fun bindTaskPriority(imageView: ImageView, priority: TaskPriority?) {
    priority?.let {
        when (it) {
            TaskPriority.COMMON -> {
                imageView.setImageResource(R.drawable.ic_bolt_green)
            }

            TaskPriority.URGENT -> {
                imageView.setImageResource(R.drawable.ic_bolt_red)
            }
        }
    }

}

@BindingAdapter("absenceType")
fun bindAbsenceType(textView: TextView, type: AbsenceType) {
    when (type) {
        AbsenceType.VACATION -> textView.text =
            textView.context.resources.getString(R.string.vacation)

        AbsenceType.BUSINESS_TRIP -> textView.text =
            textView.context.resources.getString(R.string.business_trip)

        AbsenceType.SICK_LEAVE -> textView.text =
            textView.context.resources.getString(R.string.sick_leave)

        AbsenceType.DAY_OFF -> textView.text =
            textView.context.resources.getString(R.string.day_off)
    }
}
