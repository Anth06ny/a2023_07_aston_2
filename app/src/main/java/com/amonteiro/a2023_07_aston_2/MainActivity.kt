package com.amonteiro.a2023_07_aston_2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amonteiro.a2023_07_aston_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //instancie les composants graphiques à la 1er utilisation grâce à lazy
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    //Callbck de la création de l'écran
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Affiche une interface graphique
        setContentView(binding.root)

        binding.btValidate.setOnClickListener {
            binding.et.setText("Clic sur valider")
        }

        binding.btCancel.setOnClickListener(this)

    }

    //callback d'un click sur le bouton
    override fun onClick(v: View?) {
        if(v === binding.btCancel) {
            binding.et.setText("Clic sur annuler")
        }
    }

}