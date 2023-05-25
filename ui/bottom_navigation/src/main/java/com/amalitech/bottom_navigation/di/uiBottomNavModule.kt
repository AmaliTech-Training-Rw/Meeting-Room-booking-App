package com.amalitech.bottom_navigation.di

import com.amalitech.bottom_navigation.BottomNavigationViewModel
import org.koin.dsl.module

val uiBottomNavModule = module {
    single {
        BottomNavigationViewModel(get())
    }
}
