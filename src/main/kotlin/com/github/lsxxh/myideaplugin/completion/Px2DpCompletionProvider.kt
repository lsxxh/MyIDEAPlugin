package com.github.lsxxh.myideaplugin.completion

import com.example.plugindemo.constvalue.ConstValue.PX_TO_DP
import com.example.plugindemo.constvalue.ConstValue.TO_TIPS
import com.example.plugindemo.model.ActionPerformer
import com.github.lsxxh.myideaplugin.helper.FormatTools
import com.github.lsxxh.myideaplugin.services.MyProjectService
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext
import java.util.*
import java.util.function.IntFunction
import java.util.stream.Collectors
import java.util.stream.IntStream

class Px2DpCompletionProvider(vararg items: String) : CompletionProvider<CompletionParameters>() {
    private var myItems: Array<String>
    init {
        myItems = items as Array<String>
        //MyProjectService.logger.warn("yyz, myItems: $myItems") //yyz, myItems: [Ljava.lang.String;@508070d1
        MyProjectService.logger.warn("yyz, myItems: ${myItems.toString()}")
    }
    override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet) {
        Optional.of(Arrays.stream<String>(TO_TIPS).filter { item ->
            filterCompletionItem.test(item, ActionPerformer.getActionPerformer(parameters.editor.project!!, parameters.editor)!!)
        }.collect(Collectors.toList())).ifPresent { tips: List<String> ->
            result.addAllElements(IntStream.range(0, tips.size).mapToObj(IntFunction<LookupElementBuilder> { i: Int ->
                LookupElementBuilder.create(myItems[i])
                        .withCaseSensitivity(false).withTailText(tips[i])
                        .withInsertHandler(PX_DP_HANDLER)
        }).collect(Collectors.toList()))
            MyProjectService.logger.warn("yyz, result: $result")
        }
    }

    private val filterCompletionItem: TipsPredicate<String, ActionPerformer> = TipsPredicate {
        item: String?, _: ActionPerformer ->
        item == PX_TO_DP
    }

    private fun interface TipsPredicate<T, E> {
        fun test(t: T, e: E): Boolean
    }

    private val PX_DP_HANDLER = InsertHandler { context: InsertionContext, item: LookupElement? ->
        Optional.of(ActionPerformer.getActionPerformer(context.project, context.editor)!!)
                .ifPresent { actionPerformer ->
                    FormatTools.formatCaretBeforeCode(actionPerformer)
                }
    }
}