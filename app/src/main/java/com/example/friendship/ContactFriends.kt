package com.example.friendship
import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.card.MaterialCardView
import java.lang.StringBuilder
import java.util.ArrayList


class ContactFriends : AppCompatActivity() {

    var Send_SMS: Button? = null
    var ContactsModalArrayList: ArrayList<ContactsModal> = ArrayList()

    var selectCard: MaterialCardView? = null
    var tvContact: TextView? = null
    var selectedContact: BooleanArray? = null
    var contactList = ArrayList<Int>()
    lateinit var contactArray: Array<CharSequence?>
    override fun onCreate(savedInstanceState: Bundle?) {
        ContactsModalArrayList = ArrayList()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_friends)
        Send_SMS = findViewById<View>(R.id.Send_SMS) as Button

        getContacts()
        contactArray = arrayOfNulls(ContactsModalArrayList.size)
        for (i in 0 until ContactsModalArrayList.size) {
            contactArray[i] = ContactsModalArrayList.get(i).userName.toString()
            Log.d("value is", ContactsModalArrayList.get(i).contactNumber.toString())
        }

        selectCard = findViewById(R.id.selectCard)
        tvContact = findViewById(R.id.tvCourses)
        selectedContact = BooleanArray(contactArray.size)
        selectCard!!.setOnClickListener { showCoursesDialog() }



        ActivityCompat.requestPermissions(
            this@ContactFriends,
            arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS),
            PackageManager.PERMISSION_GRANTED
        )
        Send_SMS!!.setOnClickListener {
            val mySmsManager = SmsManager.getDefault()
            for (x in 0 until contactList.size) {
                for (i in 0 until ContactsModalArrayList.size) {
                    if (contactArray[contactList[x]].toString() == ContactsModalArrayList.get(i).userName.toString()) {
                        mySmsManager.sendTextMessage(ContactsModalArrayList.get(i).contactNumber.toString(), null, "I want to talk to you.", null, null)
                        Toast.makeText(this, "SMS Sent.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


    private fun showCoursesDialog() {
        val builder = AlertDialog.Builder(this@ContactFriends)
        builder.setTitle("Select Contact")
        builder.setCancelable(false)
        builder.setMultiChoiceItems(contactArray, selectedContact) { dialog, which, isChecked ->
            if (isChecked) {
                contactList.add(which)
            } else {
                contactList.removeAt(which)
            }
        }.setPositiveButton("Ok") { dialog, which ->
            val stringBuilder = StringBuilder()
            for (i in contactList.indices) {
                stringBuilder.append(contactArray[contactList[i]])
                if (i != contactList.size - 1) {
                    stringBuilder.append(", ")
                }
                tvContact!!.text = stringBuilder.toString()
            }
        }
            .setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }
            .setNeutralButton("Clear all") { dialog, which ->
                for (i in selectedContact!!.indices) {
                    selectedContact!![i] = false
                    contactList.clear()
                    tvContact!!.text = ""
                }
            }
        builder.show()
    }




    private fun getContacts() {
        //this method is use to read contact from users device.
        //on below line we are creating a string variables for our contact id and display name.
        var contactId = ""
        var displayName = ""
        //on below line we are calling our content resolver for getting contacts
        val cursor: Cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )!!
        //on blow line we are checking the count for our cursor.
        if (cursor.count > 0) {
            //if the count is greater thatn 0 then we are running a loop to move our cursor to next.
            while (cursor.moveToNext()) {
                //on below line we are getting the phone number.
                val hasPhoneNumber: Int =
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        .toInt()
                if (hasPhoneNumber > 0) {
                    //we are checking if the has phine number is >0
                    //on below line we are getting our contact id and user name for that contact
                    contactId =
                        cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                    displayName =
                        cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                    //on below line we are calling a content resolver and making a query
                    val phoneCursor: Cursor? = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(contactId),
                        null
                    )
                    //on below line we are moving our cursor to next position.
                    if (phoneCursor!!.moveToNext()) {
                        //on below line we are getting the phone number for our users and then adding the name along with phone number in array list.
                        val phoneNumber: String =
                            phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        ContactsModalArrayList.add(ContactsModal(displayName, phoneNumber))
                    }
                    intent.putExtra("mylist", ContactsModalArrayList)
                    //on below line we are closing our phone cursor.
                    phoneCursor.close()
                }
            }
        }
    }
}