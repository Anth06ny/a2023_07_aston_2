package com.amonteiro.a2023_07_aston_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amonteiro.a2023_07_aston_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //instancie les composants graphiques à la 1er utilisation grâce à lazy
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    //Callbck de la création de l'écran
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Affiche une interface graphique
        setContentView(binding.root)

        //Callback du clic sur le bouton
        binding.btValidate.setOnClickListener {
            if(binding.rbLike.isChecked) {
                binding.et.setText(binding.rbLike.text)
            }
            else if(binding.rbDislike.isChecked) {
                binding.et.setText(binding.rbDislike.text)
            }

            binding.iv.setImageResource(R.drawable.baseline_flag_24)
            binding.iv.setColorFilter(getColor(R.color.myblue))
        }

        binding.btCancel.setOnClickListener {
            binding.rg.clearCheck()
            binding.et.setText("")
            binding.iv.setImageResource(R.drawable.baseline_delete_forever_24)
        }

    }


}