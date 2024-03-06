package connection

import java.sql.Connection
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement
import com.kms.katalon.core.annotation.Keyword

class GenerateAPIKey {

	String data
	int columnCount, i, updateVariable
	Statement stm
	ResultSetMetaData metadata
	ResultSet resultSet
	ArrayList listdata = []

	@Keyword
	getGenerateNewAPIKeyStoreDB(Connection conn, String tenantCode) {
		stm = conn.createStatement()

		resultSet = stm.executeQuery("select msl.description, TO_CHAR(trc.update_date, 'yyyy-MM-dd'), trc.ip_address, trc.update_details, TO_CHAR(trc.dtm_crt, 'yyyy-mm-dd'), TO_CHAR(mst.dtm_upd, 'yyyy-mm-dd') from tr_credential_update_log trc left join ms_lov msl on trc.lov_update_type = msl.id_ms_lov left join ms_tenant mst on trc.id_ms_tenant = mst.id_ms_tenant where mst.tenant_code = '" + tenantCode + "' order by trc.dtm_crt desc limit 1")
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