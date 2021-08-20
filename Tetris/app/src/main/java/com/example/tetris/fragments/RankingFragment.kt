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
        // Inflate
        val view = inflater.inflate(R.layout.fragment_ranking, container,false)
        // Set the adapter
        val mAdapter = RankingAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.ranking_list)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Player view model
        mPlayerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        mPlayerViewModel.readAllData.observe(viewLifecycleOwner, Observer { player ->
            val sortedData = player.sortedByDescending { it.highScore }
            mAdapter.setData(sortedData)
        })
        return view
    }

}