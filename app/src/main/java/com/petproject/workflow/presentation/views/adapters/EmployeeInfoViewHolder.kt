package com.petproject.workflow.presentation.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ItemEmployeeInfoBinding
import com.petproject.workflow.domain.entities.Employee

class EmployeeInfoViewHolder(
    val binding: ItemEmployeeInfoBinding,
    private val requestManager: RequestManager
) : RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun inflateFrom(viewGroup: ViewGroup, requestManager: RequestManager): EmployeeInfoViewHolder {
            val inflater = LayoutInflater.from(viewGroup.context)
            val binding = ItemEmployeeInfoBinding.inflate(inflater, viewGroup, false)
            return EmployeeInfoViewHolder(binding, requestManager)
        }
    }

    fun bind(
        employee: Employee,
        onEmployeeClick: (Employee) -> Unit
    ) {
        binding.employee = employee
        binding.root.setOnClickListener {
            onEmployeeClick(employee)
        }
        setEmployeePhoto(employee)
    }

    private fun setEmployeePhoto(employee: Employee) {
        if (employee.photoUrl != null) {
            requestManager
                .load(employee.photoUrl)
                .into(binding.photoImageView)
        } else {
            binding.photoImageView.setImageResource(R.drawable.ic_person)
        }
    }
}