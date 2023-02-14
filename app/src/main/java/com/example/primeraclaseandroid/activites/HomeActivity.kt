package com.example.primeraclaseandroid.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.primeraclaseandroid.R
import com.example.primeraclaseandroid.fragments.MapaFragment
import com.example.primeraclaseandroid.fragments.MovieFragment
import com.example.primeraclaseandroid.fragments.PerfilFragment
import com.example.primeraclaseandroid.databinding.ActivityHomeBinding
import com.example.primeraclaseandroid.fragments.ReadSmsFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment(MovieFragment.newInstance())
        initMenu()
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contenedorHome, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initMenu(){
        binding.menuInferior.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.peliculasFragment -> {
                    openFragment(MovieFragment.newInstance())
                    true
                }
                R.id.smsFragment -> {
                    openFragment(ReadSmsFragment.newInstance())
                    true
                }
                R.id.mapaFragment -> {
                    openFragment(MapaFragment.newInstance())
                    true
                }
                R.id.perfilFragment -> {
                    openFragment(PerfilFragment.newInstance())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }



    /*
    var peliculaAdapter : MovieListAdapter ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val openDetail = { movie : Movie ->
            startActivity(

                Intent(
                    this,
                    DetailMovieActivity::class.java
                ).apply {
                    putExtra(MOVIE_EXTRA, movie)
                }
            )
        }

        peliculaAdapter = MovieListAdapter(
            openDetail = openDetail,
            addCart = { id ->
                Snackbar.make(binding.rcvListaPelicula,"se añadio la pelicula : $id",Snackbar.LENGTH_LONG).show()
            },
            removeCart = { id ->
                Snackbar.make(binding.rcvListaPelicula,"se quito la pelicula : $id ",Snackbar.LENGTH_LONG).show()
            }
        )

        binding.rcvListaPelicula.layoutManager = GridLayoutManager(this,3)
        binding.rcvListaPelicula.adapter = peliculaAdapter
       // peliculaAdapter.setData(MovieData.getData())
        getData()

    }
    fun getData(){
        val request = ServiceBuilder.serviciosPeliculas()
        val call = request.getMovie("c3d2e2b49ca29cd75509e102b6b326ab",1)



        call.enqueue(object : Callback<MovieResponse>
            {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    binding.loading.loading.visibility = View.GONE
                    if (response.isSuccessful){

                        response.body()?.let { peliculaAdapter?.setData(it.results) }

                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    binding.loading.loading.visibility = View.GONE
                    Toast.makeText(this@HomeActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    /*override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }*/
*/
}

const val MOVIE_EXTRA = "movie_extra"
