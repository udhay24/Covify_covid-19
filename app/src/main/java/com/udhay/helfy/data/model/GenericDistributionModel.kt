package com.udhay.helfy.data.model

import java.io.Serializable

data class GenericDistributionModel(
    val confirmedCases: Int,
    val hospitalizedCases: Int,
    val recoveredCases: Int,
    val deaths: Int
) : Serializable