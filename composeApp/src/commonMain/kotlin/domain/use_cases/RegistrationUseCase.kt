package domain.use_cases

import core.EmailValidator
import core.MyResult
import domain.model.RegistrationRequest
import domain.repository.UserAuthenticationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class RegistrationUseCase(private val userAuthenticationRepository: UserAuthenticationRepository) {
    operator fun invoke(registrationRequest: RegistrationRequest):Flow<MyResult>{
        return flow {
            emit(MyResult.Loading)
            delay(4000)
            if (registrationRequest.password.length<6){
                emit(MyResult.Failed(null,"Password is too small !"))
                return@flow
            }else if (!EmailValidator.isValidEmail(registrationRequest.email)){
                emit(MyResult.Failed(null,"Please enter valid email !"))
                return@flow
            }else{
                userAuthenticationRepository.requestRegistration(registrationRequest).onEach {
                    emit(it)
                }.collect()
            }
        }
    }
}