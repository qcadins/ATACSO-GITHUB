package customizekeyword

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import com.kms.katalon.core.annotation.Keyword
import internal.GlobalVariable

import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFRow

class WriteExcel {

	FileInputStream file
	XSSFWorkbook workbook
	XSSFSheet sheet
	XSSFRow row
	XSSFCell cell

	@Keyword
	writeToExcel(String filePath, String sheetName, int rowNo, int collNo, String cellValue) {
		file = new FileInputStream(new File(filePath))

		workbook = new XSSFWorkbook(file)
		sheet = workbook.getSheet(sheetName)

		// Explicitly specify types for row and cell
		row = sheet.getRow(rowNo) ?: sheet.createRow(rowNo)
		cell = row.getCell(collNo) ?: row.createCell(collNo)

		cell.cellValue = cellValue

		file.close()
		file.withCloseable {
			workbook.write(new FileOutputStream(new File(filePath)))
		}
	}

	@Keyword
	writeToExcelNumber(String filePath, String sheetName, int rowNo, int collNo, Integer cellValue) {
		file = new FileInputStream(new File(filePath))
		workbook = new XSSFWorkbook(file)
		sheet = workbook.getSheet(sheetName)

		// Explicitly specify types for row and cell
		row = sheet.getRow(rowNo) ?: sheet.createRow(rowNo)
		cell = row.getCell(collNo) ?: row.createCell(collNo)

		cell.cellValue = cellValue

		file.close()
		FileOutputStream outFile = new FileOutputStream(new File(filePath))
		workbook.write(outFile)
		outFile.flush()
		outFile.close()
	}

	@Keyword
	writeToExcelDecimal(String filePath, String sheetName, int rowNo, int collNo, BigDecimal cellValue) {
		file = new FileInputStream(new File(filePath)) //initiate excel repository

		workbook = new XSSFWorkbook(file)
		sheet = workbook.getSheet(sheetName)

		row = sheet.getRow(rowNo) ?: sheet.createRow(rowNo)
		cell = row.getCell(collNo) ?: row.createCell(collNo)

		cell.cellValue = cellValue

		file.close()
		FileOutputStream outFile = new FileOutputStream(new File(filePath))
		workbook.write(outFile)
		outFile.flush()
		outFile.close()
	}

	// write to excel status and reason
	@Keyword
	writeToExcelStatusReason(String sheetname, int colm, String status, String reason) {
		(new customizekeyword.WriteExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
				0, colm - 1, status)
		(new customizekeyword.WriteExcel()).writeToExcel(GlobalVariable.DataFilePath, sheetname,
				1, colm - 1, reason)
	}

	@Keyword
	writeToExcelFormula(String filePath, String sheetName, int rowNo, int collNo, String cellValue) throws IOException {
		file = new FileInputStream(new File(filePath))
		workbook = new XSSFWorkbook(file)
		sheet = workbook.getSheet(sheetName)

		row = sheet.getRow(rowNo) ?: sheet.createRow(rowNo)
		cell = row.getCell(collNo) ?: row.createCell(collNo)

		cell.cellValue = cellValue

		file.close()
		FileOutputStream outFile = new FileOutputStream(new File(filePath))
		workbook.write(outFile)
		outFile.flush()
		outFile.close()
	}

	//keyword getExcelPath
	@Keyword
	getExcelPath(String path) {
		String userDir = System.getProperty('user.dir')

		String filePath = userDir + path

		filePath
	}

	//keyword getExcelRow
	@Keyword
	getExcelRow(String filePath, String sheetName, String cellValue) {
		file = new FileInputStream(new File(filePath)) //initiate excel repository
		workbook = new XSSFWorkbook(file)
		sheet = workbook.getSheet(sheetName) //getSheet -> sheet num n (start from index 0)
		row = null
		int rowNum = -1
		for (int i = 0; i <= sheet.lastRowNum; i++) {
			row = sheet.getRow(i)
			try {
				if (row != null && row.getCell(0) != null && row.getCell(0).stringCellValue.equalsIgnoreCase(cellValue)) {
					rowNum = i
					break
				}
			} catch (IllegalArgumentException e) {
				println(e)
			}
		}
		file.close()
		rowNum + 1
	}
}
