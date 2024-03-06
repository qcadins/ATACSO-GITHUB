import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import java.sql.Connection as Connection

'connection acso'
Connection connAcso = CustomKeywords.'connection.ConnectDB.connectDBACSO'()

'inisiasi flag failed = 0'
GlobalVariable.FlagFailed = 0

'call test case untuk mendapatkan api key'
WebUI.callTestCase(findTestCase('Login By Role'), [('excelPath') : excelPath, ('sheet') : sheet], FailureHandling.CONTINUE_ON_FAILURE)

'HIT API'
respon = WS.sendRequest(findTestObject('Postman/Generate New API Key', [('requestDateTime') : findTestData(excelPath).getValue(
                GlobalVariable.NumofColm, rowExcel('requestDateTime')), ('rowVersion') : '', ('tenantCode') : findTestData(
                excelPath).getValue(GlobalVariable.NumofColm, rowExcel('tenantCode'))]))

'ambil lama waktu yang diperlukan hingga request menerima balikan'
elapsedTime = ((respon.elapsedTime / 1000) + ' second')

'ambil body dari hasil respons'
responseBody = respon.responseBodyContent

'panggil keyword untuk proses beautify dari respon json yang didapat'
CustomKeywords.'customizekeyword.BeautifyJson.process'(responseBody, sheet, rowExcel('Respons') - 1, findTestData(excelPath).getValue(
        GlobalVariable.NumofColm, rowExcel('Scenario')))

'write to excel response elapsed time'
CustomKeywords.'customizekeyword.WriteExcel.writeToExcel'(GlobalVariable.DataFilePath, sheet, rowExcel('Process Time') - 
    1, GlobalVariable.NumofColm - 1, elapsedTime.toString())

'Jika status HIT API Login 200 OK'
if (WS.verifyResponseStatusCode(respon, 200, FailureHandling.OPTIONAL) == true) {
    'get API Key'
    apiKey = WS.getElementPropertyValue(respon, 'ApiKey', FailureHandling.OPTIONAL)

    'Jika API Key tidak kosong'
    if (apiKey.toString() != 'null') {
		'api key response akan menjadi AdInsKey untuk supporting'
		if (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('use Correct Token Tenant')) == 'Yes') {
			GlobalVariable.AdInsKey = apiKey
		} else {
			GlobalVariable.AdInsKey = findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Wrong Token Tenant'))
		}
		
        'jika setting check store db hidup'
        if (GlobalVariable.checkStoreDB == 'Yes') {
            'get current date'
            String currentDate = new Date().format('yyyy-MM-dd')

            'ambil store db untuk generate api key'
            ArrayList result = CustomKeywords.'connection.GenerateAPIKey.getGenerateNewAPIKeyStoreDB'(connAcso, findTestData(
                    excelPath).getValue(GlobalVariable.NumofColm, rowExcel('tenantCode')))

            'declare arraylist arraymatch'
            ArrayList arrayMatch = []

            'declare arrayindex'
            arrayIndex = 0

            'verify description ms lov'
            arrayMatch.add(WebUI.verifyMatch('Generate API Key', result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

            'verify current date'
            arrayMatch.add(WebUI.verifyMatch(currentDate, result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

            'verify ip address. Hardcode karena tidak mendapatkan IP Address (belum ada info)'
            arrayMatch.add(WebUI.verifyMatch('127.0.0.1', result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

            'verify log detail'
            arrayMatch.add(WebUI.verifyMatch('Request Generate API Key Baru', result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

            'verify datetime create'
            arrayMatch.add(WebUI.verifyMatch(currentDate, result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

            'verify datetime upload'
            arrayMatch.add(WebUI.verifyMatch(currentDate, result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

            'jika data db tidak sesuai dengan excel'
            if (arrayMatch.contains(false)) {
                GlobalVariable.FlagFailed = 1

                'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
                CustomKeywords.'customizekeyword.WriteExcel.writeToExcelStatusReason'(sheet, GlobalVariable.NumofColm, GlobalVariable.StatusFailed, 
                    (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Reason Failed')) + ';') + GlobalVariable.ReasonFailedStoredDB)
            }
        }
        
        'jika Flag failednya 0'
        if (GlobalVariable.FlagFailed == 0) {
            'write to excel success'
            CustomKeywords.'customizekeyword.WriteExcel.writeToExcel'(GlobalVariable.DataFilePath, sheet, 0, GlobalVariable.NumofColm - 
                1, GlobalVariable.StatusSuccess)
        }
    } else {
        'get error message API'
        getErrorMessageAPI(respon)
    }
} else {
    'get error message API'
    getErrorMessageAPI(respon)
}

def getErrorMessageAPI(ResponseObject respon) {
    'mengambil status code berdasarkan response HIT API'
    message = WS.getElementPropertyValue(respon, 'Message', FailureHandling.OPTIONAL)

    'Write To Excel GlobalVariable.StatusFailed and errormessage'
    CustomKeywords.'customizekeyword.WriteExcel.writeToExcelStatusReason'(sheet, GlobalVariable.NumofColm, GlobalVariable.StatusFailed, 
        ((findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Reason Failed')).replace('-', '') + ';') + 
        ('<' + message)) + '>')

    GlobalVariable.FlagFailed = 1
}

def rowExcel(String cellValue) {
    CustomKeywords.'customizekeyword.WriteExcel.getExcelRow'(GlobalVariable.DataFilePath, sheet, cellValue)
}

