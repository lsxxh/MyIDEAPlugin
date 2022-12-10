package com.github.lsxxh.myideaplugin.services

import com.github.lsxxh.myideaplugin.MyBundle

class MyApplicationService {

    init {
        println(MyBundle.message("applicationService"))

        System.getenv("CI")
//        System.getenv("CI")
//                ?: TODO("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
//
    }
}
