package com.petproject.workflow.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.ActivityLoginBinding
import com.petproject.workflow.presentation.viewmodels.LoginViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(viewModelStore, viewModelFactory)[LoginViewModel::class]
    }

    private val component by lazy {
        (application as WorkFlowApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        checkAuthorization()
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        addTextChangeListeners()
    }

    private fun checkAuthorization() {
        viewModel.navigateToHomeScreen.observe(this) { employeeId ->
            employeeId?.let {
                val intent = MainActivity.newIntent(this, it)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun addTextChangeListeners() {
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputEmail()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputPassword()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}