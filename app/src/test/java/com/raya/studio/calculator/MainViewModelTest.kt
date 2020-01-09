package com.raya.studio.calculator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {


    /**
     * When testing LiveData,
     * InstantTaskExecutorRule is needed in addition to RxImmediateSchedulerRule
     * if the class being tested has both background thread and LiveData.
     * */
    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var viewModel: MainViewModel


    @Before fun init() {
        viewModel = MainViewModel()
    }

    @Test fun testFoo(){
        viewModel.input.value = "foo"
        assertEquals(viewModel.input.value, "foo")
        assertEquals(viewModel.result.getOrAwaitValue(), "")
    }

    @Test fun testAddition(){
        viewModel.input.value = "1 + 2"
        assertEquals(viewModel.input.value, "1 + 2")
        assertEquals(viewModel.result.getOrAwaitValue(), "3")
    }

    @Test fun testSubtraction(){
        viewModel.input.value = "1 - 2"
        assertEquals(viewModel.input.value, "1 - 2")
        assertEquals(viewModel.result.getOrAwaitValue(), "-1")
    }

    @Test fun testMultiplication(){
        viewModel.input.value = "2 * 3"
        assertEquals(viewModel.input.value, "2 * 3")
        assertEquals(viewModel.result.getOrAwaitValue(), "6")
    }

    @Test fun testDivision(){
        viewModel.input.value = "2 / 5"
        assertEquals(viewModel.input.value, "2 / 5")
        assertEquals(viewModel.result.getOrAwaitValue(), "0.4")
    }

    @Test fun testExponentiation(){
        viewModel.input.value = "2 ^ 3"
        assertEquals(viewModel.input.value, "2 ^ 3")
        assertEquals(viewModel.result.getOrAwaitValue(), "8")
    }

    @Test fun testExponentiationDivision(){
        viewModel.input.value = "2 ^ 6 / 4"
        assertEquals(viewModel.input.value, "2 ^ 6 / 4")
        assertEquals(viewModel.result.getOrAwaitValue(), "16")
    }

    @Test fun testSampleFormula(){
        viewModel.input.value = "(2^3-1)*sin(pi/4)/ln(pi^2)"
        assertEquals(viewModel.input.value, "(2^3-1)*sin(pi/4)/ln(pi^2)")
        assertEquals(viewModel.result.getOrAwaitValue(), "2.1619718020347976")
    }

    /**
     * That's way you need to save your past result in some variable (e.g value)
     * */
    @Test fun testHalfFormula(){
        viewModel.input.value = "10 - 2 ^ 3 / "
        assertEquals(viewModel.input.value, "10 - 2 ^ 3 / ")
        assertEquals(viewModel.result.getOrAwaitValue(), "")
    }

    /**
     * Copyright 2019 Google LLC.
     * SPDX-License-Identifier: Apache-2.0
     * */
    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 100,
        timeUnit: TimeUnit = TimeUnit.MILLISECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}