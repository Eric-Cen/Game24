package com.mcarving.game24


import org.junit.Rule
import org.junit.Test

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun whenStarted_showsPlayerNameFields(){

    }

    @Test
    fun whenInputNames_showsPlayerNames(){

    }
}