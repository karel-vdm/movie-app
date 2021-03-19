package com.karel.movieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.karel.movieapp.domain.usecase.UseCaseGetMovieById
import com.karel.movieapp.presentation.detail.MovieDetailViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val sut = MovieDetailViewModel(
        UseCaseGetMovieById(
            repository = MockMovieRepositoryImpl()
        )
    )

    @ExperimentalCoroutinesApi
    @Test
    fun `given loading state, when get movie called, then update loading state`() =
        runBlockingTest {
            //given
            sut.setLoading(true)

            //when
            sut.getMovieById("1")

            //then
            assertEquals(false, sut.loading.getOrAwaitValue())
        }
}

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
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

    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("value was not set")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

