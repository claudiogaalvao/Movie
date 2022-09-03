package com.claudiogalvaodev.moviemanager.data

import android.content.Context
import com.claudiogalvaodev.moviemanager.di.appModules
import com.claudiogalvaodev.moviemanager.di.daoModule
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class BaseTest : KoinTest {

    @get:Rule
    val koinRule = KoinTestRule.create {
        modules(modules = appModules)
        androidContext(Mockito.mock(Context::class.java))
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { klass ->
        Mockito.mock(klass.java)
    }

}