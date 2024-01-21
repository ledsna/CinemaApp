package loginSystem

import org.mindrot.jbcrypt.BCrypt

class BcryptPasswordService : Encrypter {
    override fun encrypt(password: String, salt: String): String {
        return BCrypt.hashpw(password, salt)
    }
}
