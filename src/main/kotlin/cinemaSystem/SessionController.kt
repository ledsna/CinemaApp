package cinemaSystem

import java.time.LocalDateTime

class SessionController(private val json: JSONCinemaManager) {
    private val sessions = mutableListOf<Session>()

    init {
        sessions.addAll(json.loadSessions())
    }

    fun findOverlaps(start: LocalDateTime, end: LocalDateTime) : Boolean {
        for (session in sessions) {
            if (start >= session.start && end <= session.start.plusMinutes(session.movie.duration.toLong()))
                return true
        }
        return false
    }

    fun getSessions() : List<Session> = sessions

    fun getCurrentSessions() : List<Session> {
        return sessions.filter{ it.start.plusMinutes(it.movie.duration.toLong()) > LocalDateTime.now() }
    }

    fun addSession(session: Session) {
        sessions.add(session)
        sessions.sortBy { it.start }
        json.saveSessions(sessions)
    }

    fun removeSession(id: Int) {
        if (id in sessions.indices)
        {
            sessions.removeAt(id)
            json.saveSessions(sessions)
            return
        }
        throw IllegalArgumentException("Invalid session id!")
    }

    fun removeSessionsByMovie(movie: Movie) {
        sessions.removeIf { it.movie == movie }
    }
}