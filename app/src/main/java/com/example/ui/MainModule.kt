package com.example.ui

import com.example.data.MainRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel(get()) }
    single { MainRepository(get(), get()) }
}