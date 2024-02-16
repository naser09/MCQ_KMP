package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLogoutRequest(
val logout:Boolean = true,
val logoutAllOther:Boolean = false,
val logoutAllDevice:Boolean = false
)