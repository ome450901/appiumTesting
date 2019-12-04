package evolutionwelness.android

import evolutionwelness.AppiumTest
import io.appium.java_client.MobileBy
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.AndroidElement
import io.appium.java_client.remote.MobileCapabilityType
import org.openqa.selenium.By
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.util.concurrent.TimeUnit

open class AndroidTestBase : AppiumTest() {

    override fun capabilities(): DesiredCapabilities {
        return super.capabilities().apply {
            val appDir = File(APK_DIR)
            val app = File(appDir.canonicalPath, APK_FF_DEV)
            setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME)
            setCapability(MobileCapabilityType.APP, app.absolutePath)
        }
    }

    fun waitFor(id: String) {
        WebDriverWait(driver, DEFAULT_TIMEOUT.toLong()).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)))
    }

    fun waitFor(id: String, timeoutSecs: Long) {
        WebDriverWait(driver, timeoutSecs).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)))
    }

    fun implicitWait(secs: Long) {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    fun byText(text: String): By? {
        val command = "new UiSelector().text(\"$text\")"
        return MobileBy.AndroidUIAutomator(command);
    }

    fun byContentDescription(text: String): By? {
        val command = "new UiSelector().description(\"$text\")"
        return MobileBy.AndroidUIAutomator(command);
    }

    companion object {
        private const val APK_DIR = "apks"
        private const val APK_FF_DEV = "app-fitnessfirst-dev.apk"
        private const val DEVICE_NAME = "Android Emulator"
        private const val DEFAULT_TIMEOUT = 20

        const val ACTIVITY = "com.evolutionwellness.app.controller.activity"

        lateinit var driver: AndroidDriver<AndroidElement>
    }
}