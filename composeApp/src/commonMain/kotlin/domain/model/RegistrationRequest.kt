package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(
val username:String?,
val email:String,
val password:String,
val dateOfBirth:Long?,
val gender:Int, //0 male 1.female
val about:String
)