package com.example.primeraclaseandroid.activites

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.primeraclaseandroid.data.contentprovider.MovieProvider
import com.example.primeraclaseandroid.databinding.ActivityContentProviderBinding
import com.example.primeraclaseandroid.utils.Constants

class ContentProviderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentProviderBinding
    private val uriContentProvider: Uri = Uri.parse("content://${"com.example.primeraclaseandroid.providers"}/movie")

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityContentProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inserting data into content provider
        val tuple = ContentValues()
        tuple.put(Constants.COLUMN_NAME, "Alerta Roja")
        contentResolver.insert(uriContentProvider, tuple)

        // Reading from content provider
        val cols = arrayOf(Constants.COLUMN_ID, Constants.COLUMN_NAME)
        val uri = uriContentProvider
        val cursor = contentResolver.query(uri, cols, null, null, null)
        if (cursor?.moveToFirst() == true)
            binding.resultContentProvider.text =
                "Nombre pelicula: ${(cursor?.getString(cursor.getColumnIndex(Constants.COLUMN_NAME)) ?: String())}"
        else
            binding.resultContentProvider.text = "Acceso denegado"
    }
}