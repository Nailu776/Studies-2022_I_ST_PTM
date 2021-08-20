package com.example.tetris.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tetris.R
import com.example.tetris.player.Player

class RankingAdapter: RecyclerView.Adapter<RankingAdapter.MyViewHolder>()  {

    private var playersList = emptyList<Player>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var rankView: TextView = itemView.findViewById(R.id.rank_nr)
        var nickView: TextView = itemView.findViewById(R.id.rank_nick)
        var hsView: TextView = itemView.findViewById(R.id.rank_hs)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.ranking_row, parent, false))
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = playersList[position]
        holder.nickView.text = currentItem.nick
        holder.hsView.text = currentItem.highScore.toString()
        holder.rankView.text = (position + 1).toString()
    }

    fun setData(player: List<Player>){
        this.playersList = player
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int{
        return playersList.size
    }

    override fun getItemId(position: Int): Long{
        return position.toLong()
    }



}