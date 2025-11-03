# Test Execution Summary

This document provides an overview of the test coverage and test execution findings for the Muzz Android app for the tech test.

The manual and automated tests were run on Pixel9a, Pixel9 Pro Fold, Pixel5 and small phone emulators.

*Note: While the Gherkin-based requirements are suitable for a BDD automation framework, a simpler Page Object Model approach was chosen for speed and efficiency given the small scope of this project.*

---

## 1. Requirements Coverage Matrix

This table provides a consolidated overview of the test coverage for each requirement specified in the `README.md`.

| Req. ID | Requirement | Test ID(s) | Automated | Status | Bug ID |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Req_1.1 | First-time launch displays the login screen. | 1.1 | ✅ Yes | ✅ Passed | - |
| Req_1.2 | Failed login attempt displays an error. | 1.2.1, 1.2.2, 1.2.3 | ✅ Yes | ❌ Failed | Bug 1 |
| Req_1.3 | Successful login navigates to discover screen. | 1.3 | ✅ Yes | ✅ Passed | - |
| Req_1.4 | Returning user is taken to discover screen. | 1.4 | ❌ No (Bug Raised) | ❌ Failed | Bug 3 |
| Req_2.1 | Profiles load when internet is present. | 2.1 | ✅ Yes | ✅ Passed | - |
| Req_2.2 | No internet shows a 'Failed to load' error. | 2.2 | ❌ No (OS interaction) | ❌ Failed | Bug 6 |
| Req_2.3 | Correct like/pass count is shown after 5 swipes. | 2.3.1, 2.3.2, 2.3.3 | ✅ Yes | ✅ Passed | - |
| Req_Other | Tests should pass if device locale is changed. | | | ✅ Passed | |

---

## 2. Test Case Execution Details

| Test ID | Test Case Name | Steps | Expected | Actual | Status | Bug Ref |
|:--- |:--- |:--- |:--- |:--- |:--- |:--- |
| **1.1** | First Launch | Launch app. | Login screen. | OK. | ✅ **PASSED** | - |
| **1.2.1**| Failed Login (User) | Enter wrong user. | Error shown. | OK. | ✅ **PASSED** | - |
| **1.2.2**| Failed Login (Pass) | Enter wrong pass. | Error shown. | OK. | ✅ **PASSED** | - |
| **1.2.3**| User Enumeration | Correct user, wrong pass. | Generic error. | Specific error. | ❌ **FAILED** | Bug 1 |
| **1.3** | Successful Login | Enter correct creds. | Profiles screen. | OK. | ✅ **PASSED** | - |
| **1.4** | Session Persistence | Login, then relaunch. | Stays logged in. | Returns to login. | ❌ **FAILED** | Bug 3 |
| **2.1** | Profiles Load | Login successfully. | Profiles shown. | OK. | ✅ **PASSED** | - |
| **2.2** | Offline Mode | Disable net, login. | "Failed to load" err. | Loads fake data. | ❌ **FAILED** | Bug 6 |
| **2.3.1**| Swipe Counts (Mixed)| 5 mixed swipes. | Correct counts. | OK. | ✅ **PASSED** | - |
| **2.3.2**| Swipe Counts (Likes)| 5 likes. | Correct counts. | OK. | ✅ **PASSED** | - |
| **2.3.3**| Swipe Counts (Passes)| 5 passes. | Correct counts. | OK. | ✅ **PASSED** | - |
| **3.1** | Security: Brute-Force| 10+ wrong pass attempts. | Account locked. | Infinite attempts. | ❌ **FAILED** | Bug 2 |
| **3.2** | Security: SQLi | Enter `' OR 1=1; --'`. | Generic fail. | Failed as expected. | ✅ **PASSED** | - |
| **4.1** | A11y: Login Blocker | Use TalkBack to login. | Can enter pass. | Focus trapped. | ❌ **FAILED** | Bug 4 |
| **4.2** | A11y: Error Announce| TalkBack, trigger error. | Error announced. | Error silent. | ❌ **FAILED** | Bug 5 |
| **5.1** | Usability: Empty Login| Tap Login on blank form. | Helpful error. | Misleading error. | ❌ **FAILED**| Bug 8 |
| **5.2** | Usability: Char Valid| Enter special chars. | Input rejected. | Login rejected. | ✅ **PASSED** | - |
| **5.3** | Usability: Length Limit| Paste >256 chars. | Input truncated. | Long string ok. | ❌ **FAILED**| Bug 7 |
| **5.4** | Usability: Whitespace| User with trailing space. | Login succeeds. | Login fails. | ❌ **FAILED**| Bug 14 |
| **5.5** | Usability: Case | Enter `User` not `user`. | Login succeeds. | Login fails. | ❌ **FAILED**| Bug 15 |
| **6.1** | UI: Landscape (Profiles)| Login, rotate. | Buttons visible. | Buttons hidden. | ❌ **FAILED**| Bug 12 |
| **6.2** | UI: Landscape (Login) | Launch, rotate. | Layout adapts. | Layout cramped. | ❌ **FAILED**| Bug 13 |
| **6.3** | UI: Inconsistency | Observe hint text. | "username". | "user name". | ❌ **FAILED**| Bug 9 |
| **7.1** | i18n: Localization | Set lang to French. | All text French. | Some text English. | ❌ **FAILED**| Bug 10 |

---

## 3. Bug Reports

| Metric | Count |
| :--- |:------|
| Total Bugs Found | 15    |
| High Priority Bugs | 7     |
| Medium Priority Bugs| 2     |
| Low Priority Bugs | 6     |

*Note: Any bugs found would be discussed with the developer before raising.*

---

### Bug 1: Login page displays different error messages for an incorrect username versus an incorrect password

*   **Severity:** High
*   **Priority:** High
*   **Device:** Pixel 9a API 36
*   **Description:** The login page displays different error messages for an incorrect username versus an incorrect password. This leaks information and allows an attacker to confirm which usernames are registered on the platform.
*   **Steps to Reproduce:**
    1.  Enter a known correct username (e.g., `user`) and a deliberately incorrect password.
    2.  Tap Login.
*   **Expected Result:** A generic error message should be shown (e.g., "Invalid credentials").
*   **Actual Result:** A specific error ("Wrong password") is shown, confirming the username is valid.
*   **Attachments:** bug-1-wrong-password.png, bug-1-wrong-user-name.png

---

### Bug 2: No Brute-Force Protection on Login

*   **Severity:** High
*   **Priority:** High
*   **Device:** Pixel 9a API 36
*   **Description:** The login form does not implement any rate limiting or account lockout mechanism. This allows an attacker to perform unlimited, automated password guesses against a known username without being stopped.
*   **Steps to Reproduce:**
    1.  Enter a known correct username.
    2.  Enter an incorrect password and tap Login more than 10 times.
*   **Expected Result:** The account should be temporarily locked after a small number of failed attempts.
*   **Actual Result:** The app allows an infinite number of login attempts.
*   **Attachments:** bug-2-Infinite Login-Attempts.webm

---

### Bug 3: No Session Persistence After App Relaunch

*   **Severity:** High
*   **Priority:** High
*   **Device:** Pixel 9a API 36
*   **Description:** The app fails to save the user's session state. When a user closes and re-opens the app, they are forced to log in again, and all their in-session progress is lost. This fails a core requirement of a mobile application.
*   **Steps to Reproduce:**
    1.  Log in and interact with the app.
    2.  Close and relaunch the app.
*   **Expected Result:** The user should remain logged in and their progress should be saved.
*   **Actual Result:** The app returns to the login screen and all progress is lost.
*   **Attachments:** bug-3-No_Session_persistence.webm

---

### Bug 4: Accessibility Blocker: Unable to Enter Password with Screen Reader

*   **Severity:** High
*   **Priority:** High
*   **Device:** Pixel 9a API 36
*   **Description:** When using the TalkBack screen reader, keyboard focus does not correctly move to the password field, making it impossible for a visually impaired user to log in.
*   **Steps to Reproduce:**
    1.  Enable TalkBack.
    2.  Navigate to the username field and enter text.
    3.  Attempt to navigate to the password field.
*   **Expected Result:** The user should be able to enter text in the password field.
*   **Actual Result:** Keyboard focus becomes trapped in the username field.
*   **Attachments:** bug-4-Unable to add password with screen-reader.webm

---

### Bug 5: Accessibility: Unannounced Error Messages

*   **Severity:** High
*   **Priority:** High
*   **Device:** Pixel 9a API 36
*   **Description:** When a login error occurs, the error message is displayed visually but is not announced by the TalkBack screen reader, leaving a visually impaired user unaware of why their action failed.
*   **Steps to Reproduce:**
    1.  Enable TalkBack.
    2.  Trigger a login error (e.g., by tapping login with empty fields).
*   **Expected Result:** The TalkBack screen reader should announce the error message.
*   **Actual Result:** The error message is silent.
*   **Attachments:** bug-5-Error not annouced by with screen-reader.webm

---

### Bug 6: Profiles page displayed with no internet connection

*   **Severity:** High
*   **Priority:** High
*   **Device:** Pixel 9a API 36
*   **Description:** When there is no internet connection, the app incorrectly loads the profiles page instead of showing the specified "Failed to load" error. It does not inform the user of the network problem and may present them with stale or cached data.
*   **Steps to Reproduce:**
    1.  Disable all network connectivity on the device.
    2.  Log in to the app successfully.
*   **Expected Result:** “Failed to profiles” error message is displayed with a Retry button
*   **Actual Result:** The app successfully loads profiles, indicating it uses fake data and not a real network call.
*   **Attachments:** bug-6-Profiles page displayed with no internet.webm

---

### Bug 7: No Character Limit on Input Fields

*   **Severity:** Medium
*   **Priority:** Medium
*   **Device:** Pixel 9a API 36
*   **Description:** The username and password fields do not enforce a maximum character limit. This could expose the server to denial-of-service attacks or cause data truncation errors.
*   **Steps to Reproduce:**
    1.  Paste a string longer than 256 characters into the username field.
*   **Expected Result:** The input should be truncated to a reasonable limit.
*   **Actual Result:** The app accepts the entire long string.
*   **Attachments:** bug-7-No-char-limit-on-input-fields.png

---

### Bug 8: Misleading Error Message for Empty Login

*   **Severity:** Low
*   **Priority:** Low
*   **Device:** Pixel 9a API 36
*   **Description:** When a user taps the "Login" button with empty credentials, the app shows a misleading error message. A better experience would be to disable the button until the form is valid.
*   **Steps to Reproduce:**
    1.  Tap the "Login" button when the username and password fields are empty.
*   **Expected Result:** A helpful error like "Fields cannot be empty" should be shown.
*   **Actual Result:** A misleading error ("Wrong user name") is shown.
*   **Attachments:** bug-8-Unclear-error-messaging.png

---

### Bug 9: Inconsistent hint text on login screen

*   **Severity:** Low
*   **Priority:** Low
*   **Device:** Pixel 9a API 36
*   **Description:** The string for "username" is inconsistently presented as two words ("user name") in multiple places on the login screen. This occurs in the hint text, the floating label, and in the "Wrong user name" error message.
*   **Steps to Reproduce:**
    1.  Launch the app and observe the hint text in the username field.
    2.  Tap inside the username field and observe the floating label.
    3.  Trigger a "wrong username" error and observe the error message text.
*   **Expected Result:** In all instances, the text should be the standard, single-word "username".
*   **Actual Result:** The text is inconsistently displayed as two words: "user name".
*   **Attachments:** bug-9-inconsistent-labelling-of-username.png

---

### Bug 10: UI Text Not Localized

*   **Severity:** Medium
*   **Priority:** Medium
*   **Device:** Pixel 9a API 36
*   **Description:** Many parts of the UI have hardcoded English text, which prevents localization and creates an inconsistent user experience for users in other languages.
*   **Steps to Reproduce:**
    1.  Change the device's system language to French.
    2.  Launch the app.
*   **Expected Result:** All text in the application should be in French.
*   **Actual Result:** Several key text elements remain in English.
*   **Attachments:** bug-10-UI-text -not-localised.webm

---

### Bug 11: Technical Debt Found in System Logs

*   **Severity:** Low
*   **Priority:** Low
*   **Device:** Pixel 9a API 36
*   **Description:** During exploratory testing, the app's logs were found to contain multiple warnings and errors related to deprecated APIs and system settings.
*   **Steps to Reproduce:**
    1.  Run the app while monitoring the Logcat stream in Android Studio.
*   **Expected Result:** The logs should be clean of unexpected errors during normal operation.
*   **Actual Result:** The logs contain several technical debt warnings (`Pinning is deprecated`, etc.).
*   **Attachments:** bug-11-logcat warnings and errors.png

---

### Bug 12: Core Functionality Blocked in Landscape on Profiles Screen

*   **Severity:** High
*   **Priority:** High
*   **Device:** Pixel 9a API 36
*   **Description:** When the device is rotated to landscape on the profiles screen, the Like and Pass buttons are not visible. This is a functional blocker that completely prevents the app's core feature from being used in landscape mode.
*   **Steps to Reproduce:**
    1.  Log in to the app.
    2.  On the profiles screen, rotate the device to landscape orientation.
*   **Expected Result:** The layout should adapt, and the Like and Pass buttons should be clearly visible and tappable.
*   **Actual Result:** The profile image expands to fill the screen, completely hiding the Like and Pass buttons and blocking all user interaction.
*   **Attachments:** bug-12-profile-in-landscape.png

---

### Bug 13: Poor Landscape Layout on Login Screen

*   **Severity:** Low
*   **Priority:** Low
*   **Device:** Pixel 9a API 36
*   **Description:** When the device is rotated to landscape, the login screen layout does not adapt correctly, causing UI elements to be cramped or cut off.
*   **Steps to Reproduce:**
    1.  Launch the app.
    2.  Rotate the device to landscape orientation.
*   **Expected Result:** The login screen layout should adapt gracefully to the horizontal orientation.
*   **Actual Result:** The "Where Muslims Marry" tagline is partially cut off, and the overall layout is poorly spaced and cramped.
*   **Attachments:** bug-13-login-in-landscape.png

---

### Bug 14: Username Field Does Not Trim Whitespace

*   **Severity:** Low
*   **Priority:** Low
*   **Device:** Pixel 9a API 36
*   **Description:** The username field does not trim leading or trailing whitespace. This can cause valid login attempts to fail if the user accidentally adds a space, creating a confusing and frustrating user experience.
*   **Steps to Reproduce:**
    1.  In the username field, enter a valid username with leading/trailing whitespace.
*   **Expected Result:** The application should trim the whitespace and successfully log the user in.
*   **Actual Result:** The login fails with a "Wrong user name" error.
*   **Attachments:** bug-14-not-trimming-whitespaces.webm

---

### Bug 15: Username is Case-Sensitive

*   **Severity:** Low
*   **Priority:** Low
*   **Device:** Pixel 9a API 36
*   **Description:** The login system treats usernames as case-sensitive (e.g., `user` and `User` are considered different accounts). Standard industry practice is to make usernames case-insensitive to avoid user confusion and login failures.
*   **Steps to Reproduce:**
    1.  Enter `User` (with a capital U) in the username field.
    2.  Enter the correct password.
    3.  Tap Login.
*   **Expected Result:** The login should succeed.
*   **Actual Result:** The login fails.
*   **Attachments:** bug-15-username-case-sensitive.webm
