package com.example.data.di

import com.cesar.data.repository.ListRepository
import com.cesar.domain.repository.IListRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    factory<IListRepository> { ListRepository(androidContext()) }
}



