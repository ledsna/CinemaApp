
package loginSystem

import org.mindrot.jbcrypt.BCrypt

class UserService(private val userDataRepository: DataManager,
                  private val encrypter: Encrypter) {
    private var users = userDataRepository.loadUsers()

    fun register(username: String, password: String): Boolean {
        if (username in users) {
            return false
        }
        val salt = BCrypt.gensalt(12)
        val encryptedPassword = encrypter.encrypt(password, salt)
        users[username] = User(username, encryptedPassword, salt)
        userDataRepository.saveUsers(users)
        return true
    }

    fun login(username: String, password: String): Boolean {
        if (users.isEmpty()) {
            println("There are no users! Register!")
            return false
        }
        val user = users[username]
        if (user != null) {
            if (password.length >= 8) {
                val inputPassword = encrypter.encrypt(password, user.salt)
                if (user.encryptedPassword == inputPassword) return true
            }
        }
        println("Incorrect username or password!")
        return false
    }
}
