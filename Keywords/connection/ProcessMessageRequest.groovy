package connection

import java.sql.Connection
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Statement
import com.kms.katalon.core.annotation.Keyword

class ProcessMessageRequest {

	String data, helperQuery
	int columnCount, i, updateVariable
	Statement stm
	ResultSetMetaData metadata
	ResultSet resultSet
	ArrayList<String> listdata = []

	@Keyword
	getRequestStatus(Connection conn, String requestUuid) {
		stm = conn.createStatement()

		resultSet = stm.executeQuery("select request_status from tr_message_request where request_uuid = '" + requestUuid + "'")
		metadata = resultSet.metaData

		columnCount = metadata.getColumnCount()

		while (resultSet.next()) {
			data = resultSet.getObject(1)
		}
		data
	}

	@Keyword
	getRetryCount(Connection conn, String requestUuid) {
		stm = conn.createStatement()

		resultSet = stm.executeQuery("select retry_count from tr_message_request where request_uuid = ''")
		metadata = resultSet.metaData

		columnCount = metadata.getColumnCount()

		while (resultSet.next()) {
			data = resultSet.getObject(1)
		}
		data
	}

	@Keyword
	getProcessMessageRequestStoreDB(Connection conn, String requestUuid) {
		stm = conn.createStatement()

		resultSet = stm.executeQuery("select to_char(tmr.process_end_date, 'yyyy-mm-dd'), tmr.request_status, to_char(tmr.dtm_upd, 'yyyy-mm-dd'),  tmp.conversation_id, tmp.to_phone_number, to_char(tmp.sent_date, 'yyyy-mm-dd') from tr_message_request tmr left join ms_tenant mst on mst.id_ms_tenant = tmr.id_ms_tenant  left join ms_msg_template mmt on mmt.id_ms_msg_template = tmr.id_ms_msg_template left join ms_lov msl on msl.id_ms_lov = tmr.lov_request_type left join tr_message_properties tmp on tmr.id_message_request = tmp.id_message_request where tmr.request_uuid = '" + requestUuid + "' order by tmr.dtm_crt desc")
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