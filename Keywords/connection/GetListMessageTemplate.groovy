package connection

import java.sql.Connection
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement
import com.kms.katalon.core.annotation.Keyword

class GetListMessageTemplate {

	String data, helperQuery
	int columnCount, i, updateVariable
	Statement stm
	ResultSetMetaData metadata
	ResultSet resultSet
	ArrayList<String> listdata = []

	@Keyword
	getGetListMessageTemplateStoreDB(Connection conn, String tenantCode, String templateCode, String dataLimit, String dataOffset) {
		stm = conn.createStatement()

		if (templateCode != '') {
			helperQuery = " AND mmt.template_code = '" + templateCode + "'"
		} else if (dataLimit.length() != 0 && dataOffset.length() != 0) {
			helperQuery = " OFFSET " + dataOffset + " LIMIT " + dataLimit
		} else if (dataLimit.length() == 0 && dataOffset.length() == 0) {
			helperQuery = " LIMIT 10"
		} else {
			helperQuery = ''
		}
		resultSet = stm.executeQuery("select mmt.template_type, mmt.template_code, mmt.template_name, mmt.template_description, mmt.template_language, null from ms_msg_template mmt left join ms_tenant mst on mmt.id_ms_tenant = mst.id_ms_tenant where mst.tenant_code = '" + tenantCode + "'" + helperQuery)
		metadata = resultSet.metaData

		columnCount = metadata.getColumnCount()

		while (resultSet.next()) {
			for (i = 1 ; i <= columnCount ; i++) {
				data = resultSet.getObject(i)
				listdata.add(data)
			}
		}
		listdata
	}
}