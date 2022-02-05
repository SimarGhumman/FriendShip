package com.example.friendship


import android.content.SharedPreferences
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.*
import androidx.core.app.ActivityCompat.startActivityForResult

import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.startActivity





class Contacts : AppCompatActivity() {
    var name: String? = null
    var email: String? = null
    var nameInput: EditText? = null
    var emailInput: EditText? = null
    var phoneInput: EditText? = null
    var submitButton: Button? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contacts)
        nameInput = findViewById<View>(R.id.nameInput) as EditText
        emailInput = findViewById<View>(R.id.emailInput) as EditText
        phoneInput = findViewById<View>(R.id.phoneInput) as EditText
        submitButton = findViewById<View>(R.id.submitButton) as Button
        val sharedPref = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE)
        submitButton!!.setOnClickListener {
            name = nameInput!!.text.toString()
            email = emailInput!!.text.toString()
            val phoneInputString = phoneInput!!.text.toString()
            val phonenumber =
                PhoneNumberUtils.formatNumber(phoneInputString, Locale.getDefault().country)
            val editor = sharedPref.edit()

            editor.putString("name", name)
            editor.putString("email", email)
            editor.putString("phonenumber", phonenumber)
            editor.apply()
            addContact(name!!, email!!, phonenumber)
        }


    }

    private fun addContact(name: String, email: String, phone: String) {
        //in this method we are calling an intent and passing data to that intent for adding a new contact.
        val contactIntent = Intent(ContactsContract.Intents.Insert.ACTION)
        contactIntent.type = ContactsContract.RawContacts.CONTENT_TYPE
        contactIntent
            .putExtra(ContactsContract.Intents.Insert.NAME, name)
            .putExtra(ContactsContract.Intents.Insert.PHONE, phone)
            .putExtra(ContactsContract.Intents.Insert.EMAIL, email)
        resultLauncher.launch(contactIntent)
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                Toast.makeText(this, "Contact has been added.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ContactList::class.java))
        }
}

