package com.example.testlib

open class Options {

    open fun getList():ArrayList<String>{
        val listOpts = ArrayList<String>()

        listOpts.add("open Hyperion")
        listOpts.add("send Realm")
        listOpts.add("send Log")
        listOpts.add("compress")

        return listOpts
    }


}