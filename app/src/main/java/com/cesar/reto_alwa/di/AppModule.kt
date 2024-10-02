package com.cesar.reto_alwa.di

import com.cesar.reto_alwa.viewModel.List2ViewModel
import com.cesar.reto_alwa.viewModel.List3ViewModel
import com.cesar.reto_alwa.viewModel.List4ViewModel
import com.cesar.reto_alwa.viewModel.MoreUsedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MoreUsedViewModel(get()) }
    viewModel { List2ViewModel(get()) }
    viewModel { List4ViewModel(get()) }
    viewModel { List3ViewModel(get()) }
}