package dev.radis.mvflix.data.repository

import dev.radis.mvflix.data.model.Movie

class MovieRepository constructor(private val apiService: ApiService) {

    suspend fun getMovieList(): List<Movie> =
        apiService.getMovieList()

}