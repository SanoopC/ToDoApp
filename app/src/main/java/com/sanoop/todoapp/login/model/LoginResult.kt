package com.sanoop.todoapp.login.model

/**
 * Authentication result : success (user token) or error message.
 */
data class LoginResult(
    val token: String? = null,
    val error: String? = null
)