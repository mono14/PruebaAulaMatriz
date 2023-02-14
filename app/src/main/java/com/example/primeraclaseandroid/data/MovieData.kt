package com.example.primeraclaseandroid.data

class MovieData {

    companion object {
        fun getData() : List<Movie> {
            return listOf(
                Movie(
                    id = 1,
                    title = "spiderman",
                    poster_path = "https://image.tmdb.org/t/p/w342/osYbtvqjMUhEXgkuFJOsRYVpq6N.jpg",
                    release_date = "12/03/2005",
                    vote_average = 3.5f,
                    overview = "Este es el resumen de la pelicula y es una pelicula muy divertida, se trata de un señor que lo pica una araña y le pasan muchas cosas"
                ),
                Movie(
                    id = 2,
                    title = "spiderman 2",
                    poster_path = "https://image.tmdb.org/t/p/w342/osYbtvqjMUhEXgkuFJOsRYVpq6N.jpg",
                            release_date = "12/03/2005",
                    vote_average = 3.5f,
                    overview = "Este es el resumen de la pelicula y es una pelicula muy divertida, se trata de un señor que lo pica una araña y le pasan muchas cosas"
                ),
                Movie(
                    id = 3,
                    title = "spiderman 3",
                    poster_path = "https://image.tmdb.org/t/p/w342/osYbtvqjMUhEXgkuFJOsRYVpq6N.jpg",
                    release_date = "12/03/2005",
                    vote_average = 3.5f,
                    overview = "Este es el resumen de la pelicula y es una pelicula muy divertida, se trata de un señor que lo pica una araña y le pasan muchas cosas"
                ),
                Movie(
                    id = 4,
                    title = "spiderman 4",
                    poster_path = "https://image.tmdb.org/t/p/w342/osYbtvqjMUhEXgkuFJOsRYVpq6N.jpg",
                    release_date = "12/03/2005",
                    vote_average = 3.5f,
                    overview = "Este es el resumen de la pelicula y es una pelicula muy divertida, se trata de un señor que lo pica una araña y le pasan muchas cosas"

                ),
                Movie(
                    id = 5,
                    title = "spiderman 5",
                    poster_path = "https://image.tmdb.org/t/p/w342/osYbtvqjMUhEXgkuFJOsRYVpq6N.jpg",
                    release_date = "12/03/2005",
                    vote_average = 3.5f,
                    overview = "Este es el resumen de la pelicula y es una pelicula muy divertida, se trata de un señor que lo pica una araña y le pasan muchas cosas"
                ),
                Movie(
                    id = 6,
                    title = "spiderman 6",
                    poster_path = "https://image.tmdb.org/t/p/w342/osYbtvqjMUhEXgkuFJOsRYVpq6N.jpg",
                    release_date = "12/03/2005",
                    vote_average = 3.5f,
                    overview = "Este es el resumen de la pelicula y es una pelicula muy divertida, se trata de un señor que lo pica una araña y le pasan muchas cosas"
                )
            )
        }

    }

}