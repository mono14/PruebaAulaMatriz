package com.example.primeraclaseandroid

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.net.URL

//se van a crear funciones estaticas

//se creo una funcion de extencion para el cargue de imagenes
fun ImageView.load(
    url:String
){
    Glide.with(this.context).load(BASE_URL_RESOURCES+url).into(this)
}
const val BASE_URL_RESOURCES = "https://image.tmdb.org/t/p/w342"