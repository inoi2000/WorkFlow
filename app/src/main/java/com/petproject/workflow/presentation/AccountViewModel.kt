package com.petproject.workflow.presentation

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    fun signOut() {
        auth.signOut()
    }
}