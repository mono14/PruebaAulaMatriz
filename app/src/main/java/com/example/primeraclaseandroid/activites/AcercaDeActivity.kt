package com.example.primeraclaseandroid.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.primeraclaseandroid.databinding.ActivityAcercaDeBinding

class AcercaDeActivity: AppCompatActivity() {
    lateinit var binding : ActivityAcercaDeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcercaDeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}