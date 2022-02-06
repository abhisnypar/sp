package com.spidugu.nycshools.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.spidugu.nycshools.repository.SchoolInfo
import com.spidugu.nycshools.view.NYCSchoolDetailFragmentArgs
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class NYCSchoolDetailViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun onViewCreated_onArgumentsReceived() {
        val viewModel = NYCSchoolDetailViewModel()
        val args: NYCSchoolDetailFragmentArgs = mock {
            whenever(it.schoolInfo).thenAnswer {
                SchoolInfo(
                    dbn = "0124N23",
                    schoolName = "HENRY STREET SCHOOL FOR INTERNATIONAL",
                    satTestTakers = "29",
                    satReadingAvg = "355",
                    satMathAvg = "404",
                    satWritingAvg = "363"
                )
            }
        }

        viewModel.onViewCreated(args)

        val dbn = viewModel.dbnValueLiveData.value
        val schoolName = viewModel.schoolNameValueLiveData.value
        val satTestTakers = viewModel.satTestTakersValueLiveData.value
        val satReadingAvg = viewModel.satReadingAvgValueLiveData.value
        val satMathAvg = viewModel.satMathAvgValueLiveData.value
        val satWritingAvg = viewModel.satWritingAvgValueLiveData.value

        assertEquals("0124N23", dbn)
        assertEquals("HENRY STREET SCHOOL FOR INTERNATIONAL", schoolName)
        assertEquals("363", satWritingAvg)
        assertEquals("29", satTestTakers)
        assertEquals("355", satReadingAvg)
        assertEquals("404", satMathAvg)
    }
}