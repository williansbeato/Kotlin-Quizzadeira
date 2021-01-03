package com.example.quizzadeira.adapters

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzadeira.R
import com.example.quizzadeira.dao.DAOCategorie
import com.example.quizzadeira.model.categorie.Categorie
import kotlinx.android.synthetic.main.categorie.view.*

class AdapterCategorie() : RecyclerView.Adapter<AdapterCategorie.ViewHolder>()  {

    private val dao = DAOCategorie()
    private var categories = listOf<Categorie>()
    private var categorySelect: Int = 0
    private var select: Boolean = false

    init {
        val handler = Handler()
        handler.postDelayed({
            dao.getAll {
                categories = it
                notifyDataSetChanged()
            }
        }, 0)
    }

    override fun getItemViewType(position: Int): Int {

        return if (select && position == categorySelect) {
            R.layout.select_categorie
        } else {
            R.layout.categorie
        }
    }

    override fun getItemCount() = categories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCategorie.ViewHolder =
            ViewHolder(
                    LayoutInflater
                            .from(parent.context)
                            .inflate(viewType, parent, false)
            )

    override fun onBindViewHolder(holder: AdapterCategorie.ViewHolder, position: Int) {
        if (categories.isNotEmpty()) {
            val category = categories[position]
            holder.fillView(category)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillView(categorie: Categorie) {
            itemView.TVCatergorie.text = categorie.name

            itemView.setOnClickListener {
                select = !select
                val position = categories.indexOf(categorie)
                categorySelect = position
                notifyItemChanged(position)
            }
        }
    }

    fun getCategory(): Categorie? {
        if (select) {
            return categories[categorySelect]
        }
        return null
    }
}