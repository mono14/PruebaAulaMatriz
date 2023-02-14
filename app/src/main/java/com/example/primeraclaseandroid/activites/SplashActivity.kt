package com.example.primeraclaseandroid.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.primeraclaseandroid.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //startActivity(Intent(this,WelcomeActivity::class.java))
        if (getInfoEmpezar()){
            startActivity(Intent(this,HomeActivity::class.java))
        }else{
            saveInfoEmpezar()
            startActivity(Intent(this,WelcomeActivity::class.java))
        }
    }

    fun saveInfoEmpezar(){
        val sharedPreferences = applicationContext.getSharedPreferences("datos-inicio", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(EMPEZAR_PREFERENCE,true)
        editor.apply()
    }

    fun getInfoEmpezar(): Boolean {
        val sharedPreferences =
            applicationContext.getSharedPreferences("datos-inicio", MODE_PRIVATE)
        return sharedPreferences.getBoolean(EMPEZAR_PREFERENCE, false)
    }
}

const val EMPEZAR_PREFERENCE="empezar"