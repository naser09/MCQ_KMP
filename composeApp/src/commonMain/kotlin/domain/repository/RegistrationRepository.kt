package domain.repository

import core.MyResult
import domain.model.RegistrationRequest
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository{
    fun requestRegistration(registrationRequest: RegistrationRequest):Flow<MyResult>
}