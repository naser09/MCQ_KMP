package domain.repository

import core.MyResult
import domain.model.ForgetPasswordRequest
import domain.model.ForgetPasswordRequestVerify
import domain.model.ForgetSetPasswordRequest
import domain.model.LoginRequest
import domain.model.RegistrationRequest
import domain.model.UserLogoutRequest
import kotlinx.coroutines.flow.Flow

interface UserAuthenticationRepository {
    fun requestRegistration(registrationRequest: RegistrationRequest): Flow<MyResult>
    fun loginUser(loginRequest: LoginRequest):Flow<MyResult>
    fun forgetPasswordRequest(forgetPasswordRequest: ForgetPasswordRequest):Flow<MyResult>
    fun forgetPasswordVerify(forgetPasswordRequestVerify: ForgetPasswordRequestVerify):Flow<MyResult>
    fun forgetPasswordSet(forgetSetPasswordRequest: ForgetSetPasswordRequest):Flow<MyResult>
    fun logoutUser(userLogoutRequest: UserLogoutRequest):Flow<MyResult>
    fun saveToken(token:String)
    fun getToken():String?
}