package com.example.testlib

import android.content.Context
import android.widget.Toast

open class testClass {

    open fun ToastShow(context: Context){
        Toast.makeText(context,"!!!!!!!!!!!!!!!!",Toast.LENGTH_LONG).show()
    }
}