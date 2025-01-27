package com.example.dicodingevent.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.network.response.Events
import com.example.dicodingevent.databinding.EventCardBinding

class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {


    private var onItemClickCallback: ((Events) -> Unit)? = null

    fun setOnItemClickCallback(callback: (Events) -> Unit) {
        onItemClickCallback = callback
    }

    class EventViewHolder(private val binding:EventCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(event: Events){
            with(binding){
                tvEventName.text = event.name
                tvOwnerName.text = event.ownerName
                tvCityName.text = event.cityName
                Glide.with(itemView.context)
                    .load(event.mediaCover)
                    .centerCrop()
                    .into(imgEvent)
            }
        }
    }

    fun setData(newEvents: List<Events>) {
        differ.submitList(newEvents)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EventViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = differ.currentList[position]
        holder.bind(event)
        holder.itemView.setOnClickListener {
            onItemClickCallback?.invoke(event)
        }
        holder.setIsRecyclable(false)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Events>(){
        override fun areItemsTheSame(oldItem: Events, newItem: Events): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Events, newItem: Events): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

}