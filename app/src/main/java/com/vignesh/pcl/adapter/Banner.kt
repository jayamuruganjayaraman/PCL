package com.vignesh.pcl.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.vignesh.pcl.R
import com.vignesh.pcl.model.TournamentEntity
import kotlin.Int

class Banner (private val context: Context, private val tournamentList: List<TournamentEntity>): RecyclerView.Adapter<Banner.BannerViewHolder>(){
    inner class BannerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val bannerImg: ImageView = itemView.findViewById(R.id.bannerImg)
        val tvTournamentName: TextView = itemView.findViewById(R.id.tvTournamentName)
        val llLoadTournament: LinearLayout = itemView.findViewById(R.id.llLoadTournament)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tournament_item_view, parent, false)
        return BannerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tournamentList.size
    }

    override fun onBindViewHolder(holder: BannerViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val model: TournamentEntity = tournamentList[position]

        holder.tvTournamentName.text = model.name
        if (position % 2 == 0) {
            holder.bannerImg.setImageDrawable(getDrawable(context,R.drawable.banner1))
        } else {
            holder.bannerImg.setImageDrawable(getDrawable(context,R.drawable.banner2))
        }

       /* holder.llLoadTournament.setOnClickListener(object :
            DebouncedOnClickListener(DebouncedOnClickListener.CLICK_INT) {
            override fun onDebouncedClick(v: View?) {
                sessionManager.saveTournamentID(String.valueOf(tournamentList[position].getTournamentID()))
                val intent: Intent = Intent(context, MainActivity::class.java)
                //intent.putExtra(context.getString(R.string.key_tid) ,tournamentList.get(position).getTournamentID());
                context.startActivity(intent)
            }
        })*/
    }
}