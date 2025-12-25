package com.vignesh.pcl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vignesh.pcl.R
import com.vignesh.pcl.model.MatchEntity
import java.text.SimpleDateFormat
import java.util.*

class MatchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val matches = mutableListOf<MatchEntity>()

    companion object {
        private const val TYPE_ROUND_HEADER = 0
        private const val TYPE_MATCH = 1
    }

    fun setData(list: List<MatchEntity>) {
        matches.clear()
        matches.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 || matches[position].roundNumber != matches[position - 1].roundNumber)
            TYPE_ROUND_HEADER else TYPE_MATCH
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ROUND_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_round_header, parent, false)
            RoundHeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_match_schedule, parent, false)
            MatchViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val match = matches[position]
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

        if (holder is RoundHeaderViewHolder) {
            holder.roundTitle.text = "Round ${match.roundNumber}"
        } else if (holder is MatchViewHolder) {
            holder.matchNo.text = "Match ${match.matchNumber}: ${match.teamA} vs ${match.teamB}"
            holder.dateTime.text = sdf.format(Date(match.matchTime))
            holder.location.text = "Location: ${match.location}"
        }
    }

    override fun getItemCount() = matches.size

    class RoundHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val roundTitle: TextView = view.findViewById(R.id.tvRoundHeader)
    }

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val matchNo: TextView = view.findViewById(R.id.tvMatch)
        val dateTime: TextView = view.findViewById(R.id.tvDateTime)
        val location: TextView = view.findViewById(R.id.tvLocation)
    }
}
