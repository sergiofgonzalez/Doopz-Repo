package org.joolzminer.doopz.services.tests.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class FileSystemUtils provides some basic supporting ops for testing FileRetriever.
 */
public final class FileSystemUtils {
	
	/** The Constant CONSTANT_BASE_DIR. */
	private static final String CONSTANT_BASE_DIR = "./src/test/resources/file-tests/";
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(FileSystemUtils.class);
	
	/**
	 * Instantiates a new file system utils.
	 */
	private FileSystemUtils() {
		// prevent construction of instances
	}
	

	/**
	 * Reset test environment.
	 */
	public static void resetTestEnvironment() {
		File dir = new File(CONSTANT_BASE_DIR);
		if (dir.exists()) {
			logger.debug("Removing contents of '{}' to reset the test environment");
			
			try {
				FileUtils.deleteDirectory(dir);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * Creates the directory if not exists.
	 *
	 * @param directory the directory
	 * @return the string
	 */
	public static String createDirectoryIfNotExists(String directory) {
		String dirName = FilenameUtils.normalize(CONSTANT_BASE_DIR + directory);
		
		File dir = new File(dirName);
		if (dir.exists()) {
			logger.error("Error trying to create the directory '{}'", dir.getAbsolutePath());
			throw new IllegalArgumentException("The directory 'directory' already exists");
		} else {
			dir.mkdirs();
			return getCanonicalPath(dir);
		}
	}
	
	
	/**
	 * Creates the file in directory.
	 *
	 * @param directory the directory
	 * @param fileName the file name
	 * @return the string
	 */
	public static String createFileInDirectory(String directory, String fileName) {
		String dirName = FilenameUtils.normalize(CONSTANT_BASE_DIR) + directory;
		File dir = new File(dirName);
		if (!dir.exists()) {
			logger.error("The directory '{}' must exists before the file is created", dir.getAbsolutePath());
			throw new IllegalArgumentException("The directory 'directory' must exist.");
		} else {
			File file = new File(FilenameUtils.normalize(dirName + File.separator + fileName));
			try {
				if (!file.createNewFile()) {
					logger.error("The file '{}' could not be created : maybe it exists, or cannot be written", file);
					throw new IllegalArgumentException("The file 'filename' could not be created");
				}
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}	
			
			return getCanonicalPath(file);
		}		
	}
	
	/**
	 * Gets the canonical path.
	 *
	 * @param file the file
	 * @return the canonical path
	 */
	public static String getCanonicalPath(File file) {
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			logger.error("Error trying to get the canonical path for '{}'", file);
			throw new IllegalArgumentException(e);
		}		
	}
}
