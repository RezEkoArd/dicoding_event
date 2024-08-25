package com.vsga.dicodingevent.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vsga.dicodingevent.data.response.ListEventsItem
import com.vsga.dicodingevent.databinding.ItemRowBinding

class AdapterEventList : RecyclerView.Adapter<AdapterEventList.ViewHolder>() {

    private val listEvent = ArrayList<ListEventsItem>()

    fun getEvent(event: List<ListEventsItem>){
        listEvent.clear()
        listEvent.addAll(event)
        notifyDataSetChanged()
        Log.d("TAG", "cek item masuk $listEvent")
    }

    inner class ViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root){
        internal fun bind(event: ListEventsItem){
            binding.tvItemName.text = event.name
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .centerCrop()
                .into(binding.imgPhotoItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listEvent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = listEvent[position]
        holder.bind(event)
    }


}