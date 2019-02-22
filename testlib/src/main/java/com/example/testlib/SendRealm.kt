package com.example.testlib

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import java.io.File

open class SendRealm {

    open fun SendDatabaseByMail(context:Context,databasePath: String?,AddressMail:String?,appName:String){

        val f = File(databasePath)

        if (databasePath != null){

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "plain/text"
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "The Realm Database File of application $appName")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(AddressMail))
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Please enter some content")
            val u = Uri.fromFile(f)
            emailIntent.putExtra(Intent.EXTRA_STREAM, u)
            context.startActivity(Intent.createChooser(emailIntent, "send by："))
            Toast.makeText(context,"Your Realm Database is ready to send", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"error", Toast.LENGTH_SHORT).show()
        }


    }
}