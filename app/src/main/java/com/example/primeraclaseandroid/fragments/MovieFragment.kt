package com.example.primeraclaseandroid.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager

import com.example.primeraclaseandroid.activites.LoginActivity
import com.example.primeraclaseandroid.activites.MOVIE_EXTRA
import com.example.primeraclaseandroid.builder.ServiceBuilder
import com.example.primeraclaseandroid.databinding.FragmentMovieBinding
import com.example.primeraclaseandroid.data.Movie
import com.example.primeraclaseandroid.data.MovieListAdapter
import com.example.primeraclaseandroid.data.MovieResponse
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieFragment : Fragment() {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private var peliculasAdapter: MovieListAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    // En los fragmentos en la funcion onViewCreated se llama lo que tenga que ver con las vistas
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getData()
    }

    fun initView(){
        /* Funcion anonima las variables pueden llamar tambien funciones como en la linea 51 */
        val openDetail = { pelicula: Movie ->
            saveMovieInfo(pelicula)
            startActivity(
                Intent(
                    context,
                    LoginActivity::class.java

                ).apply {
                    putExtra(MOVIE_EXTRA, pelicula)
                }
            )
        }

        /* Se puede enviar la funcion anonima definida anteriormente o enviar la funcion en los parametros */
        peliculasAdapter = MovieListAdapter(
            openDetail = openDetail,
            addCart = { id ->
                Snackbar.make(binding.rcvListaPeliculas,"Se añadio la pelicula : $id", Snackbar.LENGTH_LONG).show()
            },
            removeCart = { id ->
                Snackbar.make(binding.rcvListaPeliculas,"Se quito la pelicula : $id", Snackbar.LENGTH_LONG).show()
            }
        )

        binding.rcvListaPeliculas.layoutManager = GridLayoutManager(context,3)
        binding.rcvListaPeliculas.adapter = peliculasAdapter
    }

    fun saveMovieInfo(pelicula: Movie){
        val sharedPreferences = context?.getSharedPreferences("datosMovie", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putInt("id",pelicula.id)
        editor?.putString("Titulo",pelicula.title)
        editor?.putString("Poster",pelicula.poster_path)
        editor?.putString("Fecha",pelicula.release_date)
        editor?.putString("Resumen",pelicula.overview)
        editor?.apply()
    }

    fun getData(){
        val request = ServiceBuilder.serviciosPeliculas()
        val call = request.getMovie("d4fd83da94a8d972b5c733e58151f78e",2)
        binding.cargar.loading.visibility = View.VISIBLE
        call.enqueue(object : Callback<MovieResponse>
        {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                binding.cargar.loading.visibility = View.GONE
                if (response.isSuccessful){
                    response.body()?.let { peliculasAdapter?.setData(it.results) }
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                binding.cargar.loading.visibility = View.GONE
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() =
            MovieFragment()
    }
}