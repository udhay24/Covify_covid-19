package com.udhay.helfy.data.model

data class HelplineModel(
    val `data`: List<Data>
) {
    data class Data(
        val state: State
    ) {
        data class State(
            val helpline: List<String>,
            val name: String // Kerala
        )
    }
}