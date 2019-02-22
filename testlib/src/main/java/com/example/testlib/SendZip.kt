package com.example.testlib

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import java.io.File

open class SendZip {

     open fun sendZip(context:Context,databasePath:String?,ZipPassword:String?,AddressMail:String?,appName:String){
        // need to delete the original ZipFile file first and then compress the new ZipFile file

        val src = databasePath!!.substring(0, databasePath.lastIndexOf("/"))    // path of destination folder
        val dest = databasePath.substring(0, databasePath.lastIndexOf("/"))+"/CompressedFile.zip"    // path of destination ZipFile file
        val deletefile = File(src).listFiles()
        val filenameList = ArrayList<String>()
        for (i in 0 until deletefile.size){
            filenameList.add(deletefile[i].name)
            if (deletefile[i].name.contains(".zip")){     // file.name get the file name, not the path;   NO NEED TO SUBSTRING!!
                deletefile[i].delete()
            }
        }

        val zip = ZipFile().zip(src,dest,false,ZipPassword)
        val zipfile = File(zip)

         val emailIntent = Intent(Intent.ACTION_SEND)
         emailIntent.type = "plain/text"
         emailIntent.putExtra(Intent.EXTRA_SUBJECT, "The Zip File of Database and Log of application $appName")
         emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(AddressMail))
         emailIntent.putExtra(Intent.EXTRA_TEXT, "Please enter some content")
         val u = Uri.fromFile(zipfile)
         emailIntent.putExtra(Intent.EXTRA_STREAM, u)
         context.startActivity(Intent.createChooser(emailIntent, "send by："))
         Toast.makeText(context,"Your Zip File is ready to send", Toast.LENGTH_SHORT).show()
     }
}