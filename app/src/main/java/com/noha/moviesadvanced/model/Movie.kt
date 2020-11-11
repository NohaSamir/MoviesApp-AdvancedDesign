package com.noha.moviesadvanced.model

data class Movie(
    val title: String,
    val overview: String,
    val poster: String,
    val rate: Float = 0.9f,
    val director: String,
    val actors: List<Actor>
)


fun getDummyListOfMovies(): List<Movie> {
    val movies = mutableListOf<Movie>()
    movies.add(
        Movie(
            "Mulan",
            "When the Emperor of China issues a decree that one man per family must serve in the Imperial Chinese Army to defend the country from Huns, Hua Mulan, the eldest daughter of an honored warrior, steps in to take the place of her ailing father. She is spirited, determined and quick on her feet. Disguised as a man by the name of Hua Jun, she is tested every step of the way and must harness her innermost strength and embrace her true potential. When the Emperor of China issues a decree that one man per family must serve in the Imperial Chinese Army to defend the country from Huns, Hua Mulan, the eldest daughter of an honored warrior, steps in to take the place of her ailing father. She is spirited, determined and quick on her feet. Disguised as a man by the name of Hua Jun, she is tested every step of the way and must harness her innermost strength and embrace her true potential.",
            "/aKx1ARwG55zZ0GpRvU2WrGrCG9o.jpg",
            director = "Mulan Director",
            actors = listOf(
                Actor("Chalmers" , "/A1XBu3CffBpSK8HEIJM8q7Mn4lz.jpg"),
                Actor("Jesse Metcalfe" , "/atp5FK0tBemYNYQfSPnI2egR7rX.jpg") ,
                Actor("Natalie Eva Marie" , "/7JX6yI3Z2NqZwm7kIiAmxzc6hRl.jpg")
            )
        )
    )
    movies.add(
        Movie(
            "Joker",
            "Forever alone in a crowd, failed comedian Arthur Fleck seeks connection as he walks the streets of Gotham City. Arthur wears two masks -- the one he paints for his day job as a clown, and the guise he projects in a futile attempt to feel like he's part of the world around him. Isolated, bullied and disregarded by society, Fleck begins a slow descent into madness as he transforms into the criminal mastermind known as the Joker.",
            "/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
            director = "Joker Director",
            actors = listOf(
                Actor("Chalmers" , "/A1XBu3CffBpSK8HEIJM8q7Mn4lz.jpg"),
                Actor("Jesse Metcalfe" , "/atp5FK0tBemYNYQfSPnI2egR7rX.jpg") ,
                Actor("Natalie Eva Marie" , "/7JX6yI3Z2NqZwm7kIiAmxzc6hRl.jpg")
            )
        )
    )
    movies.add(
        Movie(
            "Terminator",
            "The Terminator is a 1984 American science fiction film directed by James Cameron. It stars Arnold Schwarzenegger as the Terminator, a cyborg assassin sent back in time from 2029 to 1984 to kill Sarah Connor (Linda Hamilton), whose son will one day become a savior against machines in a post-apocalyptic future.",
            "/vqzNJRH4YyquRiWxCCOH0aXggHI.jpg",
            director = "Terminator Director",
            actors = listOf(
                Actor("Chalmers" , "/A1XBu3CffBpSK8HEIJM8q7Mn4lz.jpg"),
                Actor("Jesse Metcalfe" , "/atp5FK0tBemYNYQfSPnI2egR7rX.jpg") ,
                Actor("Natalie Eva Marie" , "/7JX6yI3Z2NqZwm7kIiAmxzc6hRl.jpg")
            )
        )
    )
    return movies
}