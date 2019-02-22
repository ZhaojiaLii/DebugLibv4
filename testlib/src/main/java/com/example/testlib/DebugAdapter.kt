package com.example.testlib


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.testlib.R
import com.example.testlib.OptionListener

open class DebugAdapter : BaseAdapter() {

    private lateinit var mInflater : LayoutInflater
    private lateinit var mContext : Context
    private lateinit var mList : ArrayList<String>
    private var mListener = ArrayList<OptionListener>()



    open fun addListener (aListener : OptionListener, position : Int){
        mListener.add(position,aListener)
    }

    open fun sendListener(item : String, position : Int){
        mListener[position].onClickOption(item,position)
    }

    open fun CustomAdapter(context : Context, aList : ArrayList<String>){   //constructor of the class, put all the variables inside to initialize
        mContext = context
        mList = aList
        mInflater = LayoutInflater.from(mContext)
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutItem : View
        if (convertView == null){
            layoutItem = mInflater.inflate(R.layout.text,parent,false)
        }else{
            layoutItem = convertView
        }

        val option : TextView = layoutItem.findViewById(R.id.option)
        option.text = mList[position]

        option.setOnClickListener {
            sendListener(mList[position],position)
        }

        return layoutItem
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mList.size
    }

    open fun addinlist(element : String){
        mList.add(element)
    }


}