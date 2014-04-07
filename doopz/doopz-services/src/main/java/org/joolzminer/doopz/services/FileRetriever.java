package org.joolzminer.doopz.services;

import java.io.File;
import java.util.List;

/**
 * The Interface FileRetriever provides support to retrieve the list of files that exist under a given 
 * directory.
 */
public interface FileRetriever {
	
	/**
	 * Gets the files defined under the given directory.
	 *
	 * @param baseDirectory the file directory from which the file system is scanned for files.
	 * @return the files found under the specified base directory or an empty list if none is found
	 * @throws IllegalArgumentException if the baseDirectory does not exists or the specified directory is
	 * invalid
	 */
	List<File> getFiles(String baseDirectory);
}
