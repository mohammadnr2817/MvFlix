package dev.radis.mvflix.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.radis.mvflix.data.repository.ApiService
import dev.radis.mvflix.data.repository.MovieRepository
import dev.radis.mvflix.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private val adapter = MovieAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieRepository = MovieRepository(ApiService.getInstance())
        binding.mainRvMovies.adapter = adapter
        binding.mainRvMovies.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(movieRepository)
        )[MainViewModel::class.java]

        viewModel.getErrorMessage().observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.getLoading().observe(this) {
            binding.mainProgress.isVisible = it
            binding.mainRvMovies.isVisible = !it
        }

        viewModel.movieList.observe(this) {
            adapter.setMovies(it)
        }

        viewModel.getMovieList()

    }
}