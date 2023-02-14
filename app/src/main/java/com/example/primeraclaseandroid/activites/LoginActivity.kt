package com.example.primeraclaseandroid.activites

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.primeraclaseandroid.data.Movie
import com.example.primeraclaseandroid.databinding.ActivityLoginBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var mAuth : FirebaseAuth
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.btnCreate.setOnClickListener {
            goRegister()
        }

        //recoveryLoginInfo()
        recoveryLoginEncryptedInfo()

        initLoadAds()

        //una clase abstracta
        binding.bannerGoogle.adListener = object : AdListener() {

            override fun onAdLoaded() {
                Toast.makeText(this@LoginActivity, "Cargué el banner", Toast.LENGTH_SHORT).show()
            }
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Toast.makeText(this@LoginActivity, "Falló el banner", Toast.LENGTH_SHORT).show()
            }
            override fun onAdOpened() {
                Toast.makeText(this@LoginActivity, "Abrí el contenido del banner", Toast.LENGTH_SHORT).show()
            }
            override fun onAdClicked() {
                Toast.makeText(this@LoginActivity, "Hice click en el banner", Toast.LENGTH_SHORT).show()
            }

            override fun onAdClosed() {
                Toast.makeText(this@LoginActivity, "Cerré el contenido del banner", Toast.LENGTH_SHORT).show()
            }
            override fun onAdImpression() {
                Toast.makeText(this@LoginActivity, "Se visualizó el banner", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser() {
        var email = binding.edtEmail.text.toString()
        var pass = binding.edtPass.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty()){
                binding.loadingInclude.loading.visibility = View.VISIBLE
                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    task ->
                        binding.loadingInclude.loading.visibility = View.GONE
                        if(task.isSuccessful){
                            registerEvenlogin(email,binding.chkRemember.isChecked)
                            rememberLoginInfo(email,pass)
                            rememberMovi()
                        } else {

                            Toast.makeText(this, "Los datos ingresados no coinciden," +
                                    " por favor revise nuevamente.", Toast.LENGTH_SHORT).show()
                        }
                }
        } else {
            Toast.makeText(this, "Por Favor ingrese todos los datos solicitados. ", Toast.LENGTH_SHORT).show()
        }

    }

    fun rememberMovi(){
        val sharedPreferences = applicationContext.getSharedPreferences("datosmovie", MODE_PRIVATE)
        val movie = sharedPreferences.getInt("ID",0)
        val tituloMovie = sharedPreferences.getString("Titulo","")
        val poster = sharedPreferences.getString("poster","")
        val fecha = sharedPreferences.getString("Fecha","")
        val resumen = sharedPreferences.getString("Resumen","")
        if(movie == 0){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }else {
            val pelicula = Movie(
                id = movie,
                title = tituloMovie ?: "",
                poster_path = poster ?: "",
                release_date = fecha,
                overview = resumen ?: "",
                vote_average = 0.0f
            )
            startActivity(
                Intent(
                    this, DetailMovieActivity::class.java
                ).apply { putExtra(MOVIE_EXTRA, pelicula) }
            )
        }
    }
//registrar eventos dentro de firebase
    private fun registerEvenlogin(email: String, remember: Boolean){
        val params = Bundle()
        params.putString("email",email)
        if (remember){
            params.putString("remember","Si")
        }else{
            params.putString("remember","No")
        }
        firebaseAnalytics.logEvent("login",params)
    }
    private fun goHome(){
        val  intent= Intent(this, DetailMovieActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun goRegister(){
        val  intent= Intent(this, RegisterActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
    fun rememberLoginInfo(email:String,password:String){
        if (binding.chkRemember.isChecked){
            saveNoEncryption(email, password)
            saveEncryption(email,password)
        }else{
            saveNoEncryption("", "")
            saveEncryption("", "")
        }
    }

    fun saveNoEncryption(email:String,password:String){
        val sharedPreferences  = applicationContext.getSharedPreferences("datos", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(EMAIL_PREFERENCE,email)
        editor.putString(PASSWORD_PREFERENCE,password)
        editor.apply()
    }

    fun saveEncryption(email: String,password: String){
        val sharedPreferences = getSharedPreferenceEncrypted()
        sharedPreferences.edit()
            .putString(EMAIL_PREFERENCE,email)
            .putString(PASSWORD_PREFERENCE,password)
            .apply()
    }

    fun recoveryLoginInfo(){
        val sharedPreferences=applicationContext.getSharedPreferences("datos", MODE_PRIVATE)
        val email = sharedPreferences.getString(EMAIL_PREFERENCE,"")
        val password =sharedPreferences.getString(PASSWORD_PREFERENCE,"")
        if(email?.isNotEmpty() == true){
            binding.edtEmail.setText(email)
            binding.edtPass.setText(password)
            binding.chkRemember.isChecked = true
        }

    }
    fun recoveryLoginEncryptedInfo(){
        val sharedPreferences = getSharedPreferenceEncrypted()
        val email = sharedPreferences.getString(EMAIL_PREFERENCE,"")
        val password =sharedPreferences.getString(PASSWORD_PREFERENCE,"")
        if(email?.isNotEmpty() == true){
            binding.edtEmail.setText(email)
            binding.edtPass.setText(password)
            binding.chkRemember.isChecked = true
        }
    }

    fun getSharedPreferenceEncrypted(): SharedPreferences {
        val masterKey = MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences(
            this,
            "datos_encriptados",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun initLoadAds(){
        val adRequest= AdRequest.Builder().build()
        binding.bannerGoogle.loadAd(adRequest)
    }
}
private const val EMAIL_PREFERENCE="email"
private const val PASSWORD_PREFERENCE="password"