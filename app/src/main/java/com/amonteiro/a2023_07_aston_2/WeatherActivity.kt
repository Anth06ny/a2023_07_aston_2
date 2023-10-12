package com.amonteiro.a2023_07_aston_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amonteiro.a2023_07_aston_2.databinding.ActivityWeatherBinding
import com.squareup.picasso.Picasso
import kotlin.concurrent.thread

class WeatherActivity : AppCompatActivity() {

    //instancie les composants graphiques à la 1er utilisation grâce à lazy
    val binding by lazy { ActivityWeatherBinding.inflate(layoutInflater) }

    //Données
   val model by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val value = intent.getStringExtra("cle")
//        binding.tv.setText(value)

        binding.btLoad.setOnClickListener {

            //Avant car c'est un composant graphique
            var city = binding.et.text.toString()
            binding.progressBar.isVisible = true

            thread {
                model.loadData(city)
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
        binding.tv.text = "Il fait ${model.data?.main?.temp ?: "-"}° à ${model.data?.name ?: "-"} avec un vent de ${model.data?.wind?.speed ?: "-"} m/s"
        model.data?.weather?.getOrNull(0)?.icon?.let {
            Picasso.get().load("https://openweathermap.org/img/wn/$it@4x.png").into(binding.imageView);
        }

        //Message d'erreur
        binding.tvError.text =  model.errorMessage ?: ""
        binding.tvError.isVisible =  !model.errorMessage.isNullOrBlank()

//        val errorMessage = model.errorMessage
//        if(errorMessage!= null) {
//            binding.tvError.isVisible =  true
//            binding.tvError.text =  errorMessage
//        }
//        else {
//            binding.tvError.isVisible =  false
//        }

    }
}

class WeatherViewModel : ViewModel(){
    var data : WeatherBean? = null
    var errorMessage:String? = null

    fun loadData(cityName:String){
        //reset de donnée
        errorMessage = null
        data = null


        try {
            //cas qui marche
            data = RequestUtils.loadWeather(cityName)
        }
        catch (e: Exception) {
            //Cas qui ne marche pas
            e.printStackTrace()
            errorMessage = "Erreur : ${e.message}"
        }
    }

}