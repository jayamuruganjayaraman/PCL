package com.vignesh.pcl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vignesh.pcl.R
import com.vignesh.pcl.model.TournamentEntity

class TournamentBannerAdapter(
    private val onItemClick: (TournamentEntity) -> Unit
) : ListAdapter<TournamentEntity, TournamentBannerAdapter.TournamentViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<TournamentEntity>() {
        override fun areItemsTheSame(oldItem: TournamentEntity, newItem: TournamentEntity): Boolean {
            return oldItem.tournamentId == newItem.tournamentId
        }

        override fun areContentsTheSame(oldItem: TournamentEntity, newItem: TournamentEntity): Boolean {
            return oldItem == newItem
        }
    }

    inner class TournamentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTournamentName: TextView = itemView.findViewById(R.id.tvTournamentName)
        private val tvTournamentLocation: TextView = itemView.findViewById(R.id.tvTournamentLocation)

        fun bind(item: TournamentEntity) {
            tvTournamentName.text = item.name
            tvTournamentLocation.text = item.location

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tournament_list_items, parent, false)
        return TournamentViewHolder(view)
    }

    override fun onBindViewHolder(holder: TournamentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
