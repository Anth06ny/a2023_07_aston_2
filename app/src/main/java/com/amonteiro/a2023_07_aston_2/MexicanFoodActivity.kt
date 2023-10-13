package com.amonteiro.a2023_07_aston_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.amonteiro.a2023_07_aston_2.databinding.ActivityMexicanFoodBinding
import kotlin.concurrent.thread


class MexicanFoodActivity : AppCompatActivity() {



    val binding by lazy { ActivityMexicanFoodBinding.inflate(layoutInflater) }
    val model by lazy { ViewModelProvider(this)[MexicanFoodViewModel::class.java] }

    val adapter = MexicanListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rv.layoutManager = GridLayoutManager(this, 2)
        binding.rv.adapter = adapter

        model.listLiveData.observe(this){
            adapter.submitList(it)
        }

        model.errorLiveData.observe(this){
            binding.tvError.isVisible = it.isNotBlank()
            binding.tvError.text = it
        }


        model.runInProgress.observe(this){
            binding.progressBar2.isVisible = it
        }

        model.loadData()

    }



}

class MexicanFoodViewModel : ViewModel(){
    val listLiveData = MutableLiveData<List<MexicanFoodTitleBean>>(ArrayList())
    val errorLiveData = MutableLiveData("")
    val runInProgress = MutableLiveData(false)

    fun loadData(){
        if(listLiveData.value.isNullOrEmpty()) {
            runInProgress.postValue(true)

            thread {
                try {
                    listLiveData.postValue(MexicanFoodAPI.loadListFood())
                }
                catch (e: Exception) {
                    e.printStackTrace()
                    errorLiveData.postValue("Une erreur est survenue : "  + e.message)
                }
                runInProgress.postValue(false)

            }
        }
    }
}