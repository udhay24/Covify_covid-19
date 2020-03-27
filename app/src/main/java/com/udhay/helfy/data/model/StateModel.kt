package com.udhay.helfy.data.model

class StateModel : ArrayList<StateModel.StateModelItem>(){
    data class StateModelItem(
        val __v: Int, // 0
        val _id: String, // 5e7ade7fa05e8151321c7d0b
        val confirmed_case: Int, // 1
        val datewise_data: List<DatewiseData>,
        val death: Int, // 0
        val hospitalised_case: Int, // 1
        val nationality: Nationality,
        val recovered: Int, // 0
        val state: String // Manipur
    ) {
        data class DatewiseData(
            val _id: String, // 5e7ade7fa05e8151321c7d0c
            val confirmed_case: Int, // 1
            val date: String, // 24/Mar/2020
            val death: Int, // 0
            val hospitalised_case: Int, // 1
            val recovered: Int // 0
        )
    
        data class Nationality(
            val foreign: Int, // 0
            val indian: Int, // 0
            val unknwon: Int // 1
        )
    }
}