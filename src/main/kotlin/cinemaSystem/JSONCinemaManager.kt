package cinemaSystem

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

class JSONCinemaManager : ICinemaManager {
    private val pathToMovies = "appData/movies_data.json"
    private val pathToSessions = "appData/sessions_data.json"

    private val json = Json { ignoreUnknownKeys = true }
    override fun loadMovies(): List<Movie> {
        val file = File(pathToMovies)
        if (file.exists()) {
            try {
                val jsonText = file.readText()
                return json.decodeFromString(jsonText)
            } catch (e: IOException) {
                println("It happened 1")
            }
        }
        return emptyList()
    }

    override fun loadSessions(): List<Session> {
        val file = File(pathToSessions)
        if (file.exists()) {
            try {
                val jsonText = file.readText()
                return json.decodeFromString(jsonText)
            } catch (e: IOException) {
                println("It happened 2")
            }
        }
        return emptyList()
    }

    override fun saveMovies(entities: List<Movie>) {
        val jsonText = json.encodeToString(entities)
        try {
            File(pathToMovies).writeText(jsonText)
        } catch (e: IOException) {
            //
        }
    }

    override fun saveSessions(entities: List<Session>) {
        val jsonText = json.encodeToString(entities)
        try {
            File(pathToSessions).writeText(jsonText)
        } catch (e: IOException) {
            //
        }
    }
}
