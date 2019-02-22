package com.example.testtesttest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.testlib.testClass

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val test = testClass()
        test.ToastShow(this)

    }
}
