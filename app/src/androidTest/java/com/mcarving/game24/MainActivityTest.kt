package com.mcarving.game24

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import androidx.test.core.app.ActivityScenario

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.runner.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun whenStarted_showsPlayerNameFields(){
        onView(withId())

    }

    @Test
    fun whenInputNames_showsPlayerNames(){

    }
}