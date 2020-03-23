package com.udhay.helfycovid_19.data.model

data class WorldModel(
    val confirmed_cases: Int, // 382
    val country_data: List<CountryData>,
    val hospitalised_cases: Int, // 350
    val icu_cases: Int, // 0
    val recovered_caes: Int // 24
) {
    data class CountryData(
        val confirmed_cases: Int, // 382
        val country: String, // India
        val deaths: Int, // 7
        val hospitalised_cases: Int, // 350
        val icu_cases: Int, // 0
        val recovered_cases: Int // 24
    )
}