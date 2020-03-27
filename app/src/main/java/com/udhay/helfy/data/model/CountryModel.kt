package com.udhay.helfy.data.model

class CountryModel : ArrayList<CountryModel.CountryModelItem>(){
    data class CountryModelItem(
        val __v: Int, // 0
        val _id: String, // 5e7cec2b592ce03ddd04089c
        val confirmed_case: Int, // 722
        val datewise_data: List<DatewiseData>,
        val death: Int, // 14
        val hospitalised_case: Int, // 684
        val nationality: Nationality,
        val recovered: Int // 23
    ) {
        data class DatewiseData(
            val _id: String, // 5e7cec2b592ce03ddd04089d
            val confirmed_case: Int, // 1
            val date: String, // 30/01/2020
            val death: Int, // 0
            val hospitalised_case: Int, // 0
            val recovered: Int // 1
        )
    
        data class Nationality(
            val foreign: Int, // 362
            val indian: Int, // 360
            val unknwon: Int // 0
        )
    }
}