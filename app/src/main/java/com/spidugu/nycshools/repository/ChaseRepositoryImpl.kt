package com.spidugu.nycshools.repository

import com.spidugu.nycshools.api.ChaseApi
import com.spidugu.nycshools.api.ChaseResponse
import com.spidugu.nycshools.util.ResponseResult
import com.spidugu.nycshools.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Repository Class which will have the backend logic.
 */
class ChaseRepositoryImpl(var chaseApi: ChaseApi, var dispatcher: CoroutineDispatcher) :
    IChaseRepository {

    override suspend fun fetchNYCSchoolsList(): ResponseResult<List<ChaseResponse>> {
        return safeApiCall(dispatcher) {
            chaseApi.getNYCSchoolsList()
        }
    }
}