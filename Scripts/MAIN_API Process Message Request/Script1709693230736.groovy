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

for (GlobalVariable.NumofColm = 2; GlobalVariable.NumofColm <= countColmExcel; (GlobalVariable.NumofColm)++) {
    if (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Status')).length() == 0) {
        break
    } else if (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Status')).equalsIgnoreCase('Unexecuted')) {
        'diberikan max looping'
        maxLoopingRetry = 1

        'looping max looping'
        for (loopingRetry = 1; loopingRetry <= maxLoopingRetry; loopingRetry++) {
            'inisiasi flag failed = 0'
            GlobalVariable.FlagFailed = 0

            'ambil retry count before'
            int retryCountBefore = Integer.parseInt(CustomKeywords.'connection.ProcessMessageRequest.getRetryCount'(connAcso, 
                    findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('requestUuid'))))

            'hit api'
            ResponseObject respon = hitAPI()

            'Jika status HIT API Login 200 OK'
            if (WS.verifyResponseStatusCode(respon, 200, FailureHandling.OPTIONAL) == true) {
                'get message'
                message = WS.getElementPropertyValue(respon, 'Message', FailureHandling.OPTIONAL)

                'Jika message success'
                if (message == 'Success') {
                    'jika setting check store db hidup'
                    if (GlobalVariable.checkStoreDB == 'Yes') {
                        'pause storedb dikarenakan testing failed terus. Sudah tanya dev'

                        'get current date'
                        String currentDate = new Date().format('yyyy-MM-dd')

                        'declare arraylist arraymatch'
                        ArrayList arrayMatch = []

                        'declare arrayindex'
                        arrayIndex = 0

                        checkingStatus = CustomKeywords.'connection.ProcessMessageRequest.getRequestStatus'(connAcso, findTestData(
                                excelPath).getValue(GlobalVariable.NumofColm, rowExcel('requestUuid')))

                        flagContinueInsideFor = false

                        flagBreakInsideFor = false

                        for (int i = 1; i <= 3; i++) {
                            if (checkingStatus == '1') {
                                int retryCountAfter = Integer.parseInt(CustomKeywords.'connection.ProcessMessageRequest.getRetryCount'(
                                        connAcso, findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('requestUuid'))))

                                'verify tenant code'
                                arrayMatch.add(WebUI.verifyEqual(retryCountBefore, retryCountAfter - 1, FailureHandling.CONTINUE_ON_FAILURE))

                                if (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Retry Process Message Request ?')) == 
                                'Yes') {
                                    maxLoopingRetry = findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel(
                                            'Count of Retry Process Message Request'))

                                    flagContinueInsideFor = true

                                    break
                                } else {
                                    flagBreakInsideFor = true

                                    break
                                }
                            } else if (checkingStatus == '3') {
                                int retryCountAfter = Integer.parseInt(CustomKeywords.'connection.ProcessMessageRequest.getRetryCount'(
                                        connAcso, findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('requestUuid'))))

                                'verify tenant code'
                                arrayMatch.add(WebUI.verifyEqual(retryCountBefore, retryCountAfter - 1, FailureHandling.CONTINUE_ON_FAILURE))

                                ArrayList result = CustomKeywords.'connection.ProcessMessageRequest.getProcessMessageRequestStoreDB'(
                                    connAcso, findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('requestUuid')))

                                'verify tenant code'
                                arrayMatch.add(WebUI.verifyMatch(currentDate, result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

                                'verify tenant code'
                                arrayMatch.add(WebUI.verifyMatch(checkingStatus, result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

                                'verify tenant code'
                                arrayMatch.add(WebUI.verifyMatch(currentDate, result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

                                'verify tenant code'
                                arrayMatch.add(WebUI.verifyMatch('null', (result[arrayIndex++]).toString(), false, FailureHandling.CONTINUE_ON_FAILURE))

                                'verify tenant code'
                                arrayMatch.add(WebUI.verifyMatch(findTestData(excelPath).getValue(GlobalVariable.NumofColm, 
                                            rowExcel('receiverPhone')), result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

                                'verify tenant code'
                                arrayMatch.add(WebUI.verifyMatch(currentDate, result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

                                flagBreakInsideFor = true

                                break
                            } else {
                                WebUI.delay(10)

                                if (i == 3) {
                                    'Write To Excel GlobalVariable.StatusFailed and GlobalVariable.ReasonFailedStoredDB'
                                    CustomKeywords.'customizekeyword.WriteExcel.writeToExcelStatusReason'(sheet, GlobalVariable.NumofColm, 
                                        GlobalVariable.StatusFailed, (((findTestData(excelPath).getValue(GlobalVariable.NumofColm, 
                                            rowExcel('Reason Failed')) + ';') + 'Gagal Process Message Request dengan jeda waktu ') + 
                                        (i * 10)) + ' detik.')

                                    GlobalVariable.FlagFailed = 1
                                }
                            }
                        }
                        
                        if (flagContinueInsideFor == true) {
                            continue
                        }
                        
                        if (flagBreakInsideFor == true) {
                            break
                        }
                    }
                    
                    'jika Flag failednya 0'
                    if (GlobalVariable.FlagFailed == 0) {
                        'write to excel success'
                        CustomKeywords.'customizekeyword.WriteExcel.writeToExcel'(GlobalVariable.DataFilePath, sheet, 0, 
                            GlobalVariable.NumofColm - 1, GlobalVariable.StatusSuccess)
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

def hitAPI() {
    'HIT API'
    respon = WS.sendRequest(findTestObject('Postman/Process Message Request', [('requestDateTime') : findTestData(excelPath).getValue(
                    GlobalVariable.NumofColm, rowExcel('requestDateTime')), ('rowVersion') : '', ('receiverPhone') : findTestData(
                    excelPath).getValue(GlobalVariable.NumofColm, rowExcel('receiverPhone')), ('referenceNo') : findTestData(
                    excelPath).getValue(GlobalVariable.NumofColm, rowExcel('referenceNo')), ('templateCode') : findTestData(
                    excelPath).getValue(GlobalVariable.NumofColm, rowExcel('templateCode')), ('headerParam') : findTestData(
                    excelPath).getValue(GlobalVariable.NumofColm, rowExcel('headerParam')), ('bodyParam') : findTestData(
                    excelPath).getValue(GlobalVariable.NumofColm, rowExcel('bodyParam')), ('buttonParam') : findTestData(
                    excelPath).getValue(GlobalVariable.NumofColm, rowExcel('buttonParam')), ('requestUuid') : findTestData(
                    excelPath).getValue(GlobalVariable.NumofColm, rowExcel('requestUuid')), ('partnerCode') : findTestData(
                    excelPath).getValue(GlobalVariable.NumofColm, rowExcel('partnerCode'))]))

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

    respon
}

