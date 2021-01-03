package com.example.quizzadeira

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),  ConnectReceiver.ConnectivityReceiverListener {

    var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(
                ConnectReceiver(),
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        bottomNavigationView.setupWithNavController(findNavController(R.id.navHostFragment))
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectReceiver.connectivityReceiverListener = this
    }

    private fun showNetworkMessage(isConnected: Boolean) {

        if (!isConnected) {

            this.snackBar = Snackbar.make(findViewById(R.id.action_loginFragment_to_registerFragment), "Offline", Snackbar.LENGTH_LONG)
            this.snackBar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            this.snackBar?.show()
        } else {
            this.snackBar?.dismiss()
        }
    }
}