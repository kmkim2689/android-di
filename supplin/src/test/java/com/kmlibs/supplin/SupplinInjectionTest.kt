package com.kmlibs.supplin

import android.content.Context
import com.google.common.truth.Truth.assertThat
import com.kmlibs.supplin.fixtures.Test1
import com.kmlibs.supplin.fixtures.Test2
import com.kmlibs.supplin.fixtures.Test3
import com.kmlibs.supplin.fixtures.Test4
import com.kmlibs.supplin.fixtures.Test5
import com.kmlibs.supplin.fixtures.android.module.FakeConcreteModule
import com.kmlibs.supplin.fixtures.android.module.FakeDataSourceModule
import com.kmlibs.supplin.fixtures.android.module.FakeRepositoryModule
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class SupplinInjectionTest {
    private val context = mockk<Context>(relaxed = true)

    @Before
    fun setUp() {
        Injector.init {
            context(context)
            module(FakeConcreteModule::class, FakeRepositoryModule::class, FakeDataSourceModule::class)
        }
    }

    @Test
    fun `supplin injection should provide the correct instance`() {
        // given & when
        val test = Test1()

        // then
        assertThat(test.root).isNotNull()
    }

    @Test(expected = IllegalStateException::class)
    fun `supplin injection cannot provide instances in modules`() {
        // given & when
        val test = Test2()

        // then
        test.root
    }

    @Test(expected = IllegalStateException::class)
    fun `supplin injection cannot provide parameters annotated with @Supply in constructor`() {
        // given & when
        val test = Test3()

        // then
        test.root
    }

    @Test
    fun `supplin injection can provide fields annotated with @Supply in object body`() {
        // given & when
        val test = Test4()

        // then
        assertThat(test.root).isNotNull()
    }

    @Test(expected = IllegalStateException::class)
    fun `supplin injection cannot provide fields not annotated with @Supply in constructor`() {
        // given & when
        val test = Test5()

        // then
        test.root
    }
}
