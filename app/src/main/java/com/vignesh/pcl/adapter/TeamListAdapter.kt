package com.vignesh.pcl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vignesh.pcl.R
import com.vignesh.pcl.model.TeamEntity

class TeamListAdapter( private val onItemClick: (TeamEntity) -> Unit
) : ListAdapter<TeamEntity, TeamListAdapter.TeamViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<TeamEntity>() {
        override fun areItemsTheSame(oldItem: TeamEntity, newItem: TeamEntity): Boolean {
            return oldItem.teamId == newItem.teamId
        }

        override fun areContentsTheSame(oldItem: TeamEntity, newItem: TeamEntity): Boolean {
            return oldItem == newItem
        }
    }

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTeamName: TextView = itemView.findViewById(R.id.txtTeamName)
        private val imgTeamName: ImageView = itemView.findViewById(R.id.img_team)
        fun bind(item: TeamEntity, position: Int) {
            tvTeamName.text = item.name
            if(position % 2 == 0){
                imgTeamName.setImageResource(R.drawable.team_1)
            }else{
                imgTeamName.setImageResource(R.drawable.team_2)
            }
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.team_list_item, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(getItem(position),position)
    }
}
