package evolutionwelness.android

import evolutionwelness.AppiumTest
import io.appium.java_client.MobileBy
import io.appium.java_client.android.Activity
import io.appium.java_client.android.AndroidDriver
import org.junit.After
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AndroidSplash : AppiumTest() {

    @Before
    fun setUp() {
        driver = AndroidDriver(serviceUrl(), capabilities())
//        driver.startActivity("SplashActivity")
    }

    @After
    fun after() {
        driver.removeApp(PACKAGE)
    }

    @Test
    fun `Given an account, when do the login, then login successful`() {
        // given
        val email = "y.weiyi@groupeonepoint.com"
        val password = "Jj450902"

        // when
        login(email, password)

        // then
        waitFor("com.evolutionwellness.app.fitnessfirst.debug:id/marketplace")
        assertEquals("$ACTIVITY.HomeActivity", driver.currentActivity())
    }

    @Test
    fun `Given user login, when go to the Daily Pass checkout screen, then navigate correctly`() {
        // given
//        login()

        // when
        goToDailyPassCheckout()

        // then
        waitFor("com.evolutionwellness.app.fitnessfirst.debug:id/bookButton")
        assertEquals("$ACTIVITY.PaymentActivity", driver.currentActivity())
    }

    private fun login(email: String = "y.weiyi@groupeonepoint.com", password: String = "Jj450902") {
        // Click Login on Onboarding screen
        val btnLoginOnOnboardingScreen = "com.evolutionwellness.app.fitnessfirst.debug:id/loginButton"
        waitFor(btnLoginOnOnboardingScreen)
        driver.findElementById(btnLoginOnOnboardingScreen).click()

        // Click Continue with email
        val btnContinueWithEmail = "com.evolutionwellness.app.fitnessfirst.debug:id/emailButton"
        waitFor(btnContinueWithEmail)
        driver.findElementById(btnContinueWithEmail).click()

        // Enter the email and password and then click Login on Login screen
        val btnLoginOnLoginScreen = "com.evolutionwellness.app.fitnessfirst.debug:id/loginButton"
        val emailInput = "com.evolutionwellness.app.fitnessfirst.debug:id/mailInput"
        val passwordInput = "com.evolutionwellness.app.fitnessfirst.debug:id/passwordInput"
        waitFor(btnLoginOnLoginScreen)
        driver.findElementById(emailInput).sendKeys(email)
        driver.findElementById(passwordInput).sendKeys(password)
        driver.findElementById(btnLoginOnLoginScreen).click()
    }


    private fun goToDailyPassCheckout() {
        // Click the Daily Pass tile
        waitFor("com.evolutionwellness.app.fitnessfirst.debug:id/marketplace")
        driver.findElement(byText("DAILY PASS")).click()

        // Click the first club
        val clubListId = "com.evolutionwellness.app.fitnessfirst.debug:id/clubListRecyclerView"
        val clubList = driver.findElementById(clubListId)
        waitFor(clubListId)

        val clubNameListId = "com.evolutionwellness.app.fitnessfirst.debug:id/clubName"
        val clubNameList = clubList.findElements<WebElement>(By.id(clubNameListId))
        clubNameList.first().click()

        // Click the Daily Pass ticket
        driver.findElement(byText("DAILY PASS")).click()

        // Choose a date on the date picker
        pickDate()

        // Click the continue button on date choose dialog
        val btnContinueId = "com.evolutionwellness.app.fitnessfirst.debug:id/continueButton"
        waitFor(btnContinueId)
        driver.findElementById(btnContinueId).click()

        // Click the continue button on Billing Address screen
        waitFor("com.evolutionwellness.app.fitnessfirst.debug:id/recyclerViewBillingAddress")
        driver.findElementById(btnContinueId).click()
    }

    private fun pickDate() {
        val btnNextMonth = driver.findElementById("android:id/next")

        val date = LocalDate.parse(driver.deviceTime, DateTimeFormatter.ISO_DATE_TIME)
        val dateAfter2Days = date.plusDays(10)
        if (dateAfter2Days.month != date.month) {
            btnNextMonth.click()
        }
        driver.findElement(byContentDescription(dateAfter2Days.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")))).click()
    }

    private fun waitFor(id: String) {
        WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)))
    }

    private fun waitFor(id: String, timeoutSecs: Long) {
        WebDriverWait(driver, timeoutSecs).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)))
    }

    fun byText(text: String): By? {
        val command = "new UiSelector().text(\"$text\")"
        return MobileBy.AndroidUIAutomator(command);
    }

    fun byContentDescription(text: String): By? {
        val command = "new UiSelector().description(\"$text\")"
        return MobileBy.AndroidUIAutomator(command);
    }


    fun AndroidDriver<WebElement>.startActivity(activity: String) {
        startActivity(Activity(PACKAGE, "$ACTIVITY.$activity"))
    }

    companion object {
        private val ACTIVITY = "com.evolutionwellness.app.controller.activity"
        private lateinit var driver: AndroidDriver<WebElement>

        @AfterClass
        @JvmStatic
        fun tearDown() {
            driver.quit()
        }
    }
}

