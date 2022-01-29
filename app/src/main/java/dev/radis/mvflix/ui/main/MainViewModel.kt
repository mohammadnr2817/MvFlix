package dev.radis.mvflix.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.radis.mvflix.data.model.Movie
import dev.radis.mvflix.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel constructor(private val movieRepository: MovieRepository) : ViewModel() {
    val movieList = MutableLiveData<List<Movie>>()
    private val errorMessage = MutableLiveData<String>()
    private val loading = MutableLiveData<Boolean>()

    private var movieJob: Job? = null
    private val customExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorMessage.value = throwable.localizedMessage
    }

    fun getErrorMessage(): LiveData<String> = errorMessage
    fun getLoading(): LiveData<Boolean> = loading

    fun getMovieList() {
        movieJob = viewModelScope.launch(customExceptionHandler) {
            movieList.value = movieRepository.getMovieList()
            loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        movieJob?.cancel()
    }
}