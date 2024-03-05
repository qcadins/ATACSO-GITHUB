package customizekeyword

import com.kms.katalon.core.annotation.Keyword
import org.apache.commons.codec.binary.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.security.MessageDigest

class ParseText {

	@Keyword
	parseEncrypt(String value, String aesKey) {
		SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes('UTF-8'), 'AES')
		Cipher cipher = Cipher.getInstance('AES/ECB/PKCS5Padding')
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
		if (value == null) {
			value = ''
		}
		byte[] encrypted = cipher.doFinal(value.bytes)
		Base64.encodeBase64String(encrypted)
	}

	@Keyword
	parseDecrypt(String encrypted, String aesKey) {
		SecretKeySpec skeySpec = new SecretKeySpec(aesKey.getBytes('UTF-8'), 'AES')
		Cipher cipher = Cipher.getInstance('AES/ECB/PKCS5Padding')
		cipher.init(Cipher.DECRYPT_MODE, skeySpec)
		byte[] originalText = cipher.doFinal(Base64.decodeBase64(encrypted))
		new String(originalText)
	}

	@Keyword
	convertToSHA256(String input) {
		MessageDigest digest = MessageDigest.getInstance('SHA-256')
		byte[] encodedHash = digest.digest(input.getBytes('UTF-8'))
		encodedHash.collect { byteValue ->
			String.format('%02x', byteValue)
		}.join()
	}
}
