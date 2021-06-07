package com.capstone.hibykes.ui.listStation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.hibykes.data.local.entity.StationEntity
import com.capstone.hibykes.databinding.ItemListStationBinding

class ListStationAdapter(private val listStations: List<StationEntity>) : RecyclerView.Adapter<ListStationAdapter.ListStationViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: StationEntity)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListStationViewHolder(private val binding: ItemListStationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(station: StationEntity) {
            with(binding) {
                tvItemName.text = station.name
                tvItemDesc.text = station.description
                Glide.with(itemView.context)
                    .load(station.image)
                    .into(imgItemPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListStationViewHolder {
        val itemListStationBinding = ItemListStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListStationViewHolder(itemListStationBinding)
    }

    override fun onBindViewHolder(holder: ListStationViewHolder, position: Int) {
        val station = listStations[position]
        holder.bind(station)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listStations[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listStations.size
}