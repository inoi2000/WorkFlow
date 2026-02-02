package com.petproject.workflow.presentation.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.FragmentCreateStatementDoneBinding
import com.petproject.workflow.presentation.viewmodels.CreateStatementDoneViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateStatementDoneFragment : Fragment() {
    private var _binding: FragmentCreateStatementDoneBinding? = null
    private val binding: FragmentCreateStatementDoneBinding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val args by navArgs<CreateStatementDoneFragmentArgs>()

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CreateStatementDoneViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as WorkFlowApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        component.inject(this)
        _binding = FragmentCreateStatementDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViews()
        setupObservers()
        createStatement()
    }

    private fun setupViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.setStatementData(args.statement)
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
        binding.createAnotherStatementButton.setOnClickListener {
            navigateToCreateNewStatement()
        }

        binding.goToJourneysButton.setOnClickListener {
            navigateToJourneysList()
        }
    }

    private fun setupObservers() {
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoadingState()
            } else {
                // Загрузка завершена, успех обрабатывается в другом observer
            }
        }

        viewModel.statementCreationResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { statement ->
                // Успешное создание заявки
                showSuccessState()

                // Можно использовать statement для дополнительной логики
                // Например, отображения ID заявки или других данных
                viewModel.setStatementData(statement)
            }.onFailure { throwable ->
                // Ошибка создания заявки
                showErrorState(throwable.message ?: "Неизвестная ошибка")
            }
        }

        viewModel.navigateToJourneys.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                navigateToJourneysList()
                viewModel.onNavigationComplete()
            }
        }
    }

    private fun createStatement() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val createdStatement = viewModel.createStatement(args.statement)
                // Дополнительная обработка успешно созданной заявки, если нужно
            } catch (e: Exception) {
                // Ошибка уже обработана в observer
            }
        }
    }

    private fun showLoadingState() {
        binding.loadingState.isVisible = true
        binding.circleBackground.isVisible = false
        binding.successIcon.isVisible = false
        binding.successTitleTextView.isVisible = false
        binding.successMessageTextView.isVisible = false
        binding.createAnotherStatementButton.isVisible = false
        binding.goToJourneysButton.isVisible = false
    }

    private fun showSuccessState() {
        binding.loadingState.isVisible = false
        binding.circleBackground.isVisible = true
        binding.successIcon.isVisible = true
        binding.successTitleTextView.isVisible = true
        binding.successMessageTextView.isVisible = true
        binding.createAnotherStatementButton.isVisible = true
        binding.goToJourneysButton.isVisible = true
    }

    private fun showErrorState(errorMessage: String) {
        binding.loadingState.isVisible = false
        binding.successIcon.setImageResource(android.R.drawable.ic_dialog_alert)
        binding.successIcon.setColorFilter(requireContext().getColor(android.R.color.holo_red_dark))
        binding.successTitleTextView.text = "Ошибка создания заявки"
        binding.successTitleTextView.setTextColor(requireContext().getColor(android.R.color.holo_red_dark))
        binding.successMessageTextView.text = errorMessage
        binding.createAnotherStatementButton.isVisible = true
        binding.goToJourneysButton.isVisible = true

        // Перезапускаем анимацию для состояния ошибки
        playSuccessAnimation()
    }

    private fun navigateToCreateNewStatement() {
        findNavController().navigateUp()
    }

    private fun navigateToJourneysList() {
        // Пример навигации к списку рейсов
        // Замените на вашу реальную навигацию
        val action = CreateStatementDoneFragmentDirections
            .actionCreateStatementDoneFragmentToStatementListFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}