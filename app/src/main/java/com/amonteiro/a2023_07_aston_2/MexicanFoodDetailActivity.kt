package com.amonteiro.a2023_07_aston_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.amonteiro.a2023_07_aston_2.databinding.ActivityWeatherBinding
import com.squareup.picasso.Picasso
import kotlin.concurrent.thread

class MexicanFoodDetailActivity : AppCompatActivity() {

    //ON utilise le même XML que dans Weather Activity
    val binding by lazy { ActivityWeatherBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btLoad.setOnClickListener {

            binding.progressBar.isVisible = true
            binding.tvError.isVisible = false

            val id = binding.et.text.toString()

            //Tache asynchrone
            thread {
                try {
                    val food = MexicanFoodAPI.loadFoodById(id.toInt())

                    //Mise à jour graphique
                    runOnUiThread {
                        binding.tv.text = food.title + "\n\n" + food.description
                        binding.progressBar.isVisible = false

                        //Charge l'image à partir d'une URL
                        Picasso.get().load(food.image).into(binding.imageView)
                    }
                }
                catch(e:Exception) {
                    e.printStackTrace()
                    //Mise à jour graphique
                    runOnUiThread {
                        binding.tvError.text = "Une erreur est survenue ${e.message}"
                        binding.tvError.isVisible = true
                        binding.progressBar.isVisible = false
                    }
                }
            }

        }

        val id = intent.getIntExtra("id", -1)
        if(id != -1) {
            //ici je mets l'entier sous forme de string pour ne pas déclancher l'apper au String resources (R.string.blabla)
            binding.et.setText("$id")
            //Déclanche le clic du bouton
            binding.btLoad.performClick()
        }
    }
}