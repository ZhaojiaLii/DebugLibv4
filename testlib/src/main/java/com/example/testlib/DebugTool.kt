package com.example.testlib

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.StrictMode
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.example.testlib.DebugAdapter
import com.example.testlib.OptionListener
import com.example.testlib.Options
import com.willowtreeapps.hyperion.core.Hyperion


@Suppress("DEPRECATION")
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)

open class DebugTool  (
    rootView : View,
    context: Activity,
    private var databasePath : String? = null,
    private var logcatPath : String? = null,
    private var AddressMail :String?= null,
    private var ZipPassword : String ?= null
    ) {

    private val context: Context
    private val TAG = "tag"
    private var mIsPressed = false
    private var delay = 1000
    private var fingers = 2
    private var mFingers = 0
    private val appName = context.applicationInfo.loadLabel(context.packageManager)   // application name
    private val handler = Handler()
    private val adapter1 = DebugAdapter()
    private var numberIndex : Int
    private val runnable = Runnable {
        showList()
    }

    lateinit var listView : ListView
    private lateinit var view : View

    init {

        this.context = context
        numberIndex = Options().getList().size
        val listOps : ArrayList<String> = Options().getList()
        adapter1.CustomAdapter(context,listOps)

        val builder0 = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder0.build())
        builder0.detectFileUriExposure()

        rootView.setOnTouchListener(object : View.OnTouchListener {
            @SuppressLint("LogNotTimber", "ClickableViewAccessibility")
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                try {
                    val fingers = event?.pointerCount
                    val action = event?.action

                    if (fingers != 0){
                        mFingers != fingers
                    }

                    if ((action == MotionEvent.ACTION_POINTER_DOWN ) || (action == MotionEvent.ACTION_POINTER_2_DOWN)&& fingers == this@DebugTool.fingers){
                        mIsPressed = true
                        handler.postDelayed(runnable, delay.toLong())
                        return true
                    }
                    if (action == MotionEvent.ACTION_POINTER_UP){
                        if (mIsPressed){
                            mIsPressed = false
                            handler.removeCallbacks(runnable)
                        }
                    }

                }
                catch (e : Exception){
                    Log.e(TAG,"ERROR ON TOUCH")
                }
                catch (e : Error){
                    Log.e(TAG,"ERROR ON TOUCH")
                }

                return true   //setOnTouchListener need to detect movement one by one, so we need to return true for enter the next detection
            }
        })


// ------------------------------initialize the adapter for the original functions-------------------------------------

        adapter1.addListener(object : OptionListener {
            override fun onClickOption(item: String, position: Int) {
                Hyperion.open()
            }
        },0)

        adapter1.addListener(object : OptionListener {
            override fun onClickOption(item: String, position: Int) {
                val send = SendRealm()
                send.SendDatabaseByMail(context,databasePath,AddressMail,appName.toString())
            }
        },1)

        adapter1.addListener(object : OptionListener {
            override fun onClickOption(item: String, position: Int) {
                val send = SendLogcat()
                send.SendLogcatByMail(context,logcatPath,AddressMail,appName.toString())
            }
        },2)

        adapter1.addListener(object : OptionListener {
            override fun onClickOption(item: String, position: Int) {
                val send = SendZip()
                send.sendZip(context,databasePath,ZipPassword,AddressMail,appName.toString())
            }
        },3)

// ------------------------------initialize the adapter for the original functions-------------------------------------

        val cleanLog = CleanLogs()
        cleanLog.CheckAndCleanLog(databasePath)

    }

    open fun AddOptions(option:String, listener : OptionListener){
        adapter1.addinlist(option)
        adapter1.addListener(listener,numberIndex)
        numberIndex++
    }



    @SuppressLint("InflateParams")
    open fun showList(){
        view = LayoutInflater.from(context).inflate(R.layout.listview,null)
        val builder = AlertDialog.Builder(context,R.style.AlertDialog)
        builder.setTitle("DebugTool")
            .setView(view)
            .setPositiveButton("cancel",null)
        val dialog : AlertDialog = builder.create()

        listView = view.findViewById(R.id.listView)
        listView.adapter = adapter1


        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            Toast.makeText(context,"DebugTool Closed",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }




}
