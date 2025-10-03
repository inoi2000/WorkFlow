package com.petproject.workflow.presentation.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.R
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateTaskDoneBinding
import com.petproject.workflow.presentation.viewmodels.CreateTaskDoneViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateTaskDoneFragment : Fragment() {
    private var _binding: FragmentCreateTaskDoneBinding? = null
    private val binding: FragmentCreateTaskDoneBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val args by navArgs<CreateTaskDoneFragmentArgs>()
    private lateinit var viewModel: CreateTaskDoneViewModel

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentCreateTaskDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViews()
        setupObservers()
        createTask()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[CreateTaskDoneViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.setTaskData(args.task)
    }

    private fun setupViews() {
        setupSuccessAnimation()
        setupButtons()
    }

    private fun setupSuccessAnimation() {
        // Задержка перед началом анимации для лучшего UX
        binding.successIcon.postDelayed({
            playSuccessAnimation()
        }, 300)
    }

    private fun playSuccessAnimation() {
        val scaleX = ObjectAnimator.ofFloat(binding.successIcon, View.SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(binding.successIcon, View.SCALE_Y, 0f, 1f)
        val alpha = ObjectAnimator.ofFloat(binding.successIcon, View.ALPHA, 0f, 1f)

        val circleScaleX = ObjectAnimator.ofFloat(binding.circleBackground, View.SCALE_X, 0f, 1f)
        val circleScaleY = ObjectAnimator.ofFloat(binding.circleBackground, View.SCALE_Y, 0f, 1f)

        val animatorSet = AnimatorSet().apply {
            playTogether(scaleX, scaleY, alpha, circleScaleX, circleScaleY)
            duration = 600
            interpolator = AccelerateDecelerateInterpolator()
        }

        animatorSet.start()

        // Анимация появления текста
        val titleAnimation = AnimationUtils.loadAnimation(requireContext(), android.R.anim.fade_in)
        titleAnimation.startOffset = 400
        binding.successTitleTextView.startAnimation(titleAnimation)

        val messageAnimation = AnimationUtils.loadAnimation(requireContext(), android.R.anim.fade_in)
        messageAnimation.startOffset = 600
        binding.successMessageTextView.startAnimation(messageAnimation)
    }

    private fun setupButtons() {
        binding.createAnotherTaskButton.setOnClickListener {
            navigateToCreateNewTask()
        }

        binding.goToTasksButton.setOnClickListener {
            navigateToTasksList()
        }
    }

    private fun setupObservers() {
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoadingState()
            } else {
                showSuccessState()
            }
        }

        viewModel.taskCreationResult.observe(viewLifecycleOwner) { success ->
            if (!success) {
                showErrorState()
            }
        }

        viewModel.navigateToTasks.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                navigateToTasksList()
            }
        }
    }

    private fun createTask() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.createTask(args.task)
        }
    }

    private fun showLoadingState() {
        binding.loadingState.visibility = View.VISIBLE
        binding.circleBackground.visibility = View.INVISIBLE
        binding.successIcon.visibility = View.INVISIBLE
        binding.successTitleTextView.visibility = View.INVISIBLE
        binding.successMessageTextView.visibility = View.INVISIBLE
        binding.createAnotherTaskButton.visibility = View.GONE
        binding.goToTasksButton.visibility = View.GONE
    }

    private fun showSuccessState() {
        binding.loadingState.visibility = View.GONE
        binding.circleBackground.visibility = View.VISIBLE
        binding.successIcon.visibility = View.VISIBLE
        binding.successTitleTextView.visibility = View.VISIBLE
        binding.successMessageTextView.visibility = View.VISIBLE
        binding.createAnotherTaskButton.visibility = View.VISIBLE
        binding.goToTasksButton.visibility = View.VISIBLE
    }

    private fun showErrorState() {
        binding.loadingState.visibility = View.GONE
        binding.successIcon.setImageResource(android.R.drawable.ic_dialog_alert)
        binding.successIcon.setColorFilter(requireContext().getColor(android.R.color.holo_red_dark))
        binding.successTitleTextView.text = "Ошибка создания задачи"
        binding.successTitleTextView.setTextColor(requireContext().getColor(android.R.color.holo_red_dark))
        binding.successMessageTextView.text = "Попробуйте создать задачу еще раз"
        binding.createAnotherTaskButton.visibility = View.VISIBLE
        binding.goToTasksButton.visibility = View.VISIBLE

        // Перезапускаем анимацию для состояния ошибки
        playSuccessAnimation()
    }

    private fun navigateToCreateNewTask() {
        findNavController().popBackStack(
            R.id.createTaskSelectionEmployeeFragment,
            false
        )
    }

    private fun navigateToTasksList() {
        val action = CreateTaskDoneFragmentDirections
            .actionCreateTaskDoneFragmentToInspectorTaskListFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}