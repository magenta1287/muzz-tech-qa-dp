package com.test.muzz.tests

import androidx.compose.ui.test.assertIsDisplayed
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
class LoginTests {

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
    // Feature 1: As a user I want to log in to the app
    // =====================================================================

    /**
     * Traces to README Req_1.1: Feature 1, Scenario 1
     * "the user is not signed in"
     */
    @Test
    fun onAppLaunch_loginScreenIsDisplayed() {
        // Given the user opens the app for the first time
        // When the user is not signed in
        // Then the login screen is displayed
        loginPage.assertIsDisplayed()
    }

    /**
     * Traces to README Req_1.2: Feature 1, Scenario 2
     * "wrong user name and/or password"
     */
    @Test
    fun failedLogin_withWrongUsername_showsError() {
        // Given the user provides the wrong username
        // When the user taps the login button
        loginPage.login("wronguser", BuildConfig.TEST_PASS)
        // Then an error is displayed
        loginPage.assertWrongUsernameErrorIsDisplayed()
    }

    /**
     * Traces to README Req_1.2: Feature 1, Scenario 2
     * "wrong user name and/or password"
     */
    @Test
    fun failedLogin_withWrongPassword_showsError() {
        // Given the user provides the wrong password
        // When the user taps the login button
        loginPage.login(BuildConfig.TEST_USER, "wrongpassword")
        // Then an error is displayed
        loginPage.assertWrongPasswordErrorIsDisplayed()
    }

    /**
     * Traces to README Req_1.3: Feature 1, Scenario 3
     * "correct credentials"
     */
    @Test
    fun successfulLogin_showsProfilesScreen() {
        // Given the user provides correct credentials
        // When the user taps the login button
        loginPage.login(BuildConfig.TEST_USER, BuildConfig.TEST_PASS)
        // Then the profiles screen is displayed
        profilesPage.assertIsDisplayed()
    }
}
