package com.example.quizzadeira.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzadeira.R
import com.example.quizzadeira.model.question.Answer
import kotlinx.android.synthetic.main.answer.view.*

class AdapterAnswer() : RecyclerView.Adapter<AdapterAnswer.ViewHolder>() {

    private var answers = listOf<Answer>()
    private var answerSelect: Int = 0
    private var select: Boolean = false

    override fun getItemViewType(position: Int): Int {
        return if (select && position == answerSelect) {
            R.layout.select_answer
        } else {
            R.layout.answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
            ViewHolder(
                    LayoutInflater
                            .from(parent.context)
                            .inflate(viewType, parent, false)
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (answers.isNotEmpty()) {
            val answer = answers[position]
            holder.fillView(answer)
        }
    }

    override fun getItemCount() = answers.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillView(answer: Answer) {

            itemView.TVNumberAnswer.text = answer.order.toString()
            itemView.AnswerDescription.text = answer.description.replace("&quot;"," ' ").replace("&#039;"," ' ")

            itemView.setOnClickListener {
                select = !select

                val position = answers.indexOf(answer)

                answerSelect = position
                notifyItemChanged(position)

            }
        }
    }

    fun setAnswers(list: List<Answer>){
        answers = list
        notifyDataSetChanged()
    }

    fun getAnswer(): Answer? {
        if (select) {
            return answers[answerSelect]
        }
        return null
    }
}