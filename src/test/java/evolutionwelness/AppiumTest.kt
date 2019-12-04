package evolutionwelness

import io.appium.java_client.service.local.AppiumDriverLocalService
import org.junit.AfterClass
import org.junit.BeforeClass
import org.openqa.selenium.remote.DesiredCapabilities
import java.net.URL

open class AppiumTest {

    open fun capabilities(): DesiredCapabilities = DesiredCapabilities().apply {
        setCapability("appPackage", PACKAGE)
        setCapability("autoGrantPermissions", true)
    }

    companion object {
        const val PACKAGE = " com.evolutionwellness.app.fitnessfirst.debug"

        private lateinit var service: AppiumDriverLocalService

        @BeforeClass
        @JvmStatic
        fun globalSetup() {
            service = AppiumDriverLocalService.buildDefaultService()
            service.start()
        }

        @AfterClass
        @JvmStatic
        fun globalTearDown() {
            service.stop()
        }

        fun serviceUrl(): URL = service.url
    }
}