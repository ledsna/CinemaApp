
package loginSystem

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class JSONDataManager(private val filePath : String) : DataManager {

    override fun loadUsers(): MutableMap<String, User> {
        val file = File(filePath)
        if (file.exists()) {
            return Json.decodeFromString(file.readText())
        }
        return mutableMapOf()
    }

    override fun saveUsers(users: Map<String, User>) {
        val jsonText = Json.encodeToString(users)
        File(filePath).writeText(jsonText)
    }
}
