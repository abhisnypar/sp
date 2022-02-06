package com.spidugu.nycshools.di

import com.spidugu.nycshools.viewmodels.NYCSchoolDetailViewModel
import com.spidugu.nycshools.viewmodels.NYCSchoolsListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

// viewModel Module for Koin, dependency injection.

val viewModelModule = module {

    viewModel {
        NYCSchoolsListViewModel(repository = get())
    }

    viewModel {
        NYCSchoolDetailViewModel()
    }
}