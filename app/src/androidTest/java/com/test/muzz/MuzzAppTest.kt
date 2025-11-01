package com.test.muzz

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.muzz.pageobjects.LoginPage
import com.test.muzz.pageobjects.ProfilesPage
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MuzzAppTest {

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

    /**
     * Traces to README: "1 - As a user I want to log in to the app"
     * Scenario: "The user opens the app for the first time"
     */
    @Test
    fun onAppLaunch_loginScreenIsDisplayed() {
        loginPage.assertIsDisplayed()
    }

    /**
     * Traces to README: "1 - As a user I want to log in to the app"
     * Scenario: "User login succeed"
     */
    @Test
    fun successfulLogin_showsProfilesScreen() {
        loginPage.login(BuildConfig.TEST_USER, BuildConfig.TEST_PASS)
        profilesPage.assertIsDisplayed()
    }

    /**
     * Traces to README: "1 - As a user I want to log in to the app"
     * Scenario: "User login failed" (with wrong username)
     */
    @Test
    fun failedLogin_withWrongUsername_showsError() {
        loginPage.login("wronguser", BuildConfig.TEST_PASS)
        loginPage.assertWrongUsernameErrorIsDisplayed()
    }

    /**
     * Traces to README: "1 - As a user I want to log in to the app"
     * Scenario: "User login failed" (with wrong password)
     */
    @Test
    fun failedLogin_withWrongPassword_showsError() {
        loginPage.login(BuildConfig.TEST_USER, "wrongpassword")
        loginPage.assertWrongPasswordErrorIsDisplayed()
    }

    /**
     * Traces to README: "2 - As a user I want to like dating profiles"
     * Scenario: "Profiles are loaded"
     */
    @Test
    fun onLogin_withInternet_loadsProfiles() {
        loginPage.login(BuildConfig.TEST_USER, BuildConfig.TEST_PASS)
        profilesPage.assertProfileCardIsDisplayed()
    }

    /**
     * Traces to README: "2 - As a user I want to like dating profiles"
     * Scenario: "liking profiles"
     */
    @Test
    fun onLikingAndPassingProfiles_showsCorrectCounts() {
        // Given the user is logged in and profiles are loaded
        loginPage.login(BuildConfig.TEST_USER, BuildConfig.TEST_PASS)
        profilesPage.assertProfileCardIsDisplayed()

        // When the user likes 3 profiles and passes 2
        profilesPage.clickLike()
        profilesPage.clickPass()
        profilesPage.clickLike()
        profilesPage.clickLike()
        profilesPage.clickPass()

        // Then the finished screen is displayed with the correct counts
        profilesPage.assertFinishedScreenIsDisplayed(expectedLikes = 3, expectedPasses = 2)
    }

    /**
     * Traces to README requirement: "Tests should pass even if device locale is changed"
     * This test documents a known bug where a string is hardcoded, preventing localization.
     */
    @Test
    @Ignore("Known Bug: JIRA-123 - Hardcoded tagline string prevents localization")
    fun localization_hardcodedTagline_isNotTranslated() {
        // This test is expected to fail until the hardcoded tagline is moved to strings.xml
        // It checks that the English text is NOT displayed when locale is not English.
        // To test, set emulator locale to French.
        composeTestRule.onAllNodesWithText("Where Muslims Marry").assertCountEquals(0)
    }
}
