package com.spidugu.nycshools.repository

import android.os.Parcelable
import com.spidugu.nycshools.api.ChaseResponse
import kotlinx.parcelize.Parcelize

/**
 * Parcelable object to used in case between fragments
 * or local usage of data.
 */
@Parcelize
data class SchoolInfo(
    val dbn: String,
    val schoolName: String,
    val satTestTakers: String,
    val satReadingAvg: String,
    val satMathAvg: String,
    val satWritingAvg: String
) : Parcelable {

    companion object {

        fun mapToLocal(response: ChaseResponse): SchoolInfo =
            SchoolInfo(
                dbn = response.dbn,
                schoolName = response.school_name,
                satTestTakers = response.num_of_sat_test_takers,
                satReadingAvg = response.sat_critical_reading_avg_score,
                satMathAvg = response.sat_math_avg_score,
                satWritingAvg = response.sat_writing_avg_score
            )
    }
}