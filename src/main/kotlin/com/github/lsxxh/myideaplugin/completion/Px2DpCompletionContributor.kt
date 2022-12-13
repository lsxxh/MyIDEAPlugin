package com.github.lsxxh.myideaplugin.completion

import com.github.lsxxh.myideaplugin.services.MyProjectService
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.tree.IElementType
import com.intellij.psi.xml.XmlElementType
import com.intellij.util.ProcessingContext
import java.awt.Color
import java.util.*

class Px2DpCompletionContributor : CompletionContributor() {
    init {
        MyProjectService.logger.warn("yyz, init Px2DpCompletion")
        // 构造方法中，仅执行一次。可在此处添加推荐菜单项
        extend(
            CompletionType.BASIC,
            //PlatformPatterns.psiElement(XmlElementType.XML_ATTRIBUTE_VALUE).withLanguage(XMLLanguage.INSTANCE), //暂没反应
            //PlatformPatterns.psiElement(IElementType("VALUE", XMLLanguage.INSTANCE)), //暂没反应
            PlatformPatterns.psiElement(), //暂没反应
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    //输入a即会给补全提示，enter会输入: addElement in extend
                    //按p、px、pd、pxd不会给此补全提示
                    result.addElement(LookupElementBuilder.create("addElement in extend"))
                }

            }
        )

        //改用fillCompletionVariants
        /*extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(XmlElementType.XML_ATTRIBUTE_VALUE).withLanguage(XMLLanguage.INSTANCE),
                Px2DpProvider(*ConstValue.TO_TIPS)
        )*/

        /*
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement<PsiElement>(CssElementTypes.CSS_IDENT).withParent(PlatformPatterns.psiElement<PsiElement>(CssElementTypes.CSS_NUMBER_TERM)),
                PX2RWDProvider(LogicUtils.getLogic().generateObject(arrayOfNulls<String>(TO_RWD_TIPS.length)) { tips -> Arrays.fill(tips, AUTO_COMPLETION_TAG) })
        )*/
    }

    // 可在此处添加推荐菜单项，IDEA中每敲入一个字符，则会触发一次此方法
    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        super.fillCompletionVariants(parameters, result);	// 此result为通过extend添加的菜单项
        val offset = parameters.offset
        val document = parameters.editor.document
        val lineStartOffset = document.getLineStartOffset(document.getLineNumber(offset))
        //val lineContent = document.getText(TextRange(lineStartOffset, offset))
        val lineContent = document.getText(TextRange(lineStartOffset, offset))
        MyProjectService.logger.warn("yyz, lineContent: $lineContent")
        //if (lineContent.endsWith('p') || lineContent.endsWith("px")) {
            MyProjectService.logger.warn("yyz, prepare to addElement")
            result.addElement(
                LookupElementBuilder.create("test px2dp enter")
                    //按t、test会给此补全提示:test px2dp enter,前面有10不行,可能与myToString打印的prefixMatcher有关
                    .withLookupString("t")
                    .withLookupString("test")

                    //按p、px、pd、pxd会给此补全提示:test px2dp enter,前面有10不行
                    //.withLookupString("pxd")
                    //.withLookupString("pd")
                    //.withPresentableText("px -> dp")
                    .withCaseSensitivity(true)
                    .withInsertHandler { ctx, _: LookupElement ->
                        val doc = ctx.document
                        val insertOffset = ctx.selectionEndOffset
                        //此时在补全提示出来后按enter会输入: test px2dp enterInsert Test
                        doc.insertString(insertOffset, "Insert Test")
                    }
                    .withTypeText("From Px2Dp")
                    .withItemTextForeground(Color.CYAN)
                    .bold()
            )
//        }
        //super.fillCompletionVariants(parameters, result)
        MyProjectService.logger.warn("yyz, result: $result") //yyz, result: com.intellij.codeInsight.completion.impl.CompletionServiceImpl$CompletionResultSetImpl@61cc708b
        MyProjectService.logger.warn("yyz, result str: ${result.myToString()}") //yyz, result str: this.prefixMatcher.toString(): 10px this.isStopped: false
        WordCompletionContributor.addWordCompletionVariants(result, parameters, Collections.emptySet())
        //result.stopHere()
    }

    /*方法二(推荐)：使用fillCompletionVariants
    每次有变化时都会重新执行这个方法
    会将建议通过result.addElement方法添加
    当匹配上LookupElementBuilder设置的匹配字段时就会出现在建议列表中
    override fun fillCompletionVariants(parameters: CompletionParameters, result: CompletionResultSet) {
        val offset = parameters.offset;
        val document = parameters.editor.document;
        val lineStartOffset = document.getLineStartOffset(document.getLineNumber(offset))
        val text = document.getText(TextRange.create(lineStartOffset, offset))
        when (text) {
            "my" -> {
                result.addElement(LookupElementBuilder.create("fafafa".substring(1)).withPresentableText("aaaaa").withCaseSensitivity(true).bold()
            } else -> {
            if (text.endsWith("!")) {
                return;
            }
            super.fillCompletionVariants(parameters, result);
            WordCompletionContributor.addWordCompletionVariants(result, parameters, Collections.emptySet());
        }
        }
        result.stopHere()
    }*/

    /*//LookupElementBuilder的复杂构造
    result.addElement(
    LookupElementBuilder.create("最终回车显示文本——也可以用来做匹配本文")
    .withLookupString("my额外的匹配文本1")
    .withLookupString("my额外的匹配文本2")
    .withPresentableText("一级提示文本")
    .withCaseSensitivity(true)//大小写不敏感
    .appendTailText("二级提示文本", true)
    //.withIcon()
    .withItemTextForeground(Color.BLUE)//一级提示文本颜色
    .withInsertHandler { insertionContext, lookupElement ->
        val document: Document = insertionContext.document
        var insertPosition: Int = insertionContext.selectionEndOffset
        document.insertString(insertPosition, "  => ")
        insertPosition += 5
        insertionContext.editor.caretModel.currentCaret.moveToOffset(insertPosition)
    }
    .withStrikeoutness(true)//添加表示废弃的删除线
    .withTypeText("最右侧提示文本")
    .bold()
    )*/

    /*val file: PsiFile = parameters.originalFile //当前文件对象
    val currentFile:String=file.name //当前文件字符串名
    val position: PsiElement = parameters.position //当前编写的位置
    val project: Project = position.project //项目对象
    val projectFile: VirtualFile? = project.projectFile //项目根目录*/


}

private fun CompletionResultSet.myToString(): String {
    //yyz, result str: this.prefixMatcher.toString(): 10px this.isStopped: false
    return "this.prefixMatcher.toString(): ${this.prefixMatcher.toString()}" +
            " this.isStopped: ${this.isStopped}"
}
