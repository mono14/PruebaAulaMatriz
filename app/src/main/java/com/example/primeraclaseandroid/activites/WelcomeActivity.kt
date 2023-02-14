package com.example.primeraclaseandroid.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.primeraclaseandroid.databinding.ActivityMainBinding

class WelcomeActivity : AppCompatActivity() {
    //lateinit var textView1:TextView
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       /* textView1=findViewById(R.id.textView)
        binding.textView.text = getString(R.string.prueba_texto)*/
        binding.btnEmpezarAhora.setOnClickListener {
            startActivity(
                Intent(this, HomeActivity::class.java)
            )
        }
        binding.txvAcercade.setOnClickListener {
            startActivity(
                Intent(this, AcercaDeActivity::class.java)
            )
        }
    }



}
