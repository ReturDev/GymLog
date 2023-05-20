package com.sergio.gymlog.ui.main.home.selector.adapter

import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergio.gymlog.R
import com.sergio.gymlog.data.model.exercise.MuscularGroup
import com.sergio.gymlog.databinding.IconItemBinding

class IconAdapter(
    private val muscularGroups : List<MuscularGroup>
) : RecyclerView.Adapter<IconAdapter.IconHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.icon_item, parent, false)
        return IconHolder(view)
    }

    override fun getItemCount() = muscularGroups.size

    override fun onBindViewHolder(holder: IconHolder, position: Int) {
        holder.bindIcon(muscularGroups[position].iconResource)
    }

    inner class IconHolder(view : View) : RecyclerView.ViewHolder(view){

        private val binding = IconItemBinding.bind(view)

        fun bindIcon(iconSource : Int) {
            Glide.with(binding.root.context)
                .load(iconSource)
                .into(binding.ivIconItem)
        }


    }

}