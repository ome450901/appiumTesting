package evolutionwelness

import io.appium.java_client.remote.MobileCapabilityType
import io.appium.java_client.service.local.AppiumDriverLocalService
import io.appium.java_client.service.local.AppiumServiceBuilder
import org.junit.AfterClass
import org.junit.BeforeClass
import org.openqa.selenium.remote.DesiredCapabilities
import java.io.File
import java.net.URL

open class AppiumTest {
    companion object {
        private lateinit var service: AppiumDriverLocalService

        const val PACKAGE = " com.evolutionwellness.app.fitnessfirst.debug"
        private const val APK_FF_DEV = "app-fitnessfirst-dev.apk"
        private const val APK_DIR = "apks"
        private const val DEVICE = "Android Emulator"

        @BeforeClass
        @JvmStatic
        fun globalSetup() {
            val appiumServiceBuilder = AppiumServiceBuilder()
            appiumServiceBuilder.withIPAddress("127.0.0.1")
            service = AppiumDriverLocalService.buildService(appiumServiceBuilder)
            service.start()
        }

        @AfterClass
        @JvmStatic
        fun globalTearDown() {
            service.stop()
        }

        fun capabilities(): DesiredCapabilities = DesiredCapabilities().apply {
            val appDir = File(APK_DIR)
            val app = File(appDir.canonicalPath, APK_FF_DEV)
            setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE)
            setCapability(MobileCapabilityType.APP, app.absolutePath)
            setCapability("appPackage", PACKAGE)
            setCapability("autoGrantPermissions", true)
        }

        fun serviceUrl(): URL = service.url
    }
}