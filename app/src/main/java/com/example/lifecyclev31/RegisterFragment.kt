package com.example.lifecyclev31

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import java.time.Year
import java.util.Calendar


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }



    override fun onStop() {
        val emailField: (EditText) = requireView().findViewById(R.id.registerEmailField)
        val nameField: (EditText) = requireView().findViewById(R.id.registerNameField)
        val driversLicenseCheckBox: (CheckBox) = requireView().findViewById(R.id.registerDriversLicenseCheckBox)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(getString(R.string.email), emailField.text.toString())
            putString(getString(R.string.name), nameField.text.toString())
            putBoolean(getString(R.string.driversLicense), driversLicenseCheckBox.isChecked)
            apply()
        }

        Log.d("lifecycle","onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("lifecycle","onDestroy")
        super.onDestroy()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dobField: (TextView) = view.findViewById(R.id.registerDateOfBirthField)
        val c = Calendar.getInstance()
        var year: Int = c.get(Calendar.YEAR)
        var month: Int= c.get(Calendar.MONTH)
        var day: Int= c.get(Calendar.DATE)
        dobField.text =  "$year-$month-$day"
        val emailField: (EditText) = view.findViewById(R.id.registerEmailField)
        val nameField: (EditText) = view.findViewById(R.id.registerNameField)
        val driversLicenseCheckBox: (CheckBox) = view.findViewById(R.id.registerDriversLicenseCheckBox)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        emailField.setText(sharedPref.getString(getString(R.string.email), "kalle@ankeborg.kvack"))
        nameField.setText(sharedPref.getString(getString(R.string.name), "Kalle Anka"))
        driversLicenseCheckBox.isChecked = sharedPref.getBoolean(getString(R.string.driversLicense), false)



        /*
        name = nameField.setText(savedInstanceState.getString("name","hellu").toString()).toString()
        email = emailField.setText(savedInstanceState.getString("email").toString())
        driversLicense = driversLicenseCheckBox.isChecked = savedInstanceState.getBoolean("driversLicense")

         */




        val passwordField: (EditText) = view.findViewById(R.id.registerPasswordField)
        if(savedInstanceState != null){
            Log.d("saved", "savedinstanse is non null")
            Log.d("savedinstance", savedInstanceState.toString())
            nameField.setText(savedInstanceState.getString("name","hellu").toString())
            emailField.setText(savedInstanceState.getString("email").toString())
            driversLicenseCheckBox.isChecked = savedInstanceState.getBoolean("driversLicense")
        } else {
            Log.d("saved", "savedinstanse is null")
        }
        dobField.setOnClickListener{
            val datePicker: DatePickerDialog = DatePickerDialog(
                this.requireContext(),{ _: DatePicker, chosenYear:Int, chosenMonth:Int,chosenDate:Int ->
                year= chosenYear
                    month = chosenMonth
                    day = chosenDate
                    dobField.text =  "$year-$month-$day"
                                      },year,month,day)
            datePicker.show()

        }



        val submitButton: Button = view.findViewById(R.id.registerSubmitButton)
        submitButton.setOnClickListener{

            val userBuilder: User.UserBuilder = User.UserBuilder()
                .setEmail(emailField.text.toString())
                .setName(nameField.text.toString())
                .setDob(dobField.text.toString())
                .setDriversLicense(driversLicenseCheckBox.isChecked)
                .setPassword(passwordField.text.toString())
            if(userBuilder.verify()){
                Log.d("User", "User has filled in all the fields")
                val user: User = userBuilder.build()

            } else{
                Log.d("User", "User has NOT filled in all the fields")
                Toast.makeText(this.context,"Please fill in all the fields",Toast.LENGTH_LONG).show()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}