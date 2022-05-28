/*
 * Made by Daniel Stefani for the Course Project in CS300, due June 7th 2022.
 * This work is licensed under The Unlicense, feel free to use as you wish.
 * All image assets belong to their respective owners. This project is for academic purposes only.
 */

package personal.opensrcerer.bonkersmusic.ui.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

// Utility class that wraps mutable states in an easier to use fashion
open class Stateable<T>(t: T) {
    private val state: MutableState<T> = mutableStateOf(t)

    fun value(): T { return state.value }

    infix fun changeTo(t: T) { state.value = t }
}