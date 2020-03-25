package com.udhay.helfycovid_19.data.model

class CountryModel : ArrayList<CountryModel.CountryCountModelItem>(){
    data class CountryCountModelItem(
        val __v: Int, // 0
        val _id: String, // 5e7ae0954a7e5c529e80974c
        val confirmed_case: Int, // 544
        val datewise_data: List<DatewiseData>,
        val death: Int, // 10
        val hospitalised_case: Int, // 516
        val nationality: Nationality,
        val recovered: Int // 17
    ) {
        data class DatewiseData(
            val _id: String, // 5e7ae0954a7e5c529e80974d
            val date: String, // 30/Jan/2020
            val death: Int, // 0
            val hospitalised_case: Int, // 0
            val recovered: Int // 1
        )
    
        data class Nationality(
            val foreign: Int, // 42
            val indian: Int, // 316
            val unknwon: Int // 186
        )
    }
}