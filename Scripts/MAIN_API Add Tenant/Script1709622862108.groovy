import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import java.sql.Connection as Connection

Connection connAcso = CustomKeywords.'connection.ConnectDB.connectDBACSO'()

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'customizekeyword.WriteExcel.getExcelPath'('\\Excel\\2. ACSO.xlsx')

int countColmExcel = findTestData(excelPath).columnNumbers

semicolon = ';'

for (GlobalVariable.NumofColm = 2; GlobalVariable.NumofColm <= countColmExcel; (GlobalVariable.NumofColm)++) {
    if (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Status')).length() == 0) {
        break
    } else if (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Status')).equalsIgnoreCase('Unexecuted')) {
        'inisiasi flag failed = 0'
        GlobalVariable.FlagFailed = 0

        'call test case untuk mendapatkan api key'
        WebUI.callTestCase(findTestCase('Login By Role'), [('excelPath') : excelPath, ('sheet') : sheet], FailureHandling.CONTINUE_ON_FAILURE)

        'HIT API'
        respon = WS.sendRequest(findTestObject('Postman/Add Tenant', [('requestDateTime') : findTestData(excelPath).getValue(
                        GlobalVariable.NumofColm, rowExcel('requestDateTime')), ('rowVersion') : '', ('tenantCode') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('tenantCode')), ('tenantName') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('tenantName'))]))

        'ambil lama waktu yang diperlukan hingga request menerima balikan'
        elapsedTime = ((respon.elapsedTime / 1000) + ' second')

        'ambil body dari hasil respons'
        responseBody = respon.responseBodyContent

        'panggil keyword untuk proses beautify dari respon json yang didapat'
        CustomKeywords.'customizekeyword.BeautifyJson.process'(responseBody, sheet, rowExcel('Respons') - 1, findTestData(
                excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Scenario')))

        'write to excel response elapsed time'
        CustomKeywords.'customizekeyword.WriteExcel.writeToExcel'(GlobalVariable.DataFilePath, sheet, rowExcel('Process Time') - 
            1, GlobalVariable.NumofColm - 1, elapsedTime.toString())

        'Jika status HIT API Login 200 OK'
        if (WS.verifyResponseStatusCode(respon, 200, FailureHandling.OPTIONAL) == true) {
            'get API Key'
            apiKey = WS.getElementPropertyValue(respon, 'ApiKey', FailureHandling.OPTIONAL)

            'Jika API Key tidak kosong'
            if (apiKey.toString() != 'null') {
                'jika setting check store db hidup'
                if (GlobalVariable.checkStoreDB == 'Yes') {
                    'get current date'
                    String currentDate = new Date().format('yyyy-MM-dd')

                    'ambil store db untuk add tenant'
                    ArrayList result = CustomKeywords.'connection.AddTenant.getAddTenantStoreDB'(connAcso, findTestData(
                            excelPath).getValue(GlobalVariable.NumofColm, rowExcel('tenantCode')))

                    'declare arraylist arraymatch'
                    ArrayList arrayMatch = []

                    'declare arrayindex'
                    arrayIndex = 0

                    'verify tenant code'
                    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel(
                                    'tenantCode')), result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

                    'verify tenant name'
                    arrayMatch.add(WebUI.verifyMatch(findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel(
                                    'tenantName')), result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))
					
                    'verify mengenai datetime terhadap current date'
                    arrayMatch.add(WebUI.verifyMatch(currentDate, result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

                    'jika data db tidak sesuai dengan excel'
                    if (arrayMatch.contains(false)) {
                        GlobalVariable.FlagFailed = 1

                        'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
                        CustomKeywords.'customizekeyword.WriteExcel.writeToExcelStatusReason'(sheet, GlobalVariable.NumofColm, 
                            GlobalVariable.StatusFailed, (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel(
                                    'Reason Failed')) + ';') + GlobalVariable.ReasonFailedStoredDB)
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
    }
}

def getErrorMessageAPI(ResponseObject respon) {
    'mengambil message berdasarkan response HIT API'
    message = WS.getElementPropertyValue(respon, 'Message', FailureHandling.OPTIONAL)

    'Write To Excel GlobalVariable.StatusFailed and errormessage'
    CustomKeywords.'customizekeyword.WriteExcel.writeToExcelStatusReason'(sheet, GlobalVariable.NumofColm, GlobalVariable.StatusFailed, 
        ((findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Reason Failed')).replace('-', '') + ';') + ('<' + message)) + '>')

    GlobalVariable.FlagFailed = 1
}

def rowExcel(String cellValue) {
    CustomKeywords.'customizekeyword.WriteExcel.getExcelRow'(GlobalVariable.DataFilePath, sheet, cellValue)
}

