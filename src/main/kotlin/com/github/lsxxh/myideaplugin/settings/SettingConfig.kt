package com.github.lsxxh.myideaplugin.settings

import com.example.plugindemo.constvalue.ConstValue
import com.intellij.openapi.options.SearchableConfigurable
import settings.SettingsPage
import java.util.*
import java.util.function.Predicate
import javax.swing.JComponent

object SettingConfig : SearchableConfigurable{
    private lateinit var mainGUI: SettingsPage

    override fun createComponent(): JComponent? {
        mainGUI = SettingsPage()
        return mainGUI.rootPanel
    }

    override fun isModified() = ifEdited()

    private fun ifEdited(): Boolean {
        return anyEdited({ param -> Objects.equals(param[0], param[1]) },
        arrayOf(PreferenceVariant.presetScale, mainGUI.presetDensityScale))
    }

    //Make sure you use import correct, or No type arguments expected for class ExceptionPredicate
    private fun <T> anyEdited(predicate: Predicate<T>, vararg t: T): Boolean {
        return java.util.Arrays.stream(t).anyMatch(predicate)
    }
    //继承Predicate<T>使其支持泛型类型参数<T> //暂时无需重写故直接用Predicate<T>
    //interface ExceptionPredicate<T> : Predicate<T> { //import java.util.function.Predicate
    //}

    override fun apply() {
        PreferenceVariant.presetScale = mainGUI.presetDensityScale.text.toFloat()
    }

    override fun getDisplayName(): String {
        return ConstValue.PLUGIN_NAME
    }

    override fun getId(): String {
        return displayName
    }
}