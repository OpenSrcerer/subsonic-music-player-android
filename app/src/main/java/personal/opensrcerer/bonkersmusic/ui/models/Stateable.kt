package personal.opensrcerer.bonkersmusic.ui.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class Stateable<T>(t: T) {
    private val state: MutableState<T> = mutableStateOf(t)

    fun value(): T { return state.value }

    infix fun to(t: T) { state.value = t }
}