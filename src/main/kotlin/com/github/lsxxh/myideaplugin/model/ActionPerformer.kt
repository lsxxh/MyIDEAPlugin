package com.example.plugindemo.model

import com.example.plugindemo.constvalue.ConstValue
import com.intellij.openapi.editor.CaretModel
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.SelectionModel
import com.intellij.openapi.project.Project


/**
 * 转换特定对象管理类
 *
 * @author sunqian
 * date 2019/6/10
 */

class ActionPerformer constructor(var project: Project, val editor: Editor) {
    val document: Document?
        get() = editor.document
    val selectionModel: SelectionModel?
        get() = editor.selectionModel
    val caretModel: CaretModel?
        get() = editor.caretModel
//    val constValue: ConstValue?
//        get() = ConstValue.getInstance()

    companion object {
        @Volatile
        private var actionPerformer: ActionPerformer? = null
        fun getActionPerformer(project: Project, editor: Editor): ActionPerformer? {
            if (actionPerformer == null || actionPerformer!!.project !== project || actionPerformer!!.editor !== editor) {
                synchronized(ActionPerformer::class.java) {
                    if (actionPerformer == null || actionPerformer!!.project !== project || actionPerformer!!.editor !== editor) {
                        actionPerformer = ActionPerformer(project, editor)
                    }
                }
            }
            return actionPerformer
        }
    }
}