package com.sohel.mansuri.employeesearch

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AutoCompleteTextView
import com.sohel.mansuri.employeesearch.adapters.EmployeeAdapter
import com.sohel.mansuri.employeesearch.screens.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test


/**
 * Created by sohelmansuri on 8/21/19.
 *
 * Espresso UI test, which will execute on an Android device.
 *
 * Used for testing different behaviors when interacting with the MainActivity
 * and it's RecyclerView of employees.
 *
 */
class MainActivityUITests {
    @Rule @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
            MainActivity::class.java, true, true
    )

    @Test
    fun testRecyclerViewVisible() {
        onView(withId(R.id.main_activity_recycler_view))
                .inRoot(RootMatchers.withDecorView(
                        `is`(activityRule.activity.window.decorView)
                ))
                .check(matches(isDisplayed()))
    }

    @Test
    fun testMenuSearchIconVisible() {
        onView(withId(R.id.action_search))
                .check(matches(isDisplayed()))
    }

    @Test
    fun testMenuSearchIconClick() {
        onView(withId(R.id.action_search))
                .perform(click())
    }

    @Test
    fun testMenuSearchFilter() {
        onView(withId(R.id.action_search))
                .perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
                .check(matches(ViewMatchers.withHint(activityRule.activity.resources.getString(R.string.search_for_employees_by_location))))
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
                .perform(typeText("Chicago"))
        onView(withId(R.id.main_activity_recycler_view))
                .check(matches(withViewAtPosition(2, withText("Doug"), R.id.employee_item_name)))
    }

    @Test
    fun testMenuSearchFilterNoMatch() {
        onView(withId(R.id.action_search))
                .perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
                .check(matches(ViewMatchers.withHint(activityRule.activity.resources.getString(R.string.search_for_employees_by_location))))
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
                .perform(typeText("Miami"))
        onView(withId(R.id.main_activity_recycler_view))
                .check(matches(not(withViewAtPosition(0, withText("Karen"), R.id.employee_item_name))))
    }

    @Test
    fun testNoFilterScrollToBottom() {
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.main_activity_recycler_view)
        val lastAdapterItem = recyclerView.adapter.itemCount - 1
        val lastEmployeeName = (recyclerView.adapter as EmployeeAdapter).getItem(lastAdapterItem)
        onView(withId(R.id.main_activity_recycler_view))
                .perform(RecyclerViewActions.scrollToPosition<EmployeeAdapter.EmployeeHolder>(lastAdapterItem))
        onView(withId(R.id.main_activity_recycler_view))
                .check(matches(withViewAtPosition(lastAdapterItem, withText(lastEmployeeName.name), R.id.employee_item_name)))
    }

    private fun withViewAtPosition(position: Int, itemMatcher: Matcher<View>, targetViewId: Int): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView.findViewById(targetViewId))
            }
        }
    }
}