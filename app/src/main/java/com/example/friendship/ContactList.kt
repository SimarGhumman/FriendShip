package com.example.friendship

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.database.Cursor
import android.view.View

import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.provider.ContactsContract
import android.widget.Toast

import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import android.content.DialogInterface
import android.net.Uri
import android.provider.Settings


class ContactList : AppCompatActivity() {

    var ContactsModalArrayList: ArrayList<ContactsModal> = ArrayList()
    var contactRV: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        ContactsModalArrayList = ArrayList()
        setContentView(R.layout.contact_list)

        contactRV = findViewById(R.id.idRVContacts)
        super.onCreate(savedInstanceState)

        val addNewContactFAB = findViewById<FloatingActionButton>(R.id.AddContact)
        addNewContactFAB.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val i = Intent(this@ContactList, Contacts::class.java)
                startActivity(i)
            }
        })
        requestPermissions()
        prepareContactRV()
    }

    private fun prepareContactRV() {
        MyAdapter(this, ContactsModalArrayList)
        contactRV!!.layoutManager = LinearLayoutManager(this)
        contactRV!!.adapter = MyAdapter(this, ContactsModalArrayList)
        Log.d("TestingInput", "First Step")

    }

    private fun requestPermissions() {
        // below line is use to request
        // permission in the current activity.
        Dexter.withContext(this) // below line is use to request the number of
            // permissions which are required in our app.
            .withPermissions(
                Manifest.permission.READ_CONTACTS,  // below is the list of permissions
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS, Manifest.permission.WRITE_CONTACTS
            ) // after adding permissions we are
            // calling an with listener method.
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    // this method is called when all permissions are granted
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        // do you work now
                        getContacts()
                        Toast.makeText(
                            this@ContactList,
                            "All the permissions are granted..",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // check for permanent denial of any permission
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        // permission is denied permanently,
                        // we will show user a dialog message.
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    permissionToken: PermissionToken?
                ) {
                    // this method is called when user grants some
                    // permission and denies some of them.
                    permissionToken!!.continuePermissionRequest()
                }
            }).withErrorListener(object : PermissionRequestErrorListener {
                // this method is use to handle error
                // in runtime permissions
                override fun onError(error: DexterError?) {
                    // we are displaying a toast message for error message.
                    Toast.makeText(applicationContext, "Error occurred! ", Toast.LENGTH_SHORT)
                        .show()
                }
            }) // below line is use to run the permissions
            // on same thread and to check the permissions
            .onSameThread().check()
    }

    private fun showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this@ContactList)

        // below line is the title
        // for our alert dialog.
        builder.setTitle("Need Permissions")

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS"
        ) { dialog, which -> // this method is called on click on positive
            // button and on clicking shit button we
            // are redirecting our user from our app to the
            // settings page of our app.
            dialog.cancel()
            // below is the intent from which we
            // are redirecting our user.
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        builder.setNegativeButton("Cancel"
        ) { dialog, which -> // this method is called when
            // user click on negative button.
            dialog.cancel()
        }
        // below line is used
        // to display our dialog
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
        if (cursor.getCount() > 0) {
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
                    //on below line we are closing our phone cursor.
                    phoneCursor.close()
                }
            }
        }
    }
}

