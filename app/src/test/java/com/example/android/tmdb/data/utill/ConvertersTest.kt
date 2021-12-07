package com.example.android.tmdb.data.utill

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test

class ConvertersTest {
    private val list = listOf(1, 2, 3)
    private val string = "[1,2,3]"
    private val converters = Converters()

    @Test
    fun testFromList() {
        assertThat(converters.fromList(list), `is`(string))
    }

    @Test
    fun testFromList_empty_returnEmptyString() {
        assertThat(converters.fromList(emptyList()), `is`("[]"))

    }

    @Test
    fun testFromList_null_returnEmptyStringNull() {
        assertThat(converters.fromList(null), `is`("null"))
    }

    @Test
    fun testToList() {
        assertThat(converters.toList(string), `is`(list))
    }

    @Test
    fun testToList_empty_returnEmptyString() {
        assertThat(converters.toList(""), `is`(emptyList()))
    }

}