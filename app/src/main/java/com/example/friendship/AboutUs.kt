package com.example.friendship

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AboutUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_us)
        val linkTextView1 = findViewById<TextView>(R.id.textView5)
        linkTextView1.movementMethod = LinkMovementMethod.getInstance()
        linkTextView1.setTextColor(Color.BLUE)
        linkTextView1.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mentalhealthfirstaid.org/mental-health-resources/"))
            startActivity(browserIntent)
        }
        val linkTextView2 = findViewById<TextView>(R.id.textView6)
        linkTextView2.movementMethod = LinkMovementMethod.getInstance()
        linkTextView2.setTextColor(Color.BLUE)
        linkTextView2.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.healthline.com/health/mental-health-resources#types-of-providers"))
            startActivity(browserIntent)
        }
        val linkTextView3 = findViewById<TextView>(R.id.textView7)
        linkTextView3.movementMethod = LinkMovementMethod.getInstance()
        linkTextView3.setTextColor(Color.BLUE)
        linkTextView3.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mhresources.org/"))
            startActivity(browserIntent)
        }
        val linkTextView4 = findViewById<TextView>(R.id.textView8)
        linkTextView4.movementMethod = LinkMovementMethod.getInstance()
        linkTextView4.setTextColor(Color.BLUE)
        linkTextView4.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nami.org/Home"))
            startActivity(browserIntent)
        }
        val linkTextView5 = findViewById<TextView>(R.id.textView9)
        linkTextView5.movementMethod = LinkMovementMethod.getInstance()
        linkTextView5.setTextColor(Color.BLUE)
        linkTextView5.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mentalhealth.gov/"))
            startActivity(browserIntent)
        }
        val linkTextView6 = findViewById<TextView>(R.id.textView10)
        linkTextView6.movementMethod = LinkMovementMethod.getInstance()
        linkTextView6.setTextColor(Color.BLUE)
        linkTextView6.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nimh.nih.gov/health/find-help"))
            startActivity(browserIntent)
        }
        val linkTextView7 = findViewById<TextView>(R.id.textView12)
        linkTextView7.movementMethod = LinkMovementMethod.getInstance()
        linkTextView7.setTextColor(Color.BLUE)
        linkTextView7.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://suicidepreventionlifeline.org/"))
            startActivity(browserIntent)
        }
        val linkTextView8 = findViewById<TextView>(R.id.textView13)
        linkTextView8.movementMethod = LinkMovementMethod.getInstance()
        linkTextView8.setTextColor(Color.BLUE)
        linkTextView8.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nimh.nih.gov/health/topics/suicide-prevention"))
            startActivity(browserIntent)
        }
        val linkTextView9 = findViewById<TextView>(R.id.textView14)
        linkTextView9.movementMethod = LinkMovementMethod.getInstance()
        linkTextView9.setTextColor(Color.BLUE)
        linkTextView9.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.crisistextline.org/"))
            startActivity(browserIntent)
        }
    }
}