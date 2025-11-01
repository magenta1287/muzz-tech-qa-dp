package com.test.muzz.pageobjects

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick

class ProfilesPage(private val composeTestRule: ComposeTestRule, private val activity: ComponentActivity) {

    // Define UI elements
    private val title by lazy { composeTestRule.onNodeWithText("Profiles") }
    private val profileCard by lazy { composeTestRule.onNodeWithTag("profile_card") }
    private val likeButton by lazy { composeTestRule.onNodeWithContentDescription("Like Profile") }
    private val passButton by lazy { composeTestRule.onNodeWithContentDescription("Pass Profile") }
    private val finishedTitle by lazy { composeTestRule.onNodeWithText("You're all caught up!") }

    // Actions
    fun clickLike() {
        likeButton.performClick()
    }

    fun clickPass() {
        passButton.performClick()
    }

    // Assertions
    fun assertIsDisplayed() {
        composeTestRule.waitForIdle()
        title.assertIsDisplayed()
    }

    fun assertProfileCardIsDisplayed(timeoutMillis: Long = 5000) {
        composeTestRule.waitUntil(timeoutMillis) {
            composeTestRule.onAllNodesWithTag("profile_card").fetchSemanticsNodes().isNotEmpty()
        }
        profileCard.assertIsDisplayed()
    }

    fun assertFinishedScreenIsDisplayed(expectedLikes: Int, expectedPasses: Int) {
        composeTestRule.waitForIdle()
        finishedTitle.assertIsDisplayed()
        composeTestRule.onNodeWithText("Liked: $expectedLikes • Passed: $expectedPasses").assertIsDisplayed()
    }
}
