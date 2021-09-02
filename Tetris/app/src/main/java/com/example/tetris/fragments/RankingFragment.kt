package com.example.tetris.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tetris.R
import com.example.tetris.adapter.RankingAdapter
import com.example.tetris.player.PlayerViewModel


class RankingFragment : Fragment() {

    private lateinit var mPlayerViewModel: PlayerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate view fragment_ranking layout
        val view = inflater.inflate(R.layout.fragment_ranking, container,false)

        // Set the adapter to RankingAdapter
        val mAdapter = RankingAdapter()

        // Recycler View ranking_list
        val recyclerView = view.findViewById<RecyclerView>(R.id.ranking_list)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Get Player view model and observe every time database change
        // Set new sorted data to ranking
        mPlayerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        mPlayerViewModel.readAllData.observe(viewLifecycleOwner, Observer { players ->
            val sortedData = players.sortedByDescending { it.highScore }
            mAdapter.setData(sortedData)
        })

        return view
    }

}