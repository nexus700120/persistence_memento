package com.vm.state

import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateVMFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PersistenceProperty<T>(
    private val handle: SavedStateHandle,
    private val key: String
) : ReadWriteProperty<Any, T?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return handle[key]
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        handle[key] = value
    }
}

inline fun <reified T : ViewModel> Fragment.stateViewModel(): T =
    ViewModelProvider(this, SavedStateVMFactory(this)).get(T::class.java)