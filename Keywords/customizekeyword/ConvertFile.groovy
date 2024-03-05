package customizekeyword

import com.kms.katalon.core.annotation.Keyword
import javax.xml.bind.DatatypeConverter
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Base64 as base64

class ConvertFile {

	@Keyword
	base64File(String filename) {
		File f = new File(System.getProperty('user.dir') + '\\File' + filename)
		FileInputStream fis = new FileInputStream(f)
		byte[] byteArray = new byte[(int)f.length()]
		fis.read(byteArray)
		String imageString = base64.encoder.encodeToString(byteArray)

		imageString.replaceAll('[\r\n\t ]', '')
	}

	@Keyword
	decodeBase64(String base64String, String filename) {
		byte[] bytes = DatatypeConverter.parseBase64Binary(base64String)

		// Specify the file path and name
		String filePath = System.getProperty('user.dir') + '\\Download\\' + filename + '.PDF'

		// Write the decoded bytes to the file
		Files.write(Paths.get(filePath), bytes)
	}

	@Keyword
	decodeBase64Extension(String base64String, String filenameWithExtension) {
		byte[] bytes = DatatypeConverter.parseBase64Binary(base64String)

		// Specify the file path and name
		String filePath = System.getProperty('user.dir') + '\\Download\\' + filenameWithExtension

		// Write the decoded bytes to the file
		Files.write(Paths.get(filePath), bytes)
	}

	@Keyword
	decodeBase64crt(String base64String, String filename) {
		byte[] bytes = DatatypeConverter.parseBase64Binary(base64String)

		// Specify the file path and name
		String filePath = System.getProperty('user.dir') + '\\Download\\' + filename + '.crt'

		// Write the decoded bytes to the file
		Files.write(Paths.get(filePath), bytes)
	}

	@Keyword
	decodeBase64Excel(String base64String, String filename) {
		byte[] bytes = DatatypeConverter.parseBase64Binary(base64String)

		// Specify the file path and name
		String filePath = System.getProperty('user.dir') + '\\Download\\' + filename + '.xlsx'

		// Write the decoded bytes to the file
		Files.write(Paths.get(filePath), bytes)
	}
}
