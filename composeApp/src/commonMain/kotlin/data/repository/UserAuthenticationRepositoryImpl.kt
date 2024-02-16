package data.repository

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SettingsListener
import core.CoreData
import core.MyResult
import core.base64encode
import domain.model.ForgetPasswordRequest
import domain.model.ForgetPasswordRequestVerify
import domain.model.ForgetSetPasswordRequest
import domain.model.LoginRequest
import domain.model.RegistrationRequest
import domain.model.UserLogoutRequest
import domain.repository.UserAuthenticationRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class UserAuthenticationRepositoryImpl(
    private val client: HttpClient,
    private val settings: Settings
):UserAuthenticationRepository {
    override fun requestRegistration(registrationRequest: RegistrationRequest): Flow<MyResult> {
        return flow {
            emit(MyResult.Loading)
            try {
                val response = client.post("${CoreData.apiHost}/user/registration"){
                    setBody(registrationRequest.base64encode(RegistrationRequest.serializer()))
                }
                if (response.status == HttpStatusCode.OK){
                    emit(MyResult.Success(response.bodyAsText()))
                }else{
                    emit(MyResult.Failed(data = response,response.bodyAsText()))
                }
            }catch (ex:Exception){
                emit(MyResult.Failed(null,ex.message?:"Unknown error"))
            }
        }
    }

    override fun loginUser(loginRequest: LoginRequest): Flow<MyResult> {
        return flow {
            emit(MyResult.Loading)
            try {
                val response = client.post("${CoreData.apiHost}/user/login"){
                    setBody(loginRequest.base64encode(LoginRequest.serializer()))
                }
                if (response.status == HttpStatusCode.OK){
                    emit(MyResult.Success(response.bodyAsText()))
                }else{
                    emit(MyResult.Failed(data = response,response.bodyAsText()))
                }
            }catch (ex:Exception){
                emit(MyResult.Failed(null,ex.message?:"Unknown error"))
            }
        }
    }

    override fun forgetPasswordRequest(forgetPasswordRequest: ForgetPasswordRequest): Flow<MyResult> {
        return flow {
            emit(MyResult.Loading)
            try {
                val response = client.post("${CoreData.apiHost}/user/forget"){
                    setBody(forgetPasswordRequest.base64encode(ForgetPasswordRequest.serializer()))
                }
                if (response.status == HttpStatusCode.OK){
                    emit(MyResult.Success(response.bodyAsText()))
                }else{
                    emit(MyResult.Failed(data = response,response.bodyAsText()))
                }
            }catch (ex:Exception){
                emit(MyResult.Failed(null,ex.message?:"Unknown error"))
            }
        }
    }

    override fun forgetPasswordVerify(forgetPasswordRequestVerify: ForgetPasswordRequestVerify): Flow<MyResult> {
        return flow {
            emit(MyResult.Loading)
            try {
                val response = client.post("${CoreData.apiHost}/user/forget/verify"){
                    setBody(forgetPasswordRequestVerify.base64encode(ForgetPasswordRequestVerify.serializer()))
                }
                if (response.status == HttpStatusCode.OK){
                    emit(MyResult.Success(response.bodyAsText()))
                }else{
                    emit(MyResult.Failed(data = response,response.bodyAsText()))
                }
            }catch (ex:Exception){
                emit(MyResult.Failed(null,ex.message?:"Unknown error"))
            }
        }
    }

    override fun forgetPasswordSet(forgetSetPasswordRequest: ForgetSetPasswordRequest): Flow<MyResult> {
        return flow {
            emit(MyResult.Loading)
            try {
                val response = client.post("${CoreData.apiHost}/user/registration"){
                    setBody(forgetSetPasswordRequest.base64encode(ForgetSetPasswordRequest.serializer()))
                }
                if (response.status == HttpStatusCode.OK){
                    emit(MyResult.Success(response.bodyAsText()))
                }else{
                    emit(MyResult.Failed(data = response,response.bodyAsText()))
                }
            }catch (ex:Exception){
                emit(MyResult.Failed(null,ex.message?:"Unknown error"))
            }
        }
    }

    override fun logoutUser(userLogoutRequest: UserLogoutRequest): Flow<MyResult> {
        return flow {
            emit(MyResult.Loading)
            try {
                val response = client.post("${CoreData.apiHost}/user/registration"){
                    setBody(userLogoutRequest.base64encode(UserLogoutRequest.serializer()))
                }
                if (response.status == HttpStatusCode.OK){
                    emit(MyResult.Success(response.bodyAsText()))
                }else{
                    emit(MyResult.Failed(data = response,response.bodyAsText()))
                }
            }catch (ex:Exception){
                emit(MyResult.Failed(null,ex.message?:"Unknown error"))
            }
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    override fun saveToken(token: String) {
        val data = Base64.encode(token.toByteArray())
        settings.putString("token",data)
    }
    @OptIn(ExperimentalEncodingApi::class)
    override fun getToken(): String? {
        val data = settings.getStringOrNull("token")

        return data?.toByteArray()?.let { Base64.decode(it).decodeToString() }
    }
}