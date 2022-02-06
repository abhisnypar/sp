package com.spidugu.nycshools.repository

import com.spidugu.nycshools.api.ChaseResponse
import com.spidugu.nycshools.util.ResponseResult

interface IChaseRepository {

    suspend fun fetchNYCSchoolsList(): ResponseResult<List<ChaseResponse>>
}