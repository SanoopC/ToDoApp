package com.sanoop.todoapp.network

import com.sanoop.todoapp.login.model.LoginResult
import com.sanoop.todoapp.login.model.LoginModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("login ")
    suspend fun validateLogin(@Body loginModel: LoginModel): Response<LoginResult>
}