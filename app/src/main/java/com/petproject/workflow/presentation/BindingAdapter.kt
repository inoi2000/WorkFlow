package com.petproject.workflow.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.petproject.workflow.R
import com.petproject.workflow.domain.entities.Absence
import com.petproject.workflow.domain.entities.AbsenceType
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import java.time.LocalDate

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
    val backgroundId: Int = when (status) {
        TaskStatus.NEW -> R.drawable.circle_green
        TaskStatus.IN_PROGRESS -> R.drawable.circle_blue
        TaskStatus.COMPLETED -> R.drawable.circle_green
        TaskStatus.FAILED -> R.drawable.circle_red
        TaskStatus.ON_APPROVAL -> R.drawable.circle_yellow
        TaskStatus.NOT_APPROVAL -> R.drawable.circle_orange
    }
    val drawable = ContextCompat.getDrawable(view.context, backgroundId)
    view.background = drawable
}

@BindingAdapter("taskStatusText")
fun bindTaskStatusText(textView: TextView, status: TaskStatus) {
    with(textView) {
        text = when (status) {
            TaskStatus.NEW -> context.resources.getString(R.string.new_task)
            TaskStatus.IN_PROGRESS -> context.resources.getString(R.string.in_progress)
            TaskStatus.COMPLETED -> context.resources.getString(R.string.completed)
            TaskStatus.FAILED -> context.resources.getString(R.string.faild)
            TaskStatus.ON_APPROVAL -> context.resources.getString(R.string.on_approval)
            TaskStatus.NOT_APPROVAL -> context.resources.getString(R.string.not_approval)
        }
    }
}

@BindingAdapter("taskDeadline")
fun bindTaskDeadline(textView: TextView, date: LocalDate) {
    textView.text = date.toString()
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
        val temp = count % 10;
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
fun bindTaskPriority(textView: TextView, priority: TaskPriority) {
    when (priority) {
        TaskPriority.COMMON -> {
            textView.text = textView.context.resources.getString(R.string.common)
            textView.setTextColor(textView.context.resources.getColor(R.color.green))
        }

        TaskPriority.URGENT -> {
            textView.text = textView.context.resources.getString(R.string.urgent)
            textView.setTextColor(textView.context.resources.getColor(R.color.red))
        }
    }

}

@BindingAdapter("taskPriority")
fun bindTaskPriority(imageView: ImageView, priority: TaskPriority) {
    when (priority) {
        TaskPriority.COMMON -> {
            imageView.setImageResource(R.drawable.ic_bolt_green)
        }

        TaskPriority.URGENT -> {
            imageView.setImageResource(R.drawable.ic_bolt_red)
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

@BindingAdapter("absenceDuration")
fun bindAbsenceDuration(textView: TextView, absence: Absence) {
    with(textView.context) {
        val template = resources.getString(R.string.from_to_template)
        textView.text = String.format(
            template,
            absence.start.toString(),
            absence.end.toString()
        )
    }
}
