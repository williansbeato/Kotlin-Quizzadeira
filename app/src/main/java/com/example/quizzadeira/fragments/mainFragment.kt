package com.example.quizzadeira.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quizzadeira.R

class mainFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.btGame).setOnClickListener(this)
        view.findViewById<Button>(R.id.btProfile).setOnClickListener(this)
        view.findViewById<Button>(R.id.btRanking).setOnClickListener(this)
    }


    override fun onClick(v: View?){
        when(v!!.id){
            R.id.btGame -> navController!!.navigate(R.id.action_mainFragment_to_gameFragment)
            R.id.btProfile -> navController!!.navigate(R.id.action_mainFragment_to_profileFragment)
            R.id.btRanking -> navController!!.navigate(R.id.action_mainFragment_to_rankingFragment)
        }
    }

}