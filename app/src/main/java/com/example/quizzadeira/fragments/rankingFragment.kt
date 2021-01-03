package com.example.quizzadeira.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizzadeira.R
import com.example.quizzadeira.adapters.AdapterRanking
import kotlinx.android.synthetic.main.fragment_ranking.view.*

class rankingFragment : Fragment() {

    private lateinit var adapter: AdapterRanking

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View? {

      val view = inflater.inflate(R.layout.fragment_ranking, container, false)

      adapter = AdapterRanking()

      view.RVRanking.adapter = adapter
      view.RVRanking.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

      return view
    }
}