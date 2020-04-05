package com.udhay.helfy.data.model

data class HelplineModel(
    val data : List<State>
) {
    data class State (
        val name: String,
        val helpline: List<String>
    )
}