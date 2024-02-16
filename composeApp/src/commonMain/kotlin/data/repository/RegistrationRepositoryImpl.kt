package data.repository

import core.CoreData
import core.MyResult
import core.base64encode
import data.model.UserRegistration
import domain.model.RegistrationRequest
import domain.repository.RegistrationRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegistrationRepositoryImpl(
    private val client: HttpClient
):RegistrationRepository {
    override fun requestRegistration(registrationRequest: RegistrationRequest): Flow<MyResult> {
        val data:UserRegistration = registrationRequest.run {
            UserRegistration(username, email, password, dateOfBirth, gender, about)
        }
        val encoded = data.base64encode(UserRegistration.serializer())
        return flow {
            emit(MyResult.Loading)
            try {
                val response = client.post("${CoreData.apiHost}/user/registration"){
                    setBody(encoded)
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
}