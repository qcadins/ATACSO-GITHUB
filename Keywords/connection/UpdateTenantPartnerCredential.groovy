package connection

import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import java.sql.Connection
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement
import com.kms.katalon.core.annotation.Keyword
import internal.GlobalVariable

class UpdateTenantPartnerCredential {

	String data
	int columnCount, i, updateVariable
	Statement stm
	ResultSetMetaData metadata
	ResultSet resultSet
	ArrayList<String> listdata = []

	@Keyword
	getUpdateTenantPartnerCredentialStoreDB(Connection conn, String tenantCode) {
		stm = conn.createStatement()

		resultSet = stm.executeQuery("select mst.tenant_code, partner_code, encrypted_access_token, access_token_expired_date, TO_CHAR(mpt.dtm_crt, 'yyyy-mm-dd') from ms_partner_credential mpt left join ms_tenant mst on mpt.id_ms_tenant = mst.id_ms_tenant where mst.tenant_code = '"+tenantCode+"'")
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