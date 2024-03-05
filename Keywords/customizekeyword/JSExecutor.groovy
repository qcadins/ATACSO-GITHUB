package customizekeyword

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.driver.DriverFactory

class JSExecutor {

	@Keyword
	jsExecutionFunction(String jsCode, String xpath, String newJS) {
		'Mengambil web element dari kotaknya'
		WebElement element = DriverFactory.webDriver.findElement(By.xpath(xpath))

		'menentukan lokasi terbaru untuk object'
		String newLocation = newJS

		String js = jsCode

		DriverFactory.webDriver.executeScript(js, element, newLocation)
	}
}
