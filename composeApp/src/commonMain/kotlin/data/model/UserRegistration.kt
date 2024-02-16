package data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRegistration(
    val username:String?,
    val email:String,
    val password:String,
    val dateOfBirth:Long?,
    val gender:Int, //0 male 1.female
    val about:String
)
