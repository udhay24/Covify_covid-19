package com.udhay.helfycovid_19.data.model

class StateModel : ArrayList<StateModel.StateModelItem>(){
    data class StateModelItem(
        val confirmed_cases: Int, // 382
        val datewise_data: List<Any>,
        val deaths: Int, // 7
        val hospitalised_cases: Int, // 350
        val icu_cases: Int, // 0
        val nationality: Nationality,
        val recovered_cases: Int, // 24
        val state: String, // Maharashtra
        val testing_capacities: Int, // 6000
        val total_testing_facilities: Int, // 60
        val transmission_source: TransmissionSource
    ) {
        data class Nationality(
            val foreign: Int, // 41
            val local: Int // 341
        )
    
        data class TransmissionSource(
            val imported: Int, // 177
            val local: Int, // 158
            val unknown: Int // 47
        )
    }
}