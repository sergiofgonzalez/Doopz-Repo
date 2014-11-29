package org.joolzminer.doopz.domain;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.style.ToStringCreator;

public class FileInfo implements Comparable<FileInfo> {
	
	private String filePath;
	
	private String fileName;
	
	private Long fileLength;
	
	private Long crc32;
	
	public FileInfo(File file) {
		filePath = FilenameUtils.getFullPathNoEndSeparator(file.getAbsolutePath());
		fileName = file.getName();
		fileLength = file.length();
		
		// CRC32 is lazily loaded for performance reasons
		crc32 = null;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileLength() {
		return fileLength;
	}

	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

	public Long getCrc32() {
		return crc32;
	}

	public void setCrc32(Long crc32) {
		this.crc32 = crc32;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result	+ ((fileLength == null) ? 0 : fileLength.hashCode());
		result = prime * result	+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result	+ ((filePath == null) ? 0 : filePath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileInfo other = (FileInfo) obj;
		if (fileLength == null) {
			if (other.fileLength != null)
				return false;
		} else if (!fileLength.equals(other.fileLength))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		return true;
	}

	@Override
	public int compareTo(FileInfo other) {
		int fileNameComparisonResult = fileName.compareToIgnoreCase(other.fileName);
		if (fileNameComparisonResult != 0) {
			return fileNameComparisonResult;
		}
		
		int filePathComparisonResult = filePath.compareToIgnoreCase(other.filePath);
		if (filePathComparisonResult != 0) {
			return filePathComparisonResult;
		}
		
		if (fileLength < other.fileLength) {
			return -1;
		} else if (fileLength > other.fileLength) {
			return +1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
			.append("filePath", filePath)
			.append("fileName", fileName)
			.append("fileLength", fileLength)
			.append("crc32", crc32)
			.toString();
	}	
}
