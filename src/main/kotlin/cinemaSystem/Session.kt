package cinemaSystem

import LocalDateTimeSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlinx.serialization.*

@Serializable
data class Session(val movie: Movie, @Serializable(with = LocalDateTimeSerializer::class) val start: LocalDateTime) {
    @Serializable(with = LocalDateTimeSerializer::class) private val end = start.plusMinutes(movie.duration.toLong())
    private val rows = 10
    private val places = 20
    private val hall = Hall(rows, places)

    fun getHall() : Hall {
        return hall
    }

    fun bookSeat(row: Int, place: Int): String {
        val seat = hall.getSeat(row - 1, place - 1)
        if (seat.getCurrentState() != SeatState.FREE) {
            return "Seat $row $place is already booked..."
        }
        seat.setBooked()
        return "Successfully booked!"
    }

    fun freeSeat(row: Int, place: Int): String {
        val seat = hall.getSeat(row - 1, place - 1)
        if (seat.getCurrentState() != SeatState.BOOKED) {
            return "Seat $row $place is already free..."
        }
        seat.setFree()
        return "Seat successfully freed!"
    }

    fun occupySeat(row: Int, place: Int): String {
        val seat = hall.getSeat(row - 1, place - 1)
        if (seat.getCurrentState() == SeatState.FREE) {
            return "Seat $row $place is not yet booked!"
        }
        if (seat.getCurrentState() == SeatState.OCCUPIED) {
            return "Seat $row $place is already occupied by somebody else..."
        }
        seat.setOccupied()
        return "Ticket scanned!"
    }

    override fun toString(): String {
        val end = start.plusMinutes(movie.duration.toLong())
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        val formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        var string = "$movie\n\tTime and date: ${start.format(formatterTime)} - ${end.format(formatterTime)}"
        string += "\t${start.format(formatterDate)}"
        return string
    }
}