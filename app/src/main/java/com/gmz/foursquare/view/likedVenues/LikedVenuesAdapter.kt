package com.gmz.foursquare.view.likedVenues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmz.foursquare.R
import com.gmz.foursquare.data.entities.Item
import javax.inject.Inject

class LikedVenuesAdapter @Inject constructor():RecyclerView.Adapter<LikedVenuesAdapter.VenuesViewHolder>() {

    var onItemClick: ((Item) -> Unit)? = null
    var venues: List<Item> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VenuesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_liked_venues, parent, false)
    )

    override fun onBindViewHolder(holder: VenuesViewHolder, position: Int) {
        holder.bind(venues.get(position))
    }

    override fun getItemCount() = venues.size

    inner class VenuesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.tv_itemLikedVenues_name)
        private val hint = view.findViewById<TextView>(R.id.tv_itemLikedVenues_hint)

        fun bind(item: Item) {
            name.text = item.name
            hint.text = item.tipHint
            itemView.setOnClickListener {
                onItemClick?.let { clicked -> clicked(item) }
            }
        }
    }
}