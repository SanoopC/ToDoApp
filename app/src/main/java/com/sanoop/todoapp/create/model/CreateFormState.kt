package com.sanoop.todoapp.create.model

/**
 * Data validation state of the login form.
 */
data class CreateFormState(
    val titleError: Int? = null,
    val timeError: Int? = null,
    val isDataValid: Boolean = false
)