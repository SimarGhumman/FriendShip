package com.example.friendship

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import kotlin.random.Random
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)

        val contact_friends = findViewById<Button>(R.id.goToFriends)
        contact_friends.setOnClickListener {
            startActivity(Intent(this , ContactFriends::class.java))
        }
        val contacts = findViewById<Button>(R.id.about_us)
        contacts.setOnClickListener {
            startActivity(Intent(this , AboutUs::class.java))
        }

        val contactslist = findViewById<Button>(R.id.contactlist)
        contactslist.setOnClickListener {
            startActivity(Intent(this , ContactList::class.java))
        }
    }
}