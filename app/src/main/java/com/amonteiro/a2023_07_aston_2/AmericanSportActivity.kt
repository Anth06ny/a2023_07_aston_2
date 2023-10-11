package com.amonteiro.a2023_07_aston_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.amonteiro.a2023_07_aston_2.databinding.ActivityAmericanSportBinding
import kotlin.concurrent.thread

class AmericanSportActivity : AppCompatActivity() {

    val binding by lazy { ActivityAmericanSportBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btLoad.setOnClickListener {

            binding.progressBar.isVisible = true

            //Tache asynchrone
            thread {
                try {
                    val tab = AmericanSportAPI.loadAllSports()
                    val res = tab.map { it.description }.sortedBy { it }.joinToString("\n", "Liste des sports : ") { it.toString() }

                    //Mise à jour graphique
                    runOnUiThread {
                        binding.textView.text = res
                        binding.progressBar.isVisible = false
                    }
                }
                catch(e:Exception) {
                    e.printStackTrace()
                    //Mise à jour graphique
                    runOnUiThread {
                        binding.textView.text = "Une erreur est survenue ${e.message}"
                        binding.progressBar.isVisible = false
                    }
                }
            }

        }
    }


}