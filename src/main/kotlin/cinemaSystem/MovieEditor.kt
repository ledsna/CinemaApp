package cinemaSystem

class MovieEditor(private val json : JSONCinemaManager) {
    private val movies = mutableListOf<Movie>()
    init {
        movies.addAll(json.loadMovies())
    }

    fun findMovie(title: String) : Boolean = movies.find{it.title == title} != null

    fun editMovie(id: Int, movieEdited: Movie) {
        if (id in movies.indices) {
            movies[id] = movieEdited
            json.saveMovies(movies)
            return
        }
        throw IllegalArgumentException("Invalid movie id")
    }

    fun removeMovie(id: Int) {
        if (id in movies.indices) {
            movies.removeAt(id)
            json.saveMovies(movies)
            return
        }
        throw IllegalArgumentException("Invalid movie id")
    }

    fun addMovie(movie: Movie) {
        movies.add(movie)
        json.saveMovies(movies)
    }

    fun getMovies(): List<Movie> {
        return movies
    }

}