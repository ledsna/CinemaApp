package cinemaSystem

interface ICinemaManager {
    fun loadMovies(): List<Movie>
    fun loadSessions(): List<Session>

    fun saveSessions(entities: List<Session>)
    fun saveMovies(entities:List<Movie>)
}
