package com.example.quizzadeira.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizzadeira.R
import com.example.quizzadeira.adapters.AdapterAnswer
import com.example.quizzadeira.dao.DAOGame
import com.example.quizzadeira.dao.DAOQuestion
import com.example.quizzadeira.model.question.Answer
import com.example.quizzadeira.model.question.QuestionData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_game.view.*

class gameFragment : Fragment() {

    lateinit var Adapteranswer: AdapterAnswer
    var daogame = DAOGame()
    var daoquestion = DAOQuestion()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_game, container, false)
        val sharedPref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)
        val token = sharedPref?.getString("token", "")
        val withSetup = arguments?.getBoolean("withSetup")

        if (token != null && withSetup == true) {

            val gson = Gson()
            val questionJson = arguments?.getString("question")
            val question = gson.fromJson(questionJson, QuestionData::class.java)

            if (question != null) {
                Adapteranswer = AdapterAnswer()

                Adapteranswer.setAnswers(question.problem.answers)

                view.RVAnswer.adapter = Adapteranswer
                view.RVAnswer.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                view.TVQuestion.text = question.problem.question.replace("&quot;"," ' ").replace("&#039;"," ' ")

            }

        } else {

            val build: AlertDialog.Builder = AlertDialog.Builder(activity)

            build.setCancelable(false)

            val alertDialog: AlertDialog = build.create()
            alertDialog.show()

            var question: QuestionData

            if (token != null) {

                daoquestion.existQuestion(token) {

                    alertDialog.dismiss()

                    question = it
                    Adapteranswer = AdapterAnswer()

                    Adapteranswer.setAnswers(question.problem.answers)

                    view.RVAnswer.adapter = Adapteranswer

                    view.RVAnswer.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

                    view.TVQuestion.text =
                        question.problem.question.replace("&quot;"," ' ").replace("&#039;"," ' ")

                }
            }
        }
        view.BTQuestNext.setOnClickListener {
            questionNext(
                    token,
                    Adapteranswer.getAnswer(),
            )
        }
     return view
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun questionNext(token: String?, answer: Answer?) {

        val bundle = Bundle()
        if (answer != null && token != null) {

            val build: AlertDialog.Builder = AlertDialog.Builder(activity)
            val buildII: AlertDialog.Builder = AlertDialog.Builder(activity)

            build.setView(R.layout.screen_load)
            build.setCancelable(false)

            buildII.setPositiveButton(getString(R.string.next)) { dialog, _ ->

                daoquestion.nextQuestion(token){

                    val gson = Gson()
                    val questionJson = gson.toJson(it)

                    bundle.putBoolean("withSetup", true)
                    bundle.putString("question", questionJson)

                    findNavController().navigate(R.id.gameFragment, bundle)

                    dialog.dismiss()
                }
            }
            buildII.setNegativeButton(getString(R.string.endgame)){ dialog, _ ->

                daogame.endGame(token){

                    val gson = Gson()
                    val endGame = gson.toJson(it)
                    bundle.putString("endGame", endGame)

                    findNavController().navigate(R.id.reportFragment,bundle)
                }
                dialog.dismiss()
            }

            val alertDialog: AlertDialog = build.create()
            val showUser: AlertDialog = buildII.create()

            alertDialog.show()

            daoquestion.answerQuestion(answer.order, token) {
                alertDialog.dismiss()

                if (it.answer.status == "incorrect") {
                    showUser.setTitle(getString(R.string.incorrect))
                } else {
                    showUser.setTitle(getString(R.string.correct))
                }
                showUser.setMessage(getString(R.string.yourScore) +" "+ it.answer.score.toString())

                showUser.setCancelable(false)
                showUser.show()
            }
        } else {
            Toast.makeText(activity, getString(R.string.selectAnswer), Toast.LENGTH_SHORT)
                    .show()
        }
    }
}