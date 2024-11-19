package com.petproject.workflow.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.petproject.workflow.R
import com.petproject.workflow.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setValueToViews()
        return binding.root
    }

    private fun setValueToViews() {
        with(binding) {
            with(vacationsItem) {
                itemLogo.setImageResource(R.drawable.ic_vacation)
                itemTitle.text = getString(R.string.vacation)
                itemText.text = "10-22 декабря"
                itemStatus.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.circle_green)
                itemStatusText.text = "Согласованно"
            }
            with(businessTripsItem) {
                itemLogo.setImageResource(R.drawable.ic_business_trip)
                itemTitle.text = getString(R.string.business_trips)
                itemText.text = "17-22 января, г.Саратов"
                itemStatus.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.circle_green)
                itemStatusText.text = "Согласованно"
            }
            with(approvalsItem) {
                itemLogo.setImageResource(R.drawable.ic_approval)
                itemTitle.text = getString(R.string.on_your_approval)
                itemText.text = "Новый монитор для Саши Иванова"
                itemStatus.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.circle_red)
                itemStatusText.text = "Не согласованно"
            }
            with(tasksItem) {
                itemLogo.setImageResource(R.drawable.ic_task)
                itemTitle.text = getString(R.string.tasks)
                itemText.text = "Ваши цели и индивидуальный план"
                itemStatus.visibility = View.INVISIBLE
                itemStatusText.visibility = View.INVISIBLE
            }
            with(mentoringItem) {
                itemLogo.setImageResource(R.drawable.ic_mentoring)
                itemTitle.text = getString(R.string.mentoring)
                itemText.text = "Делитесь опытом с другими сотрудниками"
                itemStatus.visibility = View.INVISIBLE
                itemStatusText.visibility = View.INVISIBLE
            }
            with(documentsItem) {
                itemLogo.setImageResource(R.drawable.ic_document)
                itemTitle.text = getString(R.string.documents)
                itemText.text = "Необходиммо ознакомится с 8 документами"
                itemStatus.visibility = View.INVISIBLE
                itemStatusText.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}