package connection

import java.sql.Connection
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement
import com.kms.katalon.core.annotation.Keyword

class SendMessage {

	String data, helperQuery
	int columnCount, i, updateVariable
	Statement stm
	ResultSetMetaData metadata
	ResultSet resultSet
	ArrayList<String> listdata = []

	@Keyword
	getSendMessageStoreDB(Connection conn, String tenantCode, String templateCode) {
		stm = conn.createStatement()

		resultSet = stm.executeQuery("select to_char(tmr.request_date, 'yyyy-mm-dd'), msl.description, mst.tenant_code, tmr.queue_message_id, tmr.reference_no, mmt.template_code, tmr.request_status, tmr.retry_count from tr_message_request tmr left join ms_tenant mst on mst.id_ms_tenant = tmr.id_ms_tenant left join ms_msg_template mmt on mmt.id_ms_msg_template = tmr.id_ms_msg_template left join ms_lov msl on msl.id_ms_lov = tmr.lov_request_type where mst.tenant_code = '" + tenantCode + "' AND mmt.template_code = '" + templateCode + "' order by tmr.dtm_crt desc")
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