# Muzz QA Technical Test - Solution

This repository contains the solution for the Muzz QA Technical Test. It includes automated tests for the login and profile discovery features, as well as a test_execution_summary.md with a summary of all the tests carried out and the bugs found.

## How to Build and Run the App

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/magenta1287/muzz-tech-qa-dp.git
    ```
2.  **Open in Android Studio:**
    Open the cloned project in a recent version of Android Studio.
3.  **Sync and Build:**
    Let Gradle sync and build the project.
4.  **Run the App:**
    Select the `app` run configuration and run it on an emulator or a physical device. A recent Google Pixel emulator (e.g., Pixel 9a) is recommended.

## How to Run the Automated Tests

The automated tests are located in the `app/src/androidTest/java/com/test/muzz/tests` directory.

You can run them in one of the following ways:

*   **Run all tests:**
    Right-click on the `tests` package (`app/src/androidTest/java/com/test/muzz/tests`) in the Project view and select "Run tests in 'com.test.muzz.tests'".
*   **Run a specific test class:**
    Open a test file (e.g., `LoginTests.kt`) and click the green play button next to the class name to run all tests within that file.

## Key Files & Evidence

*   **Test Execution Summary:** A complete summary of all manual and automated testing and bugs found is in  test_execution_summary.md at the root of the project.
*   **Screenshots and Videos:** All supporting screen recordings and screenshots for bug reports are located in the `attachments/` directory.

## Automation Tooling
I chose to use **Jetpack Compose testing framework** for the automated tests. The primary reason for this choice is that the application's UI is built with Jetpack Compose. Using the native testing framework provides direct access to the `ComposeTestRule` and a rich set of APIs for finding UI elements, performing actions, and making assertions, which leads to more robust, reliable, and less flaky tests.

## Design Pattern
I implemented the **Page Object Model (POM)** pattern to create a clear separation between the test logic and the UI interaction details.

*   **Page Objects (`pageobjects` directory):** These classes (`LoginPage.kt`, `ProfilesPage.kt`) encapsulate the UI elements and the actions that can be performed on a specific screen. This makes the tests more readable and easier to maintain. If the UI changes, only the relevant page object needs to be updated, not the test logic itself.
*   **Tests (`tests` directory):** The test classes (e.g., `LoginTests.kt`) use the methods from the page objects to perform actions and assert outcomes, keeping the test code clean and focused on the test scenario.

This approach makes the test suite scalable and resilient to UI changes.
