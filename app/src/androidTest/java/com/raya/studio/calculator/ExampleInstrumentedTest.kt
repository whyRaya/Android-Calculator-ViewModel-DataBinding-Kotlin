package com.raya.studio.calculator


import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAddition() {
        // Type on the input
        Espresso.onView(ViewMatchers.withId(R.id.input))
            .perform(clearText(), typeText("2 + 5"))

        // Check the result from the input
        Espresso.onView(ViewMatchers.withId(R.id.result))
            .check(ViewAssertions.matches(ViewMatchers.withText("7")))
    }

    @Test
    fun testHalfFormula() {
        // Type on the input
        Espresso.onView(ViewMatchers.withId(R.id.input))
            .perform(clearText(), typeText("2 * 8 / "))

        // Check the result from the input
        Espresso.onView(ViewMatchers.withId(R.id.result))
            .check(ViewAssertions.matches(ViewMatchers.withText("16")))
    }
}