package com.example.quizzadeira.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizzadeira.ConnectActivity
import com.example.quizzadeira.R
import kotlinx.android.synthetic.main.fragment_about.view.*

class aboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_about, container, false)
        view.btLogout.setOnClickListener { logout()}

        return view
    }

    @SuppressLint("CommitPrefEdits")
    private fun logout() {
        val sharedPref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)

        sharedPref?.edit()?.putString("password", "")?.putString("email", "")?.putString("token", "")
            ?.apply()

        val intent = Intent(activity, ConnectActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}