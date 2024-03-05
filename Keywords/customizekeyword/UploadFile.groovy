package customizekeyword

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.awt.Robot
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent

class UploadFile {

	@Keyword
	uploadFunction(TestObject to, String filePath) {
		WebUI.click(to)
		StringSelection ss = new StringSelection(filePath)
		Toolkit.defaultToolkit.systemClipboard.setContents(ss, null)
		Robot robot = new Robot()
		robot.keyPress(KeyEvent.VK_ENTER) //control, -
		robot.keyRelease(KeyEvent.VK_ENTER) //-, control
		robot.delay(1000) //NOTE THE DELAY (500, 1000, 1500 MIGHT WORK FOR YOU)
		robot.keyPress(KeyEvent.VK_CONTROL)
		robot.keyPress(KeyEvent.VK_V)
		robot.keyRelease(KeyEvent.VK_V)
		robot.delay(1000) //NOTE THE DELAY (500, 1000, 1500 MIGHT WORK FOR YOU)
		robot.keyRelease(KeyEvent.VK_CONTROL)
		robot.keyPress(KeyEvent.VK_ENTER)
		robot.keyRelease(KeyEvent.VK_ENTER)
	}
}
