package customizekeyword

import com.kms.katalon.core.annotation.Keyword

class Download {

	@Keyword
	isFileDownloaded(String deleteFile) {
		boolean isDownloaded = false
		File dir = new File(System.getProperty('user.dir') + '\\Download')
		//Getting the list of all the files in the specific directory
		File[] fList = dir.listFiles()
		for (File f : fList) {
			//checking the extension of the file with endsWith method.
			if (f.exists()) {
				if (deleteFile == 'Yes') {
					f.delete()
				}
				isDownloaded = true
			}
		}
		isDownloaded
	}
}
