package com.petproject.workflow.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.databinding.ActivityLoginBinding
import com.petproject.workflow.presentation.viewmodels.AuthViewModel
import com.petproject.workflow.presentation.viewmodels.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider.create(viewModelStore, viewModelFactory)[AuthViewModel::class]
    }

    private val component by lazy {
        (application as WorkFlowApplication).component
    }

    private val getAuthResponse =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@registerForActivityResult
            viewModel.handleAuthResponseIntent(dataIntent)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
//        checkAuthorization()
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        bindViewModel()
    }

    private fun bindViewModel() {
        binding.btnLogin.setOnClickListener { viewModel.openLoginPage() }
        viewModel.openAuthPageFlow.launchAndCollectIn(this) { intent ->
            // Open auth page
            getAuthResponse.launch(intent)
        }
        viewModel.toastFlow.launchAndCollectIn(this) {
            Toast.makeText(this@AuthActivity, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.authSuccessFlow.launchAndCollectIn(this) {
            //навигация к home activity
            Toast.makeText(this@AuthActivity, "Авторизация прощла успешно!", Toast.LENGTH_SHORT).show()
        }
    }

    private inline fun <T> Flow<T>.launchAndCollectIn(
        owner: LifecycleOwner,
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        crossinline action: suspend CoroutineScope.(T) -> Unit
    ) = owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(minActiveState) {
            collect {
                action(it)
            }
        }
    }

//    private fun checkAuthorization() {
//        viewModel.navigateToHomeScreen.observe(this) { employeeId ->
//            employeeId?.let {
//                val intent = MainActivity.newIntent(this, it)
//                startActivity(intent)
//                finish()
//            }
//        }
//    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AuthActivity::class.java)
        }
    }
}