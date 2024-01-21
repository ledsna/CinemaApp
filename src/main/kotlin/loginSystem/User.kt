package loginSystem

import kotlinx.serialization.Serializable

@Serializable
data class User(val username: String, val encryptedPassword: String, val salt: String)
