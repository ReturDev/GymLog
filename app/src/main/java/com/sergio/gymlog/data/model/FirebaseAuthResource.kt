package com.sergio.gymlog.data.model


sealed class FirebaseAuthResource<out R> {

    data class Success<out R>(val result: R) : FirebaseAuthResource<R>()
    data class Failure(val exception: Exception) : FirebaseAuthResource<Nothing>()

}