package com.example.quizzadeira.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzadeira.R
import com.example.quizzadeira.dao.DAORanking
import com.example.quizzadeira.model.ranking.RankingUser
import kotlinx.android.synthetic.main.ranking_users.view.*

class AdapterRanking() : RecyclerView.Adapter<AdapterRanking.ViewHolder> (){

    private val dao = DAORanking()
    private var rankings = listOf<RankingUser>()

    init {
        dao.getAll {
            rankings = it
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = rankings.size

    override fun getItemViewType(position: Int): Int = R.layout.ranking_users

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRanking.ViewHolder =
            ViewHolder(
                    LayoutInflater
                            .from(parent.context)
                            .inflate(viewType, parent, false)
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (rankings.isNotEmpty()) {
            val rankingsUser = rankings[position]
            holder.fillView(rankingsUser)
        }
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillView(rankingUser: RankingUser) {
            itemView.TVRankingUser.text = rankingUser.user
            itemView.TVRankingScore.text = rankingUser.score

        }
    }
}