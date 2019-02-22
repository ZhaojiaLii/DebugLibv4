package com.example.testlib

import java.io.File
import java.util.*

open class CleanLogs {

    open fun CheckAndCleanLog(databasePath: String?){
        val src = databasePath!!.substring(0, databasePath.lastIndexOf("/"))    // path of destination folder
        val deletefile = File(src).listFiles()
        val filenameList = ArrayList<String>()
        val deletedlist = ArrayList<String>()
        val calendar = Calendar.getInstance()
        val Month = calendar.get(Calendar.MONTH)
        val deletedate = 2019*10000+Month*100+1
        for (i in 0 until deletefile.size){
            filenameList.add(deletefile[i].name)
            if (deletefile[i].name.contains("log")){     // file.name get the file name, not the path;   NO NEED TO SUBSTRING!!
                val tempname = deletefile[i].name
                val date = tempname.substring(tempname.indexOf("2"),tempname.lastIndexOf('.'))
                val year = date.substring(0,4).toInt()
                val month = date.substring(5,date.lastIndexOf("-")).toInt()
                val day = date.substring(date.lastIndexOf("-")+1).toInt()
                val dateNum = year*10000 + month*100 + day*1
                if (deletedate >dateNum){
                    deletedlist.add(deletefile[i].name)
                    deletefile[i].delete()
                }
            }
        }
    }
}