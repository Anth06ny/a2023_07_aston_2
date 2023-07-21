package com.amonteiro.a2023_07_aston_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.amonteiro.a2023_07_aston_2.databinding.ActivityWeatherBinding
import com.squareup.picasso.Picasso
import kotlin.concurrent.thread

class WeatherActivity : AppCompatActivity() {

    //instancie les composants graphiques à la 1er utilisation grâce à lazy
    val binding by lazy { ActivityWeatherBinding.inflate(layoutInflater) }

    //Données
    var data : WeatherBean? = null
    var errorMessage:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val value = intent.getStringExtra("cle")
//        binding.tv.setText(value)

        binding.btLoad.setOnClickListener {

            //Avant car c'est un composant graphique
            var city = binding.et.text.toString()
            binding.progressBar.isVisible = true

            //reset de donnée
            errorMessage = null
            data = null

            thread {
                try {
                    //cas qui marche
                    data = RequestUtils.loadWeather(city)
                }
                catch (e: Exception) {
                    //Cas qui ne marche pas
                    e.printStackTrace()
                    errorMessage = "Erreur : ${e.message}"
                }

                //J'actualise l'écran
                runOnUiThread {
                    binding.progressBar.isVisible = false
                    refreshScreen()
                }

            }

        }

        refreshScreen()
    }


    fun refreshScreen(){
        //Data
        binding.tv.text = "Il fait ${data?.main?.temp ?: "-"}° à ${data?.name ?: "-"} avec un vent de ${data?.wind?.speed ?: "-"} m/s"
        data?.weather?.getOrNull(0)?.icon?.let {
            Picasso.get().load("https://openweathermap.org/img/wn/$it@4x.png").into(binding.imageView);
        }

        //Message d'erreur
        if(errorMessage!= null) {
            binding.tvError.isVisible =  true
            binding.tvError.text =  errorMessage
        }
        else {
            binding.tvError.isVisible =  false
        }

    }
}