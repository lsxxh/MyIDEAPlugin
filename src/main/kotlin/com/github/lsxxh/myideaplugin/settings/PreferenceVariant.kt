package com.github.lsxxh.myideaplugin.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "px2dp", storages = [Storage("px2dp.xml")])
object PreferenceVariant: PersistentStateComponent<PreferenceVariant> {
    var presetScale = 0.5F
    override fun getState(): PreferenceVariant {
        return this
    }

    override fun loadState(state: PreferenceVariant) {
        //XmlSerializerUtil - ideaU自带
        return XmlSerializerUtil.copyBean(state, this)
    }
}