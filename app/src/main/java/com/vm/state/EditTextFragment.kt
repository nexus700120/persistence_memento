package com.vm.state

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class EditTextFragment : Fragment() {

    private val state: State by lazy { stateViewModel<State>() }

    private lateinit var persistenceEditText: EditText
    private lateinit var runtimeEditText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val randomColor = state.backgroundColor
            ?: Color.rgb(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
        state.backgroundColor = randomColor
        view.setBackgroundColor(randomColor)

        persistenceEditText = view.findViewById(R.id.edit_text_persist)
        persistenceEditText.setText(state.persistenceText)

        runtimeEditText = view.findViewById(R.id.edit_text_runtime)
        runtimeEditText.setText(state.runtimeText)

        view.findViewById<Button>(R.id.push).setOnClickListener {
            requireNotNull(activity).supportFragmentManager
                .beginTransaction()
                .replace(android.R.id.content, EditTextFragment())
                .addToBackStack(null)
                .commit()
        }

    }

    override fun onStop() {
        super.onStop()
        state.persistenceText = persistenceEditText.text.toString()
        state.runtimeText = runtimeEditText.text.toString()
    }

    class State(handleState: SavedStateHandle) : ViewModel() {
        // Persistence properties
        var persistenceText: String? by PersistenceProperty(handleState, "persistence_text")
        var backgroundColor: Int? by PersistenceProperty(handleState, "background")
        // Runtime properties
        var runtimeText: String? = null
    }
}