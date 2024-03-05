package customizekeyword

import java.text.SimpleDateFormat
import com.kms.katalon.core.annotation.Keyword

class ParseDate {

	@Keyword
	parseDateFormat(String date, String format1, String format2) {
		Locale locale = Locale.US
		Date parsedDate
		String sentDate, sDate

		SimpleDateFormat sdf = new SimpleDateFormat(format1, locale)

		parsedDate = null

		sentDate = date

		parsedDate = sdf.parse(sentDate)

		sdf = new SimpleDateFormat(format2, locale)

		sDate = sdf.format(parsedDate)

		sDate
	}
}
