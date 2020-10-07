package com.apps.albertmartorell.meteomarto.ui.city

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.apps.albertmartorell.meteomarto.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CheckToolbarSubtitlesByTestRecorder {

    @Rule
    @JvmField
    // Aquesta rule permet executar l'Activity en el test, i fer accions sobre ella. El tercer paràmetre és important, amb false evita que l'activity es llanci automàticament, i això fa que la inicialització del MockWebserver sigui més inestable.
    // l'haurem de llançar manualment als tests.
    var mActivityTestRule = ActivityTestRule(Landing::class.java, false, false)

    @Rule
    // Aquesta regla gestiona els permisos, evitant que surtin els diàlegs els quals bloquejarien el test.
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_COARSE_LOCATION"
        )

    @Test
    fun checkToolbarSubtitles() {
        val textView = onView(
            allOf(
                withText("Meteo by Marto"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withId(R.id.lyt_act_landing),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Meteo by Marto")))

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.menu_city_weather_forecast), withText("Previsión"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val textView2 = onView(
            allOf(
                withText("El tiempo para los siguientes días"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withId(R.id.lyt_act_landing),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("El tiempo para los siguientes días")))

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navega cap amunt"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            withId(R.id.lyt_act_landing),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
