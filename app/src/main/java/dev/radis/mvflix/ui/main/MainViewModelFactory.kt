package dev.radis.mvflix.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.radis.mvflix.data.repository.MovieRepository

class MainViewModelFactory constructor(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java))
            MainViewModel(this.movieRepository) as T
        else
            throw IllegalStateException("ViewModel Class Not Found!")
    }
}