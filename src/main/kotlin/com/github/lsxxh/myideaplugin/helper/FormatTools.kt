package com.github.lsxxh.myideaplugin.helper

import com.example.plugindemo.constvalue.ConstValue.CHAR_QUOTE
import com.example.plugindemo.model.ActionPerformer
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.util.TextRange
import java.util.*
import java.util.regex.Pattern


object FormatTools{
    /**
     * 格式化光标处往前最近的一个可格化的长度
     *
     * @param actionPerformer 获取的动作参数
     */
    @JvmStatic
    fun formatNearCode(actionPerformer: ActionPerformer) {
        val document = actionPerformer.document
        val caretModel = actionPerformer.caretModel
        val lineNum = caretModel?.let { document?.getLineNumber(it.offset) }
        val lineStartOffset = lineNum?.let { document?.getLineStartOffset(it) }
        val lineContent = document?.getText(TextRange(lineStartOffset!!, caretModel.offset))
        val content = lineContent?.substring(getNearCodeIndex(lineContent) + 1, lineContent.length - 2)
        if (caretModel != null) {
            if (content != null) {
                formatText(
                        content,
                        arrayOf(caretModel.offset - content.length - 2, caretModel.offset),
                        actionPerformer
                )
            }
        }
    }

/**
 * 格式化指定的style文本
 *
 * @param style           style文本
 * @param position        起止index
 * @param actionPerformer 动作参数
 */
private fun formatText(style: String, position: Array<Int>, actionPerformer: ActionPerformer) {
    //filter所要求的写法FormatTools::isNumeric要求一个类文件，顶层文件不行
    Optional.of(style).filter(FormatTools::isNumeric).ifPresent {
        WriteCommandAction.runWriteCommandAction(actionPerformer.project) {
            actionPerformer.document?.replaceString(
                position[0],
                position[1],
                style + "0" + "dp"
            )
        }

    }
}

    private fun isNumeric(str: String) =
            Pattern.compile("-?[0-9]*(\\.[0-9]+)?").matcher(str).matches()

    /**
     * 取一个长度单位的起始index
     *
     * @param content 待处理文本
     * @return 返回文本中包含的一个长度单位的起始index
     */
    private fun getNearCodeIndex(content: String): Int {
        return content.indexOf(CHAR_QUOTE)
    }
}