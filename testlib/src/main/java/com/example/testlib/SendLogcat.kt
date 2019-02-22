package com.example.testlib

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import java.io.File
import java.io.IOException

open class SendLogcat {

    open fun SendLogcatByMail(context:Context,logcatPath: String?,AddressMail:String?,appName:String) {

        val outputFile = File(logcatPath)

        try {
            Runtime.getRuntime().exec(
                "logcat -f " + outputFile.absolutePath
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "vnd.android.cursor.dir/email"
        val to = arrayOf(AddressMail)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
        val u = Uri.fromFile(outputFile.absoluteFile)
        emailIntent.putExtra(Intent.EXTRA_STREAM, u)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "The Logcat of application $appName")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Please enter some content")
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."))
        Toast.makeText(context,"Your Logcat is ready to send", Toast.LENGTH_SHORT).show()
    }
}