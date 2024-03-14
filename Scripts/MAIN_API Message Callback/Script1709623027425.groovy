import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import internal.GlobalVariable as GlobalVariable

'get data file path'
GlobalVariable.DataFilePath = CustomKeywords.'customizekeyword.WriteExcel.getExcelPath'('\\Excel\\2. ACSO.xlsx')

'get total column excel'
int countColmExcel = findTestData(excelPath).columnNumbers

for (GlobalVariable.NumofColm = 2; GlobalVariable.NumofColm <= countColmExcel; (GlobalVariable.NumofColm)++) {
    if (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Status')).length() == 0) {
        break
    } else if (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Status')).equalsIgnoreCase('Unexecuted')) {
        'Flag failed reset 0'
        GlobalVariable.FlagFailed = 0

        'HIT API'
        respon = WS.sendRequest(findTestObject('Postman/Message Callback', [('requestDateTime') : findTestData(excelPath).getValue(
                        GlobalVariable.NumofColm, rowExcel('requestDateTime')), ('rowVersion') : '', ('from_phone_number') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('from_phone_number')), ('to_phone_number') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('to_phone_number')), ('id') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('id')), ('conversation_id') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('conversation_id')), ('template_name') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('template_name')), ('status') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('status')), ('timestamp') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('timestamp')), ('agent_name') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('agent_name')), ('created_time') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('created_time')), ('action_id') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('action_id')), ('message') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('message')), ('error') : findTestData(excelPath).getValue(
                        GlobalVariable.NumofColm, rowExcel('error'))]))

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
            'get message'
            message = WS.getElementPropertyValue(respon, 'Message', FailureHandling.OPTIONAL)

            'Jika messagenya success'
            if (message == 'Success') {
                if (GlobalVariable.FlagFailed == 0) {
                    'write to excel success'
                    CustomKeywords.'customizekeyword.WriteExcel.writeToExcel'(GlobalVariable.DataFilePath, sheet, 0, GlobalVariable.NumofColm - 
                        1, GlobalVariable.StatusSuccess)
                }
            } else {
                getErrorMessageAPI(respon)
            }
        } else {
            getErrorMessageAPI(respon)
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

