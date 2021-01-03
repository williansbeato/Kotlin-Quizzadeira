package com.example.quizzadeira.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.quizzadeira.R
import com.example.quizzadeira.dao.DAOuser
import com.example.quizzadeira.model.user.UserInput
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*

class registerFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_register, container, false)
        view.BTRegisterRegister.setOnClickListener {
            register(
                    ETRegisterEmail.text.toString(),
                    ETRegisterPassword.text.toString(),
                    ETRegisterName.text.toString(),
                    ETRegisterConfirm.text.toString()
            )
        }
    return view
    }

    private fun register(email: String, password: String, name: String, confirm: String) {

        val userInput = UserInput(email, name, password)

        if (userInput.email == "" || userInput.password == "" || userInput.name == "") {
            Toast.makeText(activity, R.string.emptyField, Toast.LENGTH_SHORT).show()

        } else {

            if (userInput.password != confirm) {
                Toast.makeText(activity, R.string.wrongPassword, Toast.LENGTH_SHORT)
                        .show()
            } else {

                val dao = DAOuser()

                dao.insert(userInput) {

                    val build: AlertDialog.Builder = AlertDialog.Builder(activity)

                    build.setMessage(R.string.successfullyRegistered)

                    build.setPositiveButton(R.string.ok) { dialog, which ->

                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        dialog.dismiss()
                    }

                    val alertDialog: AlertDialog = build.create()
                    alertDialog.show()

                }
            }
        }
    }
}