package com.example.primeraclaseandroid.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.primeraclaseandroid.R
import com.example.primeraclaseandroid.databinding.ItemMovieBinding
import com.example.primeraclaseandroid.load

/*
se encarga de poner la lista en la  vista
siempre se hereda de RecyclerView.Adapter<RecyclerView.ViewHolder>()
 */
class MovieListAdapter(
    private val addCart:(idMovie : Int) -> Unit,
    private val removeCart:(idMovie : Int) -> Unit,
    private val openDetail:(movie: Movie) -> Unit

) : RecyclerView.Adapter<PeliculasViewHolder>() {

    val listMovie = ArrayList<Movie>()

    //se cambio el tipo de variable de arraylist a list para que leyera y pudiera graficar 
    fun setData(listMovie: List<Movie>){
        this.listMovie.clear()
        this.listMovie.addAll(listMovie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return PeliculasViewHolder(

            layoutInflater.inflate(
                R.layout.item_movie,
                parent,
                false
            )

        )

    }

    override fun onBindViewHolder(holder: PeliculasViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.initData(movie)
        holder.initListener(addCart, removeCart, openDetail, movie)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

}
/*

 */
class PeliculasViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView) {

    val binding = ItemMovieBinding.bind(itemView)

    fun initData(movie : Movie){
        //Glide.with(itemView.context).load(movie.poster_path).into(binding.posteMovie)
        binding.posteMovie.load(movie.poster_path)
    }

    fun initListener(
        addCart:(idMovie : Int) -> Unit,
        removeCart:(idMovie : Int) -> Unit,
        openDetail:(movie: Movie) -> Unit,
        movie : Movie
    ){
        binding.btnAnadir.setOnClickListener {
            addCart(movie.id)
            binding.btnAnadir.visibility=View.GONE
            binding.btnQuitar.visibility=View.VISIBLE
          //Toast.makeText(itemView.context, "pelicula : ${movie.titulo}", Toast.LENGTH_LONG).show()
        }

        binding.btnQuitar.setOnClickListener {
            binding.btnAnadir.visibility=View.VISIBLE
            binding.btnQuitar.visibility=View.GONE
            removeCart(movie.id)
        }

        binding.posteMovie.setOnClickListener {
            openDetail(movie)
        }
    }
}