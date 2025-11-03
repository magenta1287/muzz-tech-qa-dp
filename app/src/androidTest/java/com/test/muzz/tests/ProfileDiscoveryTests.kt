package com.test.muzz.tests

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.muzz.BuildConfig
import com.test.muzz.MainActivity
import com.test.muzz.pageobjects.LoginPage
import com.test.muzz.pageobjects.ProfilesPage
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ProfileDiscoveryTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var loginPage: LoginPage
    private lateinit var profilesPage: ProfilesPage

    @Before
    fun setup() {
        hiltRule.inject()
        loginPage = LoginPage(composeTestRule, composeTestRule.activity)
        profilesPage = ProfilesPage(composeTestRule, composeTestRule.activity)
    }

    // =====================================================================
    // Feature 2: As a user I want to like dating profiles
    // =====================================================================

    /**
     * Traces to README Req_2.1: Feature 2, Scenario 1
     * "Profiles are loaded"
     */
    @Test
    fun onLogin_withInternet_loadsProfiles() {
        // When the user logs in
        loginPage.login(BuildConfig.TEST_USER, BuildConfig.TEST_PASS)

        // Then the profile card is displayed (the page object handles waiting for the loading indicator to disappear)
        profilesPage.assertProfileCardIsDisplayed()
    }

    /**
     * Traces to README Req_2.3: Feature 2, Scenario 3
     * "liking profiles" (Mixed Likes and Passes)
     */
    @Test
    fun onLikingAndPassingProfiles_showsCorrectCounts() {
        // Given the user is logged in and profiles are loaded
        loginPage.login(BuildConfig.TEST_USER, BuildConfig.TEST_PASS)
        profilesPage.assertProfileCardIsDisplayed()

        // When the user likes 3 profiles and passes 2
        profilesPage.clickLike() // 1
        profilesPage.clickPass() // 2
        profilesPage.clickLike() // 3
        profilesPage.clickLike() // 4
        profilesPage.clickPass() // 5

        // Then the finished screen is displayed with the correct counts
        profilesPage.assertFinishedScreenIsDisplayed(expectedLikes = 3, expectedPasses = 2)
    }

    /**
     * Traces to README Req_2.3: Feature 2, Scenario 3
     * "liking profiles" (All Likes)
     */
    @Test
    fun onLikingOnly_showsCorrectCounts() {
        // Given the user is logged in and profiles are loaded
        loginPage.login(BuildConfig.TEST_USER, BuildConfig.TEST_PASS)
        profilesPage.assertProfileCardIsDisplayed()

        // When the user likes all 5 profiles
        repeat(5) { profilesPage.clickLike() }

        // Then the finished screen is displayed with the correct counts
        profilesPage.assertFinishedScreenIsDisplayed(expectedLikes = 5, expectedPasses = 0)
    }

    /**
     * Traces to README Req_2.3: Feature 2, Scenario 3
     * "liking profiles" (All Passes)
     */
    @Test
    fun onPassingOnly_showsCorrectCounts() {
        // Given the user is logged in and profiles are loaded
        loginPage.login(BuildConfig.TEST_USER, BuildConfig.TEST_PASS)
        profilesPage.assertProfileCardIsDisplayed()

        // When the user passes on all 5 profiles
        repeat(5) { profilesPage.clickPass() }

        // Then the finished screen is displayed with the correct counts
        profilesPage.assertFinishedScreenIsDisplayed(expectedLikes = 0, expectedPasses = 5)
    }
}
