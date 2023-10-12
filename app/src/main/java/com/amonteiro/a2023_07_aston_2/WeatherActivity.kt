package com.amonteiro.a2023_07_aston_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
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

        observe()
        binding.btLoad.setOnClickListener {
            var city = binding.et.text.toString()
            model.loadData(city)
        }
    }

    fun observe() {
        model.data.observe(this) {
            binding.tv.text = "Il fait ${it?.main?.temp ?: "-"}° à ${it?.name ?: "-"} avec un vent de ${it?.wind?.speed ?: "-"} m/s"
            it?.weather?.getOrNull(0)?.icon?.let {
                Picasso.get().load("https://openweathermap.org/img/wn/$it@4x.png").into(binding.imageView);
            }
        }

        model.errorMessage.observe(this) {
            binding.tvError.text = it
            binding.tvError.isVisible = it.isNotBlank()
        }

        model.runInProgress.observe(this) {
            binding.progressBar.isVisible = it
        }
    }

}

class WeatherViewModel : ViewModel() {
    //var data : WeatherBean? = null
    var data = MutableLiveData<WeatherBean?>(null)
    var errorMessage = MutableLiveData("")
    var runInProgress = MutableLiveData(false)

    fun loadData(cityName: String) {
        //reset de donnée
        errorMessage.postValue("")
        data.postValue(null)
        runInProgress.postValue(true)

        thread {
            try {
                //cas qui marche
                data.postValue(RequestUtils.loadWeather(cityName))
            }
            catch (e: Exception) {
                //Cas qui ne marche pas
                e.printStackTrace()
                errorMessage.postValue("Erreur : ${e.message}")
            }
            runInProgress.postValue(false)
        }
    }

}