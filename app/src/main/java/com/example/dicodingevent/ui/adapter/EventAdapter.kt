package com.example.dicodingevent.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.network.response.Events
import com.example.dicodingevent.databinding.EventCardBinding

class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val events = mutableListOf<Events>()

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
        events.clear()
        events.addAll(newEvents)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EventViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
        holder.itemView.setOnClickListener{
            onItemClickCallback?.invoke(event)
        }
    }
}