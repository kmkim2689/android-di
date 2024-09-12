package com.kmlibs.supplin

import android.content.Context
import com.kmlibs.supplin.annotations.Module
import com.kmlibs.supplin.model.InjectionData
import kotlin.reflect.KClass
import kotlin.reflect.full.hasAnnotation

class InjectionBuilder {
    private lateinit var context: Context
    private var modules = listOf<Any>()

    fun context(context: Context) {
        this.context = context
    }

    fun module(vararg modules: KClass<*>) {
        modules.forEach { module ->
            if (module.hasAnnotation<Module>()) {
                this.modules += module.objectInstance ?: error("no object instance")
            }
        }
    }

    fun build(): InjectionData = InjectionData(modules, context)
}
