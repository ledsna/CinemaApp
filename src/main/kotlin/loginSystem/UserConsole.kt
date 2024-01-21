package loginSystem

class UserConsole(private val userService: UserService) {
    fun signUp(): Boolean {
        println("Creating a new user...")
        print("Enter username: ")
        val username = readln()
        print("Enter password: ")
        var password = readln()
        while (password.length < 8) {
            println("Your password should be at least 8 symbols long!")
            password = readln()
        }

        if (userService.register(username, password)) {
            println("Registration complete!")
            return true
        }
        println("User already exists...")
        return false
    }

    fun signIn(): Boolean {
        println("Logging in...")
        print("Enter username: ")
        val username = readln()
        print("Enter password: " )
        val password = readln()

        if (userService.login(username, password)) {
            println("Login successful!")
            return true
        }
        return false
    }
}
