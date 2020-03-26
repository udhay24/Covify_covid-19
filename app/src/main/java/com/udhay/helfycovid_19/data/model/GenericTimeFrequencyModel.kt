package com.udhay.helfycovid_19.data.model

import java.io.Serializable

data class GenericTimeFrequencyModel(
    val cases: List<TimeCases>
): Serializable {
    data class TimeCases(
        val date: String,
        val confirmedCases: Int,
        val recovered: Int,
        val hospitalizedCases: Int,
        val deaths: Int
    )
}