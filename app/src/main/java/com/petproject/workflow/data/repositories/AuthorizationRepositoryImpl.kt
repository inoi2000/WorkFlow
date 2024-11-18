package com.petproject.workflow.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.petproject.workflow.domain.repositories.AuthorizationRepository

class AuthorizationRepositoryImpl : AuthorizationRepository {

    private val auth: FirebaseAuth = Firebase.auth

    override fun signIn(email: String, password: String, onFailureListener: (Exception) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnFailureListener {
                onFailureListener(it)
            }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun verifySuccessAuthorization(callback: (String?) -> Unit) {
        auth.addAuthStateListener { firebaseAuth: FirebaseAuth ->
            firebaseAuth.currentUser?.let {
                callback(it.uid)
            }
        }
    }
}