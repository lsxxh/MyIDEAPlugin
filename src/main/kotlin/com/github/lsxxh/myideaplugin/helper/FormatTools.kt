package com.github.lsxxh.myideaplugin.helper

import com.example.plugindemo.constvalue.ConstValue.CHAR_QUOTE
import com.example.plugindemo.model.ActionPerformer
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.util.TextRange
import java.util.*
import java.util.regex.Pattern


object FormatTools{
    private var preset_density_scale = 0.5

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
 * @param content           style文本
 * @param position        起止index
 * @param actionPerformer 动作参数
 */
private fun formatText(content: String, position: Array<Int>, actionPerformer: ActionPerformer) {
    //filter所要求的写法FormatTools::isNumeric要求一个类文件，顶层文件不行
    Optional.of(content).filter(FormatTools::isNumeric).ifPresent {
        WriteCommandAction.runWriteCommandAction(actionPerformer.project) {
            actionPerformer.document?.replaceString(
                position[0],
                position[1],
                //成功调用formatNearCode测试将: xx="5px=>xx="50dp
                (content.toInt() * preset_density_scale).toInt().toString() + "dp\""
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