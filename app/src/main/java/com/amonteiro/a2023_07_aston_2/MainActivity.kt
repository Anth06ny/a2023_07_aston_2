package com.amonteiro.a2023_07_aston_2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.amonteiro.a2023_07_aston_2.databinding.ActivityMainBinding

const val MENU_WEATHER = 1
const val MENU_SPORT = 2
const val MENU_MEXICAN = 3
const val MENU_RV = 4
const val MENU_MAPS = 5

class MainActivity : AppCompatActivity() {

    //instancie les composants graphiques à la 1er utilisation grâce à lazy
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //Callbck de la création de l'écran
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Affiche une interface graphique
        setContentView(binding.root)

        //Callback du clic sur le bouton
        binding.btValidate.setOnClickListener {
            if (binding.rbLike.isChecked) {
                binding.et.setText(binding.rbLike.text)
            }
            else if (binding.rbDislike.isChecked) {
                binding.et.setText(binding.rbDislike.text)
            }

            binding.iv.setImageResource(R.drawable.baseline_flag_24)
            binding.iv.setColorFilter(Color.CYAN)

        }

        binding.btCancel.setOnClickListener {
            binding.rg.clearCheck()
            binding.et.setText("")
            binding.iv.setImageResource(R.drawable.baseline_delete_forever_24)
        }

    }



    //Callback création du menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(0, MENU_WEATHER, 0, "Météo")
        menu.add(0, MENU_SPORT, 0, "Sport")
        menu.add(0, MENU_MEXICAN, 0, "Meixcan food")
        menu.add(0, MENU_RV, 0, "RecyclerView")
        menu.add(0, MENU_MAPS, 0, "ISS")

        return super.onCreateOptionsMenu(menu)
    }

    //callback des clics sur le menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == MENU_WEATHER) {
            val intent = Intent(this, WeatherActivity::class.java)
            intent.putExtra("cle", binding.et.text.toString())

            startActivity(intent)
        }
        else if(item.itemId == MENU_SPORT) {
            val intent = Intent(this, AmericanSportActivity::class.java)
            startActivity(intent)
        }
        else if(item.itemId == MENU_MEXICAN) {
            val intent = Intent(this, MexicanFoodActivity::class.java)
            startActivity(intent)
        }
        else if(item.itemId == MENU_RV) {
            val intent = Intent(this, WeatherAroundActivity::class.java)
            startActivity(intent)
        }
        else if(item.itemId == MENU_MAPS) {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


}