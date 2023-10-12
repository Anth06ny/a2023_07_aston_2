package com.amonteiro.a2023_07_aston_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amonteiro.a2023_07_aston_2.databinding.ActivityAmericanSportBinding
import kotlin.concurrent.thread

class AmericanSportActivity : AppCompatActivity() {

    val binding by lazy { ActivityAmericanSportBinding.inflate(layoutInflater) }

    //Données
    val model by lazy { ViewModelProvider(this).get(AmericanSportViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        refreshScreen()

        binding.btLoad.setOnClickListener {

            binding.progressBar.isVisible = true

            //Tache asynchrone
            thread {
                model.loadData()
                runOnUiThread {
                    refreshScreen()
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    fun refreshScreen() {
        //cas qui marche
        val res = model.data.map { it.description }.sortedBy { it }.joinToString("\n", "Liste des sports : ") { it.toString() }
        binding.textView.text = res

        //cas d'erreur
        binding.tvError.text = model.errorMessage
        binding.tvError.isVisible = model.errorMessage.isNotBlank()

    }
}

class AmericanSportViewModel : ViewModel() {
    var data = ArrayList<SportBean>()
    var errorMessage = ""


    fun loadData() {
        data.clear()
        errorMessage = ""

        try {
            data.addAll(AmericanSportAPI.loadAllSports())
        }
        catch (e: Exception) {
            e.printStackTrace()
            errorMessage = "Une erreur est survenue ${e.message}"
        }

    }
}
