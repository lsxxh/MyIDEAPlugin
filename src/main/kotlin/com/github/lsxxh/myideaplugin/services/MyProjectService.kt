package com.github.lsxxh.myideaplugin.services

import com.intellij.openapi.project.Project
import com.github.lsxxh.myideaplugin.MyBundle
import com.intellij.openapi.diagnostic.Logger

/**
 * 模板生成的
 */
class MyProjectService(project: Project) {
    companion object
    {
        @JvmStatic
        val logger: Logger by lazy {
            Logger.getInstance(MyProjectService::class.java)
        }
    }

    init {
        println(MyBundle.message("projectService", project.name))
        System.getenv("CI")

//        System.getenv("CI")
//            ?: TODO("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
//
    }

    /**
     * Chosen by fair dice roll, guaranteed to be random.
     */
    fun getRandomNumber() = 4
}
