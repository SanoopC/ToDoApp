package com.sanoop.todoapp.login.viewmodel

import android.util.Patterns
import androidx.lifecycle.*
import com.sanoop.todoapp.R
import com.sanoop.todoapp.login.model.LoginFormState
import com.sanoop.todoapp.login.model.LoginModel
import com.sanoop.todoapp.login.model.LoginResult
import com.sanoop.todoapp.network.RetrofitClient
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel() : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch() {
            try {
                val response = RetrofitClient.apiInterface.validateLogin(
                    LoginModel(email, password)
                )
                if (response.isSuccessful) {
                    _loginResult.value = response.body()
                } else {
                    val errorMessage =
                        JSONObject(response.errorBody()?.string()).get("error").toString()
                    _loginResult.value = LoginResult(
                        error = errorMessage
                    )
                }
            } catch (e: Exception) {
                _loginResult.value = LoginResult(error = "Login failed")
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isEmailValid(username)) {
            _loginForm.value = LoginFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isEmailValid(username: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }
}

class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}