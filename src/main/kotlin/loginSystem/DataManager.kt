package loginSystem

interface DataManager {
    fun loadUsers(): MutableMap<String, User>
    fun saveUsers(users: Map<String, User>)
}