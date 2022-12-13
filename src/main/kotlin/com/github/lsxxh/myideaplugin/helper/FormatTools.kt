package com.github.lsxxh.myideaplugin.helper

import com.github.lsxxh.myideaplugin.constvalue.ConstValue.CHAR_QUOTE
import com.example.plugindemo.model.ActionPerformer
import com.github.lsxxh.myideaplugin.settings.PreferenceVariant
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.TextRange
import java.util.regex.Pattern
import kotlin.math.roundToInt


private fun String.getNumericStr(): String {
    FormatTools.logger.warn("yyz, this: $this") //WARN - yideaplugin.helper.FormatTools - yyz, this: 10px"
    val matcher = Pattern.compile("-?[0-9]*(\\.[0-9]+)?").matcher(this)
    //注意:想要matcher.group()方法，必须先matcher.find()方法，切忌！
    //要不然总是报：java.lang.IllegalStateException: No match found
    while (matcher.find()) {
        FormatTools.logger.warn("yyz, matcher.group(): ${matcher.group()}")
        return matcher.group()
    }
    return ""
}

object FormatTools{
    //Need: import com.intellij.openapi.diagnostic.Logger
    val logger = Logger.getInstance(FormatTools::class.java)
    //只有第一次起了作用,后面都是用的第一次设置的值,故用kt 委托
    //但委托是提供一个回调当观测的这个值改变时回调;此处直接在每次转换时获取该值即可
    /*private var preset_density_scale: Float by Delegates.observable(0.5f){
        property, oldValue, newValue -> print("property: $property $oldValue -> $newValue")
    }*/

    /**
     * 格式化光标处往前最近的一个可格化的长度
     *
     * @param actionPerformer 获取的动作参数
     */
    @JvmStatic
    fun formatCaretBeforeCode(actionPerformer: ActionPerformer) {
        val document = actionPerformer.document
        val caretModel = actionPerformer.caretModel
        val lineNum = caretModel?.let { document?.getLineNumber(it.offset) }
        val lineStartOffset = lineNum?.let { document?.getLineStartOffset(it) }
        val lineContent = document?.getText(TextRange(lineStartOffset!!, caretModel.offset))
        val content = lineContent!!.substring(getCaretBeforeCodeIndex(lineContent) + 1, lineContent.length)
        // LOG.debug("px: $hasPXUnitAppended content: $content") //无效
        logger.warn("yyz, lineContent: $lineContent, content: $content") //OK
        if (caretModel != null) {
            formatText(
                    content,
                    //arrayOf(caretModel.offset - content.length - 2, caretModel.offset),
                    arrayOf(caretModel.offset - content.length, caretModel.offset),
                    actionPerformer
            )
        }
    }

//    /**
//     * 格式化光标处往前最近的一个可格化的长度
//     *
//     * @param actionPerformer 获取的动作参数
//     */
    /*
    @JvmStatic
    fun formatCaretBeforeCode(actionPerformer: ActionPerformer) {
        val document = actionPerformer.document
        val caretModel = actionPerformer.caretModel
        val lineNum = caretModel?.let { document?.getLineNumber(it.offset) }
        val lineStartOffset = lineNum?.let { document?.getLineStartOffset(it) }
        val lineContent = document?.getText(TextRange(lineStartOffset!!, caretModel.offset))
        //  xx="10px or xx="10px" or xx="10
        val hasPXUnitAppended = lineContent!!.contains("px") && lineContent.length - lineContent.indexOf("px") <= 3
        val content = lineContent.substring(getCaretBeforeCodeIndex(lineContent) + 1, lineContent.length
                - if (hasPXUnitAppended) 2 else 0)
        // LOG.debug("px: $hasPXUnitAppended content: $content") //无效
        if (caretModel != null) {
            formatText(
                    content,
                    //arrayOf(caretModel.offset - content.length - 2, caretModel.offset),
                    arrayOf(caretModel.offset - content.length, caretModel.offset),
                    actionPerformer
            )
        }
    }*/

    /**
     * 格式化指定的style文本
     *
     * @param content           style文本
     * @param position        起止index
     * @param actionPerformer 动作参数
     */
    private fun formatText(content: String, position: Array<Int>, actionPerformer: ActionPerformer) {
        logger.warn("yyz, start: ${position[0]}, end: ${position[1]}")
        WriteCommandAction.runWriteCommandAction(actionPerformer.project) {
            actionPerformer.document?.replaceString(
                position[0],
                position[1],
                //成功调用formatCaretBeforeCode测试将: xx="5px=>xx="50dp
                (content.getNumericStr().toInt() * PreferenceVariant.presetScale).toInt().toString() + "dp\""
            )
        }
        logger.warn("yyz, content.getNumericStr(): ${content.getNumericStr()}")
        logger.warn("yyz, scale: ${PreferenceVariant.presetScale}")
    }

    /**
     * 格式化指定的style文本
     *
     * @param content           style文本
     * @param position        起止index
     * @param project         parameters.editor.project/
     *                        anActionEvent.project获取
     */
    fun formatText(content: String, position: Array<Int>, parameters: CompletionParameters) {
        val project = parameters.editor.project
        val document = parameters.editor.document
        logger.warn("yyz, start: ${position[0]}, end: ${position[1]}")
        WriteCommandAction.runWriteCommandAction(project) {
            document.replaceString(
                position[0],
                position[1],
                //成功调用formatCaretBeforeCode测试将: xx="5px=>xx="50dp
                //注:关于1/3(0.33)时转换小1<=kotlin中交互式运行>>> (9 * 0.33).toInt()//2
                //(9 * 0.33).roundToInt() //import kotlin.math.roundToInt
                //round(2.97) //Should be replaced with Kotlin function roundToInt =>2.97.roundToInt()
                //-换了roundToInt也一样.再测Float.roundToInt() OK,解决前述2.97.toInt() = 2问题
                (content.getNumericStr().toInt() * PreferenceVariant.presetScale).roundToInt().toString() + "dp\""
            )
        }
        logger.warn("yyz, content.getNumericStr(): ${content.getNumericStr()}")
        logger.warn("yyz, scale: ${PreferenceVariant.presetScale}")
    }

    //    /**
    // * 格式化指定的style文本
    // *
    // * @param content           style文本
    // * @param position        起止index
    // * @param actionPerformer 动作参数
    // */
    //private fun formatText(content: String, position: Array<Int>, actionPerformer: ActionPerformer) {
    //    //filter所要求的写法FormatTools::isNumeric要求一个类文件，顶层文件不行
    //    Optional.of(content).filter(FormatTools::isNumeric).ifPresent {
    //        WriteCommandAction.runWriteCommandAction(actionPerformer.project) {
    //            actionPerformer.document?.replaceString(
    //                position[0],
    //                position[1],
    //                //成功调用formatCaretBeforeCode测试将: xx="5px=>xx="50dp
    //                (content.toInt() * preset_density_scale).toInt().toString() + "dp\""
    //            )
    //        }
    //    }
    //}

    private fun isNumeric(str: String) =
            Pattern.compile("-?[0-9]*(\\.[0-9]+)?").matcher(str).matches()

    /**
     * 取一个长度单位的起始index
     *
     * @param content 待处理文本
     * @return 返回文本中包含的一个长度单位的起始index
     */
    private fun getCaretBeforeCodeIndex(content: String): Int {
        return content.indexOf(CHAR_QUOTE)
    }
}