package org.joolzminer.doopz.entities;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

/**
 * The Class FileInfo holds a File information consisting of the path where the file is located,
 *  the file name, its length in bytes and the CRC of its contents.
 */
public class FileInfo implements Comparable<FileInfo> {
	
	/** The file path. */
	private String filePath;
	
	/** The file name. */
	private String fileName;
	
	/** The file length. */
	private Long fileLength;
	
	/** The crc32. */
	private Long crc32;
	
	/**
	 * Instantiates a new file info. Requires the file to be not null
	 *
	 * @param file the file used as a source to instantiate a FileInfo
	 */
	public FileInfo(File file) {
		filePath = FilenameUtils.getFullPathNoEndSeparator(file.getAbsolutePath()); 
		fileName = file.getName();
		fileLength = file.length();
		
		// CRC32 is lazily loaded for performance reasons
		crc32 = null;			
	}
		
	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the file length.
	 *
	 * @return the file length
	 */
	public Long getFileLength() {
		return fileLength;
	}

	/**
	 * Sets the file length.
	 *
	 * @param fileLength the new file length
	 */
	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

	/**
	 * Gets the crc32.
	 *
	 * @return the crc32
	 */
	public Long getCrc32() {
		return crc32;
	}

	/**
	 * Sets the crc32.
	 *
	 * @param crc32 the new crc32
	 */
	public void setCrc32(Long crc32) {
		this.crc32 = crc32;
	}		
	
	/**
	 * Gets the file path.
	 *
	 * @return the file path
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Sets the file path.
	 *
	 * @param filePath the new file path
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {		
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileLength == null) ? 0 : fileLength.hashCode());
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((filePath == null) ? 0 : filePath.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		FileInfo other = (FileInfo) obj;
		if (fileLength == null) {
			if (other.fileLength != null) {
				return false;
			}
		} else if (!fileLength.equals(other.fileLength)) {
			return false;
		}
		if (fileName == null) {
			if (other.fileName != null) {
				return false;
			}
		} else if (!fileName.equals(other.fileName)) {
			return false;
		}
		if (filePath == null) {
			if (other.filePath != null) {
				return false;
			}
		} else if (!filePath.equals(other.filePath)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileInfo [filePath=" + filePath + ", fileName=" + fileName
				+ ", fileLength=" + fileLength + ", crc32=" + crc32 + "]";
	}
}
