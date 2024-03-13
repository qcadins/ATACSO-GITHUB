import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import internal.GlobalVariable as GlobalVariable

'setting menggunakan base url yang benar atau salah'
CustomKeywords.'connection.DataVerif.settingBaseUrl'(excelPath, GlobalVariable.NumofColm, rowExcel('Use Correct Base Url'))

'HIT API Login untuk get token'
responLogin = WS.sendRequest(findTestObject('Postman/Login By Role', [('requestDateTime') : findTestData(excelPath).getValue(
                GlobalVariable.NumofColm, rowExcel('requestDateTime')), ('rowVersion') : '', ('loginId') : findTestData(
                excelPath).getValue(GlobalVariable.NumofColm, rowExcel('loginId Authentication')), ('password') : findTestData(
                excelPath).getValue(GlobalVariable.NumofColm, rowExcel('password Authentication')), ('roleCode') : findTestData(
                excelPath).getValue(GlobalVariable.NumofColm, rowExcel('roleCode Authentication')), ('tenantCode') : findTestData(
                excelPath).getValue(GlobalVariable.NumofColm, rowExcel('tenantCode Authentication'))]))

'Jika status HIT API Login 200 OK'
if (WS.verifyResponseStatusCode(responLogin, 200, FailureHandling.OPTIONAL) == true) {
    'adins key'
    GlobalVariable.TrueAdInsKey = WS.getElementPropertyValue(responLogin, 'token')
} else {
    getErrorMessageAPI(responLogin)
}

def getErrorMessageAPI(ResponseObject respon) {
    'mengambil status code berdasarkan response HIT API'
    message = WS.getElementPropertyValue(respon, 'Message', FailureHandling.OPTIONAL)

    'Write To Excel GlobalVariable.StatusFailed and errormessage'
    CustomKeywords.'customizekeyword.WriteExcel.writeToExcelStatusReason'(sheet, GlobalVariable.NumofColm, GlobalVariable.StatusFailed, 
        ((findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Reason Failed')) + ';') + ('<' + message)) + 
        '>')

    GlobalVariable.FlagFailed = 1
}

def rowExcel(String cellValue) {
    CustomKeywords.'customizekeyword.WriteExcel.getExcelRow'(GlobalVariable.DataFilePath, sheet, cellValue)
}

