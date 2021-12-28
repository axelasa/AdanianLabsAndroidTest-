package com.axel.adanianlabstest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axel.adanianlabstest.R
import com.axel.adanianlabstest.models.Dogs
import com.axel.adanianlabstest.models.Hit
import com.bumptech.glide.Glide

class ImageAdapter (private val data:List<Hit>,private val listener:OnItemClickListener): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){

    inner class ImageViewHolder(val view: View):RecyclerView.ViewHolder(view),View.OnClickListener{

        fun bind(hits:Hit){
            val title = view.findViewById<TextView>(R.id.title)
            val imageView = view.findViewById<ImageView>(R.id.image)
            val description = view.findViewById<TextView>(R.id.description)
            title.text = hits.user//title of image (user)
            description.text = hits.tags // the description of the image

          Glide.with(view.context).load(hits.webformatURL).centerCrop().into(imageView)// the image

        }
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position:Int = bindingAdapterPosition
            if (position!= RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.dogs_list,parent,false)
        return ImageViewHolder(v)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}