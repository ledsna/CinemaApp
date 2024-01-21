package loginSystem

interface Encrypter {
    fun encrypt(password: String, salt: String): String
}
