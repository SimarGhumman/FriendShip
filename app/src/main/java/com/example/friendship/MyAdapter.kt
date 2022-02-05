package com.example.friendship

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator


class MyAdapter(private val context: Context, private var ContactsModalArrayList: ArrayList<ContactsModal>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //passing our layout file for displaying our card item
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.myadapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //setting data to our text views from our modal class.
        Log.d("Testing", "Simar")
        val contacts = ContactsModalArrayList[position]
        holder.contactTV.text = contacts.userName

        val generator: ColorGenerator = ColorGenerator.MATERIAL // or use DEFAULT

        // generate random color
        // generate random color
        val color: Int = generator.getRandomColor()

        //below text drawable is a circular.

        //below text drawable is a circular.
        val drawable2: TextDrawable = TextDrawable.builder().beginConfig()
            .width(100) // width in px
            .height(100) // height in px
            .endConfig() //as we are building a circular drawable we are calling a build round method.
            //in that method we are passing our text and color.
            .buildRound(contacts.getUserName().substring(0, 1), color)
        //setting image to our image view on below line.
        //setting image to our image view on below line.
        holder.contactIV.setImageDrawable(drawable2)

        //adding on click listner for our item of recycler view.
        holder.itemView.setOnClickListener { //on below line we are opening a new activity and passing data to it.
            val contentResolver = context.contentResolver

            val uri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(
                    contacts.contactNumber
                )
            )

            val projection =
                arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID)

            val cursor: Cursor? = contentResolver.query(
                uri,
                projection,
                null,
                null,
                null
            )

            if (cursor != null) {
                cursor.moveToNext()
                //might have to change to LOOKUP_URI
                val contactName: String = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME))
                val contactId: String = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID))
                Log.d("contactMatch name:", contactName)
                Log.d("contactMatch id:", contactId)
                val intent = Intent(Intent.ACTION_VIEW)
                val uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactId)
                intent.data = uri
                context.startActivity(intent)
                cursor.close()
            }
        }



    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //creating variables for our text views.
        val contactTV: TextView
        val contactIV: ImageView

        init {
            //initializing our text views.
            contactTV  = itemView.findViewById(R.id.idTVContactName)
            contactIV = itemView.findViewById(R.id.idIVContact)
        }
    }

    override fun getItemCount(): Int {
        return ContactsModalArrayList.size
    }
}

//    lateinit var context:Context
//    lateinit var ContactsModalArrayList:ArrayList<ContactsModal>