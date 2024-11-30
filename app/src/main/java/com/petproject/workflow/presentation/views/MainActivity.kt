package com.petproject.workflow.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.petproject.workflow.R
import com.petproject.workflow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val employeeId: String by lazy {
        intent.getStringExtra(EXTRA_CURRENT_EMPLOYEE_ID)
            ?: throw IllegalArgumentException("Employee ID is incorrect")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
    }

//    fun hideBottomNavigationView() {
//        binding.bottomNav.visibility = View.GONE
//    }
//
//    fun showBottomNavigationView() {
//        binding.bottomNav.visibility = View.VISIBLE
//    }


    companion object {
        const val EXTRA_CURRENT_EMPLOYEE_ID = "current_employee_id"

        fun newIntent(context: Context, employeeId: String): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_CURRENT_EMPLOYEE_ID, employeeId)
            }
        }
    }
}