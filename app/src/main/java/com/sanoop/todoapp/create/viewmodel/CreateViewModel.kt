package com.sanoop.todoapp.create.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sanoop.todoapp.R
import com.sanoop.todoapp.create.model.CreateFormState

class CreateViewModel() : ViewModel() {

    private val _createForm = MutableLiveData<CreateFormState>()
    val createFormState: LiveData<CreateFormState> = _createForm

    fun createDataChanged(title: String, time: String) {
        if (title.isBlank()) {
            _createForm.value = CreateFormState(titleError = R.string.invalid_title)
        } else if (time.isBlank()) {
            _createForm.value = CreateFormState(timeError = R.string.invalid_time)
        } else {
            _createForm.value = CreateFormState(isDataValid = true)
        }
    }
}

class CreateViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateViewModel::class.java)) {
            return CreateViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}