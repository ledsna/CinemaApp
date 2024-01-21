package cinemaSystem

import kotlinx.serialization.Serializable

@Serializable
data class Seat(val row : Int, val place : Int) {
    private var state : SeatState = SeatState.FREE

    fun setBooked() {
        state = SeatState.BOOKED
    }

    fun setFree() {
        state = SeatState.FREE
    }

    fun setOccupied() {
        state = SeatState.OCCUPIED
    }

    fun getCurrentState() : SeatState {
        return state
    }
    override fun toString() : String {
        return if (state == SeatState.FREE) "o" else if (state == SeatState.BOOKED) "x" else "v"
    }
}