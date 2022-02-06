package com.spidugu.nycshools.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spidugu.nycshools.view.NYCSchoolDetailFragmentArgs

class NYCSchoolDetailViewModel : ViewModel() {

    private val _dbValueData = MutableLiveData<String>()
    val dbnValueLiveData: LiveData<String> = _dbValueData

    private val _schoolNameValueData = MutableLiveData<String>()
    val schoolNameValueLiveData: LiveData<String> = _schoolNameValueData

    private val _satMathAvgValueData = MutableLiveData<String>()
    val satMathAvgValueLiveData: LiveData<String> = _satMathAvgValueData

    private val _satReadingAvgValueData = MutableLiveData<String>()
    val satReadingAvgValueLiveData: LiveData<String> = _satReadingAvgValueData
    private val _satTestTakersValueData = MutableLiveData<String>()
    val satTestTakersValueLiveData: LiveData<String> = _satTestTakersValueData

    private val _satWritingAvgValueData = MutableLiveData<String>()
    val satWritingAvgValueLiveData: LiveData<String> = _satWritingAvgValueData

    fun onViewCreated(args: NYCSchoolDetailFragmentArgs) {
        val schoolInfo = args.schoolInfo
        _dbValueData.value = schoolInfo.dbn
        _schoolNameValueData.value = schoolInfo.schoolName
        _satMathAvgValueData.value = schoolInfo.satMathAvg
        _satReadingAvgValueData.value = schoolInfo.satReadingAvg
        _satTestTakersValueData.value = schoolInfo.satTestTakers
        _satWritingAvgValueData.value = schoolInfo.satWritingAvg
    }
}