package cinemaSystem

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class CinemaConsole(private val movieEditor: MovieEditor,
                    private val sessionController: SessionController,) {
    fun runApplication() {
        var input: String

        do {
            homeMenu()
            input = readln()

            when (input) {
                "1" -> addMovie()
                "2" -> editMovies()
                "3" -> addSession()
                "4" -> editSessions()
                "5" -> sellTicket()
                "6" -> scanTicket()
                "7" -> returnTicket()
            }
        } while (input in "1".."7")
    }

    private fun homeMenu() {
        println("Type the operation id:")

        println("\t1: Add a new movie")
        println("\t2: Edit movies")
        println("\t3: Add a new session")
        println("\t4: Edit current sessions")
        println("\t5: Sell a ticket")
        println("\t6: Scan a ticket")
        println("\t7: Return a ticket")

        println("\tAnything else: Login screen")
    }

    private fun editMovies() {
        val movies = movieEditor.getMovies()
        if (movies.isEmpty()) {
            println("No movies are currently available.")
            return
        }

        movies.forEachIndexed { id, movie -> print("${id + 1}: $movie\n") }

        println("Enter a movie id to edit or anything else to go back:")
        var id = readln().toIntOrNull()
        if (id == null || id - 1 !in movies.indices) {
            return
        }

        id -= 1

        println("'D' to delete, 'Enter' to edit:")
        val input = readln()
        if (input == "D") {
            sessionController.removeSessionsByMovie(movies[id])
            movieEditor.removeMovie(id)
            println("The movie has been successfully removed.")
            return
        }

        println("New movie title ('Enter' to skip):")
        val name = readln()
        println("New movie description ('Enter' to skip):")
        val description = readln()

        val updatedMovie = Movie(name.ifBlank { movies[id].title },
            description.ifBlank { movies[id].description },
            movies[id].duration)

        movieEditor.editMovie(id, updatedMovie)

        println("Your changes were applied!")
    }

    private fun addMovie() {
        print("Title: ")
        val title = readln()
        if (movieEditor.findMovie(title)) {
            println("Oh no... This movie already exists!")
            return
        }

        var duration: Int? = null
        while (duration == null) {
            print("\nMovie duration (minutes): ")
            duration = readln().toIntOrNull()
            if (duration == null) println("Incorrect duration format...")
        }

        var description: String? = null
        while (description == null || description == "") {
            print("\nMovie description: ")
            description = readln()
            if (description == "") println("Description cannot be empty...")
        }

        val movie = Movie(title, description, duration)
        movieEditor.addMovie(movie)

        println("\nMovie added!")
    }

    private fun editSessions() {
        val sessions = getSessions(true) ?: return

        println("Enter a session id to edit or anything else to go back: ")
        var id = readln().toIntOrNull()
        if (id == null || id - 1 !in sessions.indices) {
            return
        }
        id -= 1

        println("'D' to delete, 'Enter' to edit:")
        val input = readln()

        if (input == "D") {
            sessionController.removeSession(id)
            println("The session has been successfully removed.")
            return
        }
        addSession()
        sessionController.removeSession(id)
    }

    private fun getSessions(onlyCurrent: Boolean = false): List<Session>? {
        val sessions = if (onlyCurrent) sessionController.getCurrentSessions() else sessionController.getSessions()
        if (sessions.isEmpty()) {
            println("There are no sessions...")
            return null
        }
        sessions.forEachIndexed { id, session -> println("${id + 1}: $session") }
        return sessions
    }

    private fun addSession() {
        val movie = chooseMovie()
        var start: LocalDateTime? = null
        val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")

        while (start == null || sessionController.findOverlaps(start, start.plusMinutes(movie.duration.toLong())))
        {
            println("Session start time ('HH:mm dd.MM.yyyy'):")
            val sessionTime = readln()
            try {
                start = LocalDateTime.parse(sessionTime, formatter)
            } catch (e: DateTimeParseException) {
                println("Invalid date format!")
            }
        }

        sessionController.addSession(Session(movie, start))
        println("Session added successfully.")
    }

    private fun chooseMovie(): Movie {
        val movies = movieEditor.getMovies()
        movies.forEachIndexed { id, movie -> println("${id + 1}: $movie") }

        var id: Int? = null
        while (id == null || id - 1 !in movies.indices) {
            println("Enter an id of the movie you want to choose:")
            id = readln().toIntOrNull()
            if (id == null || id - 1 !in movies.indices) println("Error! Incorrect id.")
        }
        return movies[id - 1]
    }

    private fun chooseSeat(hall: Hall): Pair<Int, Int> {
        println("'o' — seat is free,")
        println("'x' — seat is booked,'")
        println("'v' — seat is occupied.\n")
        println(hall)

        var row: Int? = null
        while (row == null || !hall.rowExists(row)) {
            println("Enter seat row:")
            row = readln().toIntOrNull()
            if (row == null || !hall.rowExists(row)) println("This row doesn't exist...")
        }

        var place: Int? = null
        while (place == null || !hall.placeExists(place)) {
            println("Enter seat place:")
            place = readln().toIntOrNull()
            if (place == null || !hall.placeExists(place)) println("This place doesn't exist...")
        }

        return Pair(row, place)
    }

    private fun sellTicket() {
        val session = chooseSession() ?: return
        val seat = chooseSeat(session.getHall())
        val result = session.bookSeat(seat.first, seat.second)
        println(result)
    }

    private fun returnTicket() {
        val session = chooseSession() ?: return

        if (LocalDateTime.now() > session.start) {
            println("This session has already started, you can't return this ticket!")
            return
        }

        val seat = chooseSeat(session.getHall())
        val result = session.freeSeat(seat.first, seat.second)
        println(result)
    }

    private fun chooseSession(): Session? {
        val sessions = sessionController.getCurrentSessions()

        if (sessions.isEmpty()) {
            println("There are no sessions...")
            return null
        }

        sessions.forEachIndexed { id, session -> print("${id + 1}: $session\n") }

        println("Choose a session by id:")
        val id = readln().toIntOrNull()
        if (id == null || id - 1 !in sessions.indices) {
            println("Invalid session id.")
            return null
        }
        return sessions[id - 1]
    }

    private fun scanTicket() {
        val session = chooseSession() ?: return
        val seat = chooseSeat(session.getHall())
        println(session.occupySeat(seat.first, seat.second))
    }
}
