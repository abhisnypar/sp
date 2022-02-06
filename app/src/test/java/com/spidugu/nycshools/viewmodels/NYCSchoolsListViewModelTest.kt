package com.spidugu.nycshools.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.spidugu.nycshools.api.ChaseApi
import com.spidugu.nycshools.api.ChaseResponse
import com.spidugu.nycshools.repository.ChaseRepositoryImpl
import com.spidugu.nycshools.repository.IChaseRepository
import com.spidugu.nycshools.repository.SchoolInfo
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NYCSchoolsListViewModelTest {
    private lateinit var viewModel: NYCSchoolsListViewModel
    private val dispatcher = TestCoroutineDispatcher()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    private val chaseResponseList = arrayListOf<ChaseResponse>().apply {
        add(ChaseResponse("dbn", "SchoolName", "10", "3", "100", "56"))
    }

    private val schoolInfo = SchoolInfo("dbn", "SchoolName", "10", "3", "100", "56")

    private val mockApi: ChaseApi = mock {
        runBlocking {
            whenever(it.getNYCSchoolsList()).thenAnswer {
                chaseResponseList
            }
        }
    }

    private val exceptionMockApi: ChaseApi = mock {
        runBlocking {
            whenever(it.getNYCSchoolsList()).thenThrow(RuntimeException())
        }
    }

    @Test
    fun testFetchData_onSuccessReceived() {
        runBlocking {
            val repo: IChaseRepository = ChaseRepositoryImpl(mockApi, Dispatchers.IO)
            launch(Dispatchers.IO) {  // Will be launched in the mainThreadSurrogate dispatcher
                viewModel = NYCSchoolsListViewModel(repo)
                delay(2000)
                withContext(Dispatchers.Main) {
                    val value: List<SchoolInfo> =
                        viewModel.schoolsInfoListLiveData.value ?: emptyList()

                    assertEquals(value.first().schoolName, "SchoolName")
                    assertEquals(value.first().dbn, "dbn")
                    assertEquals(value.first().satTestTakers, "10")
                    assertEquals(value.first().satReadingAvg, "3")
                    assertEquals(value.first().satMathAvg, "100")
                    assertEquals(value.first().satWritingAvg, "56")
                }
            }
        }
    }

    @Test
    fun testFetchData_onThrowsException() {
        runBlocking {
            val repo: IChaseRepository = ChaseRepositoryImpl(exceptionMockApi, Dispatchers.IO)
            launch {
                viewModel = NYCSchoolsListViewModel(repo)
                delay(2000)
                withContext(Dispatchers.Main) {
                    val value = viewModel.schoolsInfoListLiveData.value ?: emptyList()
                    assertTrue(value.isEmpty())
                    assertTrue(viewModel.showErrorInfoLiveData.value ?: false)
                }
            }
        }
    }

    @Test
    fun testOnClickItem_navigateToDetailScreen() {
        runBlocking {
            val repo: IChaseRepository = ChaseRepositoryImpl(exceptionMockApi, Dispatchers.IO)
            launch {
                viewModel = NYCSchoolsListViewModel(repo)
                delay(2000)
                viewModel.onClickItem(schoolInfo)
                withContext(Dispatchers.Main) {
                    val navDirections = viewModel.navigateToLiveData.value?.getEventIfNotHandled()
                    assertNotNull(navDirections)
                    assertEquals(navDirections?.actionId, 2131230784)
                }
            }
        }
    }
}