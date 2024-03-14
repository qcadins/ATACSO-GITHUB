package customizekeyword

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable

class CheckSaveProccess {
	
	@Keyword
	checkStatus(int count, TestObject object, int colm, String sheetname) {
		if (WebUI.verifyElementPresent(object, 3, FailureHandling.OPTIONAL)) {
			if (count == 0) {
				(new customizekeyword.WriteExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						0, colm - 1, GlobalVariable.StatusSuccess)
			} else {
				(new customizekeyword.WriteExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						0, colm - 1, GlobalVariable.StatusFailed)
				(new customizekeyword.WriteExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						1, colm - 1, GlobalVariable.ReasonFailedSystemError)
			}
		} else {
			if (count == 0) {
				(new customizekeyword.WriteExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						0, colm - 1, GlobalVariable.StatusFailed)
				(new customizekeyword.WriteExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						1, colm - 1, GlobalVariable.ReasonFailedSaveGagal)
			} else {
				(new customizekeyword.WriteExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						0, colm - 1, GlobalVariable.StatusFailed)
				(new customizekeyword.WriteExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
						1, colm - 1, GlobalVariable.ReasonFailedMandatory)
			}
		}
	}

	@Keyword
	checkAlert(int colm, String sheetname, Object object) {
		int flagFailed = 0
		if (WebUI.verifyElementPresent(object, 1, FailureHandling.OPTIONAL)) {
			String erroralert = WebUI.getText(object, FailureHandling.OPTIONAL)
			if (erroralert != null) {
				if (!erroralert.contains('Success'.toUpperCase())) {
					String failedAlertReason = WebUI.getAttribute(object, 'aria-label', FailureHandling.OPTIONAL)

					(new customizekeyword.WriteExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
							0, colm - 1, GlobalVariable.StatusFailed)
					(new customizekeyword.WriteExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
							1, colm - 1, failedAlertReason)
					flagFailed = 1
				}
			}
		}
		flagFailed
	}
}
