package presentaion.registration_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.MyResult
import domain.model.RegistrationRequest
import domain.repository.UserAuthenticationRepository
import domain.use_cases.RegistrationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RegistrationModel(
    private val registrationUseCase: RegistrationUseCase,
    val userAuthenticationRepository: UserAuthenticationRepository
):ScreenModel {
    private var _showLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")
    val showLoading:State<Boolean> get() = _showLoading
    val state = mutableStateOf<MyResult?>(null)

    fun clearErrorMessage(){ errorMessage.value = "" }
    fun printToken(){
        userAuthenticationRepository.getToken()?.let { println(it) }
    }
    fun register(registrationRequest: RegistrationRequest){
        registrationUseCase(registrationRequest)
            .onEach {
                state.value = it
                when(it){
                    is MyResult.Failed<*> ->{
                        _showLoading.value = false
                        errorMessage.value = it.error
                    }
                    MyResult.Loading -> {
                        _showLoading.value = true
                    }
                    is MyResult.Success<*> -> {
                        _showLoading.value = false
                        println(it.data)
                        userAuthenticationRepository.saveToken(it.data as String)
                    }
                }
            }
            .launchIn(screenModelScope)
    }
}