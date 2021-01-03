package com.example.quizzadeira.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quizzadeira.R
import com.example.quizzadeira.model.game.endgame.EndGameData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_result.view.*

class resultFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_result, container, false)
        val gson = Gson()
        val game = arguments?.getString("endGame")
        val endGame = gson.fromJson(game, EndGameData::class.java)

        view.txtEndScore.text = endGame.game.score.toString()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.BTPlayAgain).setOnClickListener(this)
        view.findViewById<Button>(R.id.BTHomes).setOnClickListener(this)
    }

    override fun onClick(v: View?){
        when(v!!.id){
            R.id.BTPlayAgain -> navController.navigate(R.id.difficultyFragment)
            R.id.BTHomes -> navController.navigate(R.id.mainFragment)
        }
    }
}