import cinemaSystem.*
import loginSystem.*
import loginSystem.UserConsole

fun main() {
    val userDataRepository = JSONDataManager("appData/user_data.json")
    val passwordService = BcryptPasswordService()
    val userService = UserService(userDataRepository, passwordService)
    val userConsole = UserConsole(userService)
    val dataStorage = JSONCinemaManager()
    val movieManager = MovieEditor(dataStorage)
    val sessionManager = SessionController(dataStorage)
    val cinemaConsole = CinemaConsole(movieManager, sessionManager)
    var input: String

    while (true) {
        print("1: Register\n2: Login\n3: Exit\n> ")

        input = readln()
        var success = false
        when (input) {
            "1" -> success = userConsole.signUp()
            "2" -> success = userConsole.signIn()
            "3" -> {
                println("Bye-bye!..")
                return
            }
            else -> println("Invalid option...")
        }

        if (success) {
            cinemaConsole.runApplication()
        }
    }
}

