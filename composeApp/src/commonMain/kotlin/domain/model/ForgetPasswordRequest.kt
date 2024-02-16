package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ForgetPasswordRequest(
val email:String
)
@Serializable
data class ForgetPasswordRequestVerify(val otp:Int,val email:String)

@Serializable
data class ForgetSetPasswordRequest(
    val password:String
)