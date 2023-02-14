package com.example.primeraclaseandroid.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.primeraclaseandroid.data.Movie
import com.example.primeraclaseandroid.databinding.ActivityDetailMovieBinding
import com.example.primeraclaseandroid.load

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private val movie: Movie?
        get() = intent.extras?.getParcelable(MOVIE_EXTRA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Glide.with(this).load(movie?.poster_path).into(binding.imvPoster)
        movie?.poster_path?.let { binding.imvPoster.load(it) }
        binding.tlbToolbar.title = movie?.title ?: ""


        movie?.let { binding.dtlHeader.imvPoster.load(it.poster_path) }
        binding.dtlHeader.rtbCalificacion.rating= movie?.vote_average ?: 0f
        binding.dtlHeader.txvFechaLanzamiento.text = movie?.release_date ?: ""
        binding.dtlHeader.txvTitle.text = movie?.title ?: ""

        binding.dtlBody.txvResumen.text = movie?.overview ?: ""


    }


}