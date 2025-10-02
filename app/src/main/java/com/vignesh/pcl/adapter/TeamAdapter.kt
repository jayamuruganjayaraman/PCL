package com.vignesh.pcl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vignesh.pcl.R
import com.vignesh.pcl.model.TeamEntity

class TeamAdapter(private var teamList: List<TeamEntity>,
                  private val onItemClick: (TeamEntity) -> Unit
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTeamName: TextView = itemView.findViewById(R.id.tv_name)

        fun bind(team: TeamEntity) {
            tvTeamName.text = team.name

            itemView.setOnClickListener {
                onItemClick(team)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teamList[position])
    }

    override fun getItemCount(): Int = teamList.size

    fun updateTeams(newTeams: List<TeamEntity>) {
        teamList = newTeams
        notifyDataSetChanged()
    }
}