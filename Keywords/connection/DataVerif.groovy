package connection

import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import java.sql.Connection
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement
import com.kms.katalon.core.annotation.Keyword
import internal.GlobalVariable

class DataVerif {

	String data, helperQuery
	int columnCount, i, updateVariable
	Statement stm
	ResultSetMetaData metadata
	ResultSet resultSet
	ArrayList<String> listdata = []

	@Keyword
	settingBaseUrl(String excelPath, int colm, int row) {
		if (findTestData(excelPath).getValue(colm, row) == 'No') {
			GlobalVariable.base_url = findTestData('Login/Login').getValue(3, 2) + 'BASEURLSALAH'
		} else {
			GlobalVariable.base_url = findTestData('Login/Login').getValue(3, 2)
		}
	}

	@Keyword
	getMaxDataLimitPagination(Connection conn) {
		stm = conn.createStatement()

		resultSet = stm.executeQuery("select gs_value from am_general_setting where gs_code = 'MAX_DATA_LIMIT_PAGINATION'")
		metadata = resultSet.metaData

		columnCount = metadata.getColumnCount()

		while (resultSet.next()) {
			data = resultSet.getObject(1)
		}
		data
	}
}