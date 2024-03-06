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

'dapat total column'
int countColmExcel = findTestData(excelPath).columnNumbers

for (GlobalVariable.NumofColm = 2; GlobalVariable.NumofColm <= countColmExcel; (GlobalVariable.NumofColm)++) {
    if (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Status')).length() == 0) {
        break
    } else if (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('Status')).equalsIgnoreCase('Unexecuted')) {
        'inisiasi flag failed = 0'
        GlobalVariable.FlagFailed = 0

        'call test case untuk mendapatkan api key'
        WebUI.callTestCase(findTestCase('Generate New API Key'), [('excelPath') : excelPath, ('sheet') : sheet], FailureHandling.CONTINUE_ON_FAILURE)

		'rakit paginationDetails'
        String paginationDetails = ''

		'jika dataLimit atau data offset ada value'
        if ((findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('dataLimit')).length() > 0) || (findTestData(
            excelPath).getValue(GlobalVariable.NumofColm, rowExcel('dataOffset')).length() > 0)) {
			'rakit'
            paginationDetails = (paginationDetails + '"paginationDetails": {')

			'jika data limit ada value'
            if (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('dataLimit')).length() > 0) {
				'rakit datalimit'
                paginationDetails = (((paginationDetails + '"dataLimit": ') + findTestData(excelPath).getValue(GlobalVariable.NumofColm, 
                    rowExcel('dataLimit'))) + ',')
            }
            
			'jika data offset ada value'
            if (findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('dataOffset')).length() > 0) {
				'rakit data offset'
                paginationDetails = (((paginationDetails + '"dataOffset": ') + findTestData(excelPath).getValue(GlobalVariable.NumofColm, 
                    rowExcel('dataOffset'))) + ',')
            }
            
			'menghapus akhir comma'
            paginationDetails = (paginationDetails.replaceAll('(,*$)', '') + '},')
        }
        
        'HIT API'
        respon = WS.sendRequest(findTestObject('Postman/Get List Message Template', [('requestDateTime') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('requestDateTime')), ('rowVersion') : '', ('templateType') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('templateType')), ('templateCode') : findTestData(
                        excelPath).getValue(GlobalVariable.NumofColm, rowExcel('templateCode')), ('paginationDetails') : paginationDetails]))

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
            message = WS.getElementPropertyValue(respon, 'Message', FailureHandling.OPTIONAL)

            'Jika message success'
            if (message.toString() == 'Success') {
                'jika setting check store db hidup'
                if (GlobalVariable.checkStoreDB == 'Yes') {
                    'declare arrayindex'
                    arrayIndex = 0

                    'declare arraylist arraymatch'
                    ArrayList arrayMatch = []

                    'get data limit'
                    dataLimit = WS.getElementPropertyValue(respon, 'DataLimit', FailureHandling.OPTIONAL)

                    'get data offset'
                    dataOffset = WS.getElementPropertyValue(respon, 'DataOffset', FailureHandling.OPTIONAL)

                    'get totall data'
                    totalData = WS.getElementPropertyValue(respon, 'TotalData', FailureHandling.OPTIONAL)

                    'get total results'
                    ArrayList resultsAll = WS.getElementPropertyValue(respon, 'Results', FailureHandling.OPTIONAL)

                    'get parameter details'
                    parameterDetails = WS.getElementPropertyValue(respon, 'Results.ParameterDetails', FailureHandling.OPTIONAL)

					'get template type'
                    templateType = WS.getElementPropertyValue(respon, 'Results.TemplateType', FailureHandling.OPTIONAL)

					'get template code'
                    templateCode = WS.getElementPropertyValue(respon, 'Results.TemplateCode', FailureHandling.OPTIONAL)

					'get template name'
                    templateName = WS.getElementPropertyValue(respon, 'Results.TemplateName', FailureHandling.OPTIONAL)

					'get template description'
                    templateDescription = WS.getElementPropertyValue(respon, 'Results.TemplateDescription', FailureHandling.OPTIONAL)

					'get template language'
                    templateLanguage = WS.getElementPropertyValue(respon, 'Results.TemplateLanguage', FailureHandling.OPTIONAL)

                    'ambil store db untuk add tenant'
                    ArrayList result = CustomKeywords.'connection.GetListMessageTemplate.getGetListMessageTemplateStoreDB'(
                        connAcso, findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('tenantCode')), findTestData(
                            excelPath).getValue(GlobalVariable.NumofColm, rowExcel('templateCode')), findTestData(excelPath).getValue(
                            GlobalVariable.NumofColm, rowExcel('dataLimit')), findTestData(excelPath).getValue(GlobalVariable.NumofColm, 
                            rowExcel('dataOffset')))

					'array match mengenai total data results'
                    arrayMatch.add(WebUI.verifyEqual(resultsAll.size(), result.size() / 6, FailureHandling.CONTINUE_ON_FAILURE))

					'array match mengenai total data'
                    arrayMatch.add(WebUI.verifyEqual(totalData, result.size() / 6, FailureHandling.CONTINUE_ON_FAILURE))

					'jika datalimit dan kosong dan template code kosong'
                    if ((findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('dataLimit')) == '')) {
						'ambil max limit dari genset'
                        maxLimit = CustomKeywords.'connection.DataVerif.getMaxDataLimitPagination'(connAcso)

						'array match mengenai data limit dan max limit'
                        arrayMatch.add(WebUI.verifyMatch(dataLimit.toString(), maxLimit, false, FailureHandling.CONTINUE_ON_FAILURE))
                    } else {
						'array match mengenai data limit dan inputan user'
                        arrayMatch.add(WebUI.verifyMatch(dataLimit.toString(), findTestData(excelPath).getValue(GlobalVariable.NumofColm, 
                                    rowExcel('dataLimit')), false, FailureHandling.CONTINUE_ON_FAILURE))
                    }
                    
					'jika dataoffset koosng dan template code kosong'
                    if ((findTestData(excelPath).getValue(GlobalVariable.NumofColm, rowExcel('dataOffset')) == '')) {
					'array match mengenai data offset dan 0'
                        arrayMatch.add(WebUI.verifyMatch(dataOffset.toString(), '0', false, FailureHandling.CONTINUE_ON_FAILURE))
                    } else {
						' array match mengenai data offset dan inputan data offset'
                        arrayMatch.add(WebUI.verifyMatch(dataOffset.toString(), findTestData(excelPath).getValue(GlobalVariable.NumofColm, 
                                    rowExcel('dataOffset')), false, FailureHandling.CONTINUE_ON_FAILURE))
                    }
					
					'looping'
                    for (index = 0; index < (result.size() / 6); index++) {
                        'verify template type'
                        arrayMatch.add(WebUI.verifyMatch(templateType[index], result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

                        'verify template code'
                        arrayMatch.add(WebUI.verifyMatch(templateCode[index], result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

                        'verify template name'
                        arrayMatch.add(WebUI.verifyMatch(templateName[index], result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

                        'verify template description'
                        arrayMatch.add(WebUI.verifyMatch(templateDescription[index], result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

                        'verify template language'
                        arrayMatch.add(WebUI.verifyMatch(templateLanguage[index], result[arrayIndex++], false, FailureHandling.CONTINUE_ON_FAILURE))

                        'verify parameterdetails'
                        arrayMatch.add(WebUI.verifyMatch((parameterDetails[index]).toString(), (result[arrayIndex++]).toString(), 
                                false, FailureHandling.CONTINUE_ON_FAILURE))
                    }
                    
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
            getErrorMessageAPI(respon)
        }
    }
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

