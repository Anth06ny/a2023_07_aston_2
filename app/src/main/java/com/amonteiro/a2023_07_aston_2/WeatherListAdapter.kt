package com.amonteiro.a2023_07_aston_2

import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.amonteiro.a2023_07_aston_2.databinding.RowWeatherBinding


class WeatherListAdapter : ListAdapter<WindBean, WeatherListAdapter.ViewHolder>(Comparator()) { //3

    //1 Class référençant les composants graphiques
    class ViewHolder(val bind: RowWeatherBinding) : RecyclerView.ViewHolder(bind.root)

    //2 Permet de comparer des listes pour les animations
    class Comparator : DiffUtil.ItemCallback<WindBean>() {
        override fun areItemsTheSame(oldItem: WindBean, newItem: WindBean)
                = oldItem === newItem

        override fun areContentsTheSame(oldItem: WindBean, newItem: WindBean)
                = oldItem == newItem
    }

    //Instanciation d'une ligne de row_student.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(RowWeatherBinding.inflate(LayoutInflater.from(parent.context)))


    //Remplissage des composants graphiques
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind.tvtemp.text = "${current.speed}"
    }
}