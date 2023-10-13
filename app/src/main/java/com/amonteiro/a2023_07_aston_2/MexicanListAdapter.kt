package com.amonteiro.a2023_07_aston_2

import android.content.Intent
import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.amonteiro.a2023_07_aston_2.databinding.RowWeatherBinding
import com.squareup.picasso.Picasso


class MexicanListAdapter : ListAdapter<MexicanFoodTitleBean, MexicanListAdapter.ViewHolder>(Comparator()) { //3

    //1 Class référençant les composants graphiques
    class ViewHolder(val bind: RowWeatherBinding) : RecyclerView.ViewHolder(bind.root)

    //2 Permet de comparer des listes pour les animations
    class Comparator : DiffUtil.ItemCallback<MexicanFoodTitleBean>() {
        override fun areItemsTheSame(oldItem: MexicanFoodTitleBean, newItem: MexicanFoodTitleBean)
                = oldItem === newItem

        override fun areContentsTheSame(oldItem: MexicanFoodTitleBean, newItem: MexicanFoodTitleBean)
                = oldItem == newItem
    }

    //Instanciation d'une ligne de row_student.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(RowWeatherBinding.inflate(LayoutInflater.from(parent.context)))


    //Remplissage des composants graphiques
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind.tvville.text = current.title
        holder.bind.tvtemp.text = current.difficulty
        Picasso.get().load(current.image).into(holder.bind.imageView)

        holder.bind.main.setOnClickListener {
            //Changement d'écran et je passe l'id
            val intent = Intent(holder.bind.tvville.context, MexicanFoodDetailActivity::class.java)
            intent.putExtra("id", current.id)
            holder.bind.tvville.context.startActivity(intent)
        }
    }
}