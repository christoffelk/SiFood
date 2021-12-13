package com.capstone.sifood

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.capstone.sifood.other.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }
    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }
    @Test
    fun loadHome()
    {
        onView(withId(R.id.rv_populer)).check(matches(isDisplayed()))
        onView(withId(R.id.view_pager)).check(matches(isDisplayed()))
    }
    
    @Test
    fun loadFavorite()
    {
        onView(withId(R.id.navigation_notifications)).perform(ViewActions.click())
        onView(withId(R.id.rv_favorite)).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailFavorite()
    {
        onView(withId(R.id.navigation_notifications)).perform(ViewActions.click())
        onView(withId(R.id.rv_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            1,
            ViewActions.click()
        ))
        checkDetail()
    }
    @Test
    fun loadDetailHome()
    {
        onView(withId(R.id.rv_populer)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_populer)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            1,
            ViewActions.click()
        ))
        checkDetail()
    }
    @Test
    fun loadDetailArticle()
    {
        onView(withId(R.id.navigation_dashboard)).perform(ViewActions.click())
        onView(withId(R.id.rv_article)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_article)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            1,
            ViewActions.click()
        ))
        onView(withId(R.id.detail_article)).check(matches(isDisplayed()))
    }
    @Test
    fun loadArticle()
    {
        onView(withId(R.id.navigation_dashboard)).perform(ViewActions.click())
        onView(withId(R.id.rv_article)).check(matches(isDisplayed()))
    }

    private fun checkDetail()
    {
        onView(withId(R.id.picture_profile)).check(matches(isDisplayed()))
        onView(withId(R.id.foodName)).check(matches(isDisplayed()))
        onView(withId(R.id.from)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.picture_background)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_maps)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_detail_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_detail_back)).check(matches(isDisplayed()))
    }
}