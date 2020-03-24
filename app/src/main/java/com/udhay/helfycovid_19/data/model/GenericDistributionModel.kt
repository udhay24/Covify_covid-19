package com.udhay.helfycovid_19.data.model

import android.os.Parcelable
import java.io.Serializable

data class GenericDistributionModel(
    val confirmedCases: Int,
    val hospitalizedCases: Int,
    val icuCases: Int,
    val recoveredCases: Int,
    val deaths: Int
) : Serializable