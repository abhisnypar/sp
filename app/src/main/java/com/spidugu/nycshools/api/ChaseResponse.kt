package com.spidugu.nycshools.api

/**
 *  {
"dbn": "01M292",
"school_name": "HENRY STREET SCHOOL FOR INTERNATIONAL STUDIES",
"num_of_sat_test_takers": "29",
"sat_critical_reading_avg_score": "355",
"sat_math_avg_score": "404",
"sat_writing_avg_score": "363"
},
 */
data class ChaseResponse(
    val dbn: String,
    val school_name: String,
    val num_of_sat_test_takers: String,
    val sat_critical_reading_avg_score: String,
    val sat_math_avg_score: String,
    val sat_writing_avg_score: String
)