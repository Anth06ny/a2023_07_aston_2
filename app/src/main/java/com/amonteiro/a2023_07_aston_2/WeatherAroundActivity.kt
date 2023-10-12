package com.amonteiro.a2023_07_aston_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amonteiro.a2023_07_aston_2.databinding.ActivityWeatherAroundBinding

class WeatherAroundActivity : AppCompatActivity() {

    val binding by lazy { ActivityWeatherAroundBinding.inflate(layoutInflater) }

    //Données
    val list = ArrayList<WindBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btAdd.setOnClickListener {
            //Modification des données
            list.add(0, WindBean(list.size.toDouble()))
            //Affichage
            refreshScreen()
        }

        binding.btDelete.setOnClickListener {
            //Modification des données
            list.removeFirstOrNull()
            //Affichage
            refreshScreen()
        }
    }

    fun refreshScreen() {
        binding.tv.text = list.joinToString("\n") { "-${it.speed}" }
    }
}