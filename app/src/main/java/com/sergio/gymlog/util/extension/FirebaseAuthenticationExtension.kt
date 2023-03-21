package com.sergio.gymlog.util.extension

import android.app.Activity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuthException
import com.sergio.gymlog.R


private val authErrors : Map<String, Int> = mapOf(
    "ERROR_INVALID_EMAIL" to R.string.firebase_error_invalid_email_format,
    "ERROR_WRONG_PASSWORD" to R.string.firebase_error_wrong_password,
    "ERROR_EMAIL_ALREADY_IN_USE" to  R.string.firebase_error_email_in_use,
    "ERROR_USER_NOT_FOUND" to R.string.error_login_user_not_found,
    "ERROR_WEAK_PASSWORD" to R.string.firebase_error_weak_password
//    "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" to R.string.error_login_accounts_exits_with_different_credential,
//    "ERROR_CREDENTIAL_ALREADY_IN_USE" to R.string.error_login_credential_already_in_use,
//    "ERROR_USER_DISABLED" to R.string.error_login_user_disabled,
//    "ERROR_USER_TOKEN_EXPIRED" to R.string.error_login_user_token_expired,
//    "ERROR_OPERATION_NOT_ALLOWED" to R.string.error_login_operation_not_allowed,
)

fun FirebaseAuthException.getErrorMessage() : Int{

    return authErrors[errorCode] ?: R.string.firebase_error_generic

}


