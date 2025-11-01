package com.test.muzz.pageobjects

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.test.muzz.R

class LoginPage(private val composeTestRule: ComposeTestRule, private val activity: ComponentActivity) {

    // Define strings using resources for robustness
    private val usernameHint = activity.getString(R.string.login_user_name_hint)
    private val passwordHint = activity.getString(R.string.login_password_hint)
    private val loginButtonText = activity.getString(R.string.login_button_text)

    // Define UI elements
    private val usernameInput by lazy { composeTestRule.onNodeWithText(usernameHint) }
    private val passwordInput by lazy { composeTestRule.onNodeWithText(passwordHint) }
    private val loginButton by lazy { composeTestRule.onNodeWithText(loginButtonText) }

    // Actions
    fun enterUsername(username: String) {
        usernameInput.performTextInput(username)
    }

    fun enterPassword(password: String) {
        passwordInput.performTextInput(password)
    }

    fun clickLogin() {
        loginButton.performClick()
    }

    // High-level interaction
    fun login(username: String, password: String) {
        enterUsername(username)
        enterPassword(password)
        clickLogin()
    }

    // Assertions
    fun assertIsDisplayed() {
        usernameInput.assertIsDisplayed()
        passwordInput.assertIsDisplayed()
        loginButton.assertIsDisplayed()
    }

    fun assertWrongUsernameErrorIsDisplayed() {
        val errorMessage = activity.getString(R.string.login_wrong_user_name_error)
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    fun assertWrongPasswordErrorIsDisplayed() {
        val errorMessage = activity.getString(R.string.login_wrong_password_error)
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }
}
