package com.github.lsxxh.myideaplugin.services

import com.intellij.openapi.project.Project
import com.github.lsxxh.myideaplugin.MyBundle
/**
 * 模板生成的，暂时未用上
 */
class MyProjectService(project: Project) {

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
