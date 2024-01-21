package cinemaSystem

import kotlinx.serialization.Serializable

@Serializable
class Movie(val title: String, val description: String, var duration: Int) {
    override fun toString() : String {
        return "$title, $duration minutes.\n\t$description"
    }
}