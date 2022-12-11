package com.github.lsxxh.myideaplugin.services

import com.github.lsxxh.myideaplugin.MyBundle

/**
 * 模板生成的，暂时未用上
 */
class MyApplicationService {
    init {
        println(MyBundle.message("applicationService"))

        System.getenv("CI")
//        System.getenv("CI")
//                ?: TODO("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
//
    }
}
