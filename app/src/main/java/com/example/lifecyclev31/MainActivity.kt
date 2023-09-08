package com.example.lifecyclev31

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity() {

    private val fragmentManager: FragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loginButton: Button = findViewById(R.id.loginButton)
        val registerButton: Button = findViewById(R.id.registerButton)
        loginButton.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, LoginFragment()).commit()
        }
        registerButton.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            if (savedInstanceState != null) {
                Log.d("saveinstance", "savinstance is not null")

            } else {
                Log.d("saveinstance", "savinstance is null")
                fragmentTransaction.replace(R.id.fragmentContainer, RegisterFragment()).commit()
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        Log.d("saving instance", "instance is saved")

       // supportFragmentManager.putFragment(outState,"RegisterFragment",RegisterFragment)
        super.onSaveInstanceState(outState, outPersistentState)
    }
}