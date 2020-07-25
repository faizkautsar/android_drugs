package com.example.drugs.repositories

import com.example.drugs.utils.SingleResponse
import com.google.firebase.iid.FirebaseInstanceId

interface FirebaseContract{
    fun getToken(listener: SingleResponse<String>)
}

class FirebaseRepository : FirebaseContract{
    override fun getToken(listener: SingleResponse<String>) {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            when{
                it.isSuccessful -> {
                    it.result?.let { result ->
                        listener.onSuccess(result.token)
                    } ?: listener.onFailure(Error("Failed to get firebase token"))
                }
                else -> listener.onFailure(Error("Exception happen when get firebase token"))
            }
        }
    }
}