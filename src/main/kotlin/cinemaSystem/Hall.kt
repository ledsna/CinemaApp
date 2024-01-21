package cinemaSystem

import kotlinx.serialization.Serializable


@Serializable
data class Hall(val rows : Int, val places : Int) {
    private val seats = Array(rows) {row -> Array(places) {place -> Seat(row, place)} }

    fun rowExists(row: Int) : Boolean = row in 1..rows

    fun placeExists(place: Int) : Boolean = place in 1..places

    fun getSeat(row : Int, place: Int) : Seat = seats[row][place]

    override fun toString() : String {
        var finalString = ""
        for (row in 0..rows) {
            finalString += "${if (row == 0) "  " else if (row < 10) "$row " else "$row"} "
            for (place in 1..places) {
                finalString += if (row != 0) seats[row - 1][place - 1] else place
                finalString += if (row != 0) "  " else if (place < 10) "  " else " "
            }
            finalString += "\n"
        }

        return finalString
    }
}