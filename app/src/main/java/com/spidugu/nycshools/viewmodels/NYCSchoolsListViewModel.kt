package com.spidugu.nycshools.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.spidugu.nycshools.api.ChaseResponse
import com.spidugu.nycshools.repository.IChaseRepository
import com.spidugu.nycshools.repository.SchoolInfo
import com.spidugu.nycshools.util.Event
import com.spidugu.nycshools.util.ResponseResult
import com.spidugu.nycshools.view.NYCSchoolsListFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NYCSchoolsListViewModel(var repository: IChaseRepository) : ViewModel() {

    private var _schoolsInfoListData = MutableLiveData<List<SchoolInfo>>()
    val schoolsInfoListLiveData: LiveData<List<SchoolInfo>> = _schoolsInfoListData

    private var _showProgressBarData = MutableLiveData<Int>()
    val showProgressBarLiveData = _showProgressBarData

    private var _showRefreshSpinner = MutableLiveData<Boolean>()
    val showRefreshSpinner = _showRefreshSpinner

    private var _showErrorInfoData = MutableLiveData<Boolean>()
    val showErrorInfoLiveData: LiveData<Boolean> = _showErrorInfoData

    private var _showErrorInfoDataText = MutableLiveData<String?>()
    val showErrorInfoDataText: LiveData<String?> = _showErrorInfoDataText

    private var _navigateToData = MutableLiveData<Event<NavDirections>>()
    val navigateToLiveData: LiveData<Event<NavDirections>> = _navigateToData

    init {
        //for unWanted api calling while screen rotates.
        onViewCreated()
    }

    fun onViewCreated() {
        _showProgressBarData.postValue(View.VISIBLE)
        fetchNYCSchoolsList()
    }

    /**
     * Backend API request to fetch NYC Schools List.
     */
    private fun fetchNYCSchoolsList() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.fetchNYCSchoolsList()) {
                is ResponseResult.Success -> {
                    onSuccess(result.value)
                }

                is ResponseResult.GenericError -> {
                    onErrorReceived(result.error)
                }

                is ResponseResult.NetworkError -> {
                    withContext(Dispatchers.Main) {
                        _showProgressBarData.value = View.GONE
                        _showErrorInfoData.value = true
                    }
                }
            }
        }
    }

    /**
     * Backend API request to fetch NYC Schools List, when error received
     */
    private suspend fun onErrorReceived(error: Throwable?) {
        withContext(Dispatchers.Main) {
            _showErrorInfoData.value = true
            _showProgressBarData.value = View.GONE
            _showRefreshSpinner.value = false
            _showErrorInfoDataText.value = error?.message
        }
    }

    /**
     * Backend API request to fetch NYC Schools List, onSuccess received
     */
    private suspend fun onSuccess(value: List<ChaseResponse>) {
        withContext(Dispatchers.Main) {
            _schoolsInfoListData.value = value.map {
                SchoolInfo.mapToLocal(it)
            }
            _showProgressBarData.value = View.GONE
            _showRefreshSpinner.value = false
        }
    }

    /**
     * on click of School item will navigate to the next detail screen.
     */
    fun onClickItem(schoolInfo: SchoolInfo?) {
        schoolInfo?.let {
            _navigateToData.value =
                Event(
                    NYCSchoolsListFragmentDirections.actionLandingFragmentToDetailFragment2(
                        schoolInfo
                    )
                )
        }
    }

    /***
     * Swipe to refresh clicked.
     */
    fun onRefreshClicked() {
        _showRefreshSpinner.value = true
        _showErrorInfoData.value = false
        _showProgressBarData.value = View.GONE
        fetchNYCSchoolsList()
    }
}