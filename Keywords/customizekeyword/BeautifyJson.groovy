package customizekeyword

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import internal.GlobalVariable

class BeautifyJson {

	WriteExcel needto = new WriteExcel()

	def process(String responseBody, String sheet, int rowNo, String fileName) {
		try {
			// Parse the original JSON string
			JsonSlurper slurper = new JsonSlurper()
			Map json = slurper.parseText(responseBody)

			// Beautify the JSON
			JsonBuilder builder = new JsonBuilder(json)
			String beautifiedJson = builder.toPrettyString()

			try {
				needto.writeToExcel(GlobalVariable.DataFilePath, sheet, rowNo, GlobalVariable.NumofColm - 1, beautifiedJson)
			} catch (IllegalArgumentException ex) {
				String beautifiedJsonPath = System.getProperty('user.dir') + File.separator + "Response" + File.separator + fileName + ".json"
				new File(beautifiedJsonPath).text = beautifiedJson
				needto.writeToExcel(GlobalVariable.DataFilePath, sheet, rowNo, GlobalVariable.NumofColm - 1, beautifiedJsonPath)
			}
		} catch (Throwable t) {
			println("Failed to parse JSON: ${t.message}")
			needto.writeToExcel(GlobalVariable.DataFilePath, sheet, rowNo, GlobalVariable.NumofColm - 1, t.message.toString())
			// Handle the JSON parsing error here
			// For example, you can log the error or perform alternative processing
		} catch (IllegalArgumentException e) {
			println("Failed to beautify the JSON: ${e.message}")
		}
	}
}
