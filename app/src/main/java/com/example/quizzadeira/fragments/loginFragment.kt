package com.example.quizzadeira.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.quizzadeira.MainActivity
import com.example.quizzadeira.R
import com.example.quizzadeira.dao.DAOuser
import com.example.quizzadeira.model.user.UserLogin
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class loginFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val sharedPref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)

        if (sharedPref != null) {
            val email = sharedPref.getString("email", "")
            val password = sharedPref.getString("password", "")

            login(email!!, password!!, false)

        }

        view.BTRegister.setOnClickListener { nextTo() }
        view.BTLogin.setOnClickListener {
            login(
                    ETLoginEmail.text.toString(),
                    ETLoginPassword.text.toString()
            )
        }

        view.BTRegister.setOnClickListener {
            nextTo()
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun login(email: String, password: String, verify: Boolean = false) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            val dao = DAOuser()

            val userLogin = UserLogin(email, password)

            try {

                val build: AlertDialog.Builder = AlertDialog.Builder(activity)
                build.setView(R.layout.screen_load)
                build.setCancelable(false)

                val alertDialog: AlertDialog = build.create()
                alertDialog.show()

                dao.login(userLogin) {

                    alertDialog.dismiss()

                   it

                    val sharedPref = activity?.getSharedPreferences("user", Context.MODE_PRIVATE)

                    if (sharedPref != null) {
                        with(sharedPref.edit()) {
                            putString("email", it.email)
                            putString("password", password)
                            putString("token", it.token)

                            commit()
                        }
                    }

                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)

                }
            } catch (e: Exception) {
                Toast.makeText(activity, R.string.loginWrong, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun nextTo() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }
}