package com.example.quizzadeira.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizzadeira.R
import com.example.quizzadeira.adapters.AdapterCategorie
import com.example.quizzadeira.dao.DAOGame
import com.example.quizzadeira.dao.DAOQuestion
import com.example.quizzadeira.model.Difficulty
import com.example.quizzadeira.model.categorie.Categorie
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_difficulty.view.*

class difficultyFragment : Fragment() {

    lateinit var adapter: AdapterCategorie

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_difficulty, container, false)

        adapter = AdapterCategorie()

        view.RVDifficulty.adapter = adapter

        view.RVDifficulty.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        view.BTPlay.setOnClickListener {
            setSetup(
                    adapter.getCategory(),
                    ButtonClicked(view)
            )
        }

        view.btRandom.setOnClickListener {
            random()
        }

        return view
    }

    fun ButtonClicked(view: View): Int {

        if (view is ToggleButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.TBNivel1 ->
                    if (checked) {
                        return 1
                    }
                R.id.TBNivel2 ->
                    if (checked) {
                        return 2
                    }
                R.id.TBNivel3
                ->
                    if (checked) {
                        return 3
                    }
            }
        }
    return 0
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    private fun setSetup(categorie: Categorie?, lvl: Int) {

        val daoGame = DAOGame()
        val daoQuestion = DAOQuestion()

        if (categorie != null) {
            val difficulty = Difficulty(lvl)

            val sharedPref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
            val token = sharedPref?.getString("token", "")

            if (token != null) {

                val build: AlertDialog.Builder = AlertDialog.Builder(activity)
                build.setView(R.layout.screen_load)
                build.setCancelable(false)

                val alertDialog: AlertDialog = build.create()
                alertDialog.show()

                daoGame.startGameWhitSetup(token, difficulty.difficulty, categorie.id) {
                    val bundle = Bundle()

                    bundle.putInt("score",it.score)

                    daoQuestion.nextQuestion(token) {

                        val gson = Gson()
                        val questionJson = gson.toJson(it)

                        bundle.putBoolean("withSetup", true)
                        bundle.putString("question", questionJson)

                        alertDialog.dismiss()
                        findNavController().navigate(R.id.gameFragment, bundle)
                    }
                    daoQuestion.existQuestion(token) {
                        alertDialog.dismiss()
                        Toast.makeText(activity,getString(R.string.gameRunning), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(activity, getString(R.string.selectDifficultyCategory), Toast.LENGTH_SHORT)
                    .show()
        }
   }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun random(){
        val daoGame = DAOGame()
        val daoQuestion = DAOQuestion()

        val sharedPref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
        val token = sharedPref?.getString("token", "")

        if (token != null) {
            val build: AlertDialog.Builder = AlertDialog.Builder(activity)
            build.setView(R.layout.screen_load)
            build.setCancelable(false)

            val alertDialog: AlertDialog = build.create()
            alertDialog.show()

            daoGame.startGame(token){
                val bundle = Bundle()

                bundle.putInt("score",it.score)

                daoQuestion.nextQuestion(token) {
                    alertDialog.dismiss()

                    val gson = Gson()
                    val questionJson = gson.toJson(it)

                    bundle.putBoolean("withSetup", true)
                    bundle.putString("question", questionJson)

                    findNavController().navigate(R.id.gameFragment, bundle)
                }
            }
            daoQuestion.existQuestion(token) {
                alertDialog.dismiss()
                Toast.makeText(activity,getString(R.string.gameRunning),Toast.LENGTH_SHORT).show()
            }
        }
    }
}