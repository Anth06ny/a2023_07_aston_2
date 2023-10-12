package com.amonteiro.a2023_07_aston_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.amonteiro.a2023_07_aston_2.databinding.ActivityWeatherAroundBinding

class WeatherAroundActivity : AppCompatActivity() {

    val binding by lazy { ActivityWeatherAroundBinding.inflate(layoutInflater) }

    //Données
    val list = ArrayList<WindBean>()

    //Outil
    val adapter = WeatherListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Réglages RecyclerView
        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(this, 1)

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

        adapter.submitList(list.toList())
    }
}