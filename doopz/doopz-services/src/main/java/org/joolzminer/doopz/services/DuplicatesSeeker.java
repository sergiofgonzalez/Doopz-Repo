package org.joolzminer.doopz.services;

import java.util.Map;
import java.util.Set;

import org.joolzminer.doopz.entities.FileInfo;


/**
 * The Interface DuplicatesSeeker defines the operations that search for duplicates from a given
 * directory using different strategies.
 */
public interface DuplicatesSeeker {
	
	/**
	 * Gets the duplicates FileMap from a given directory using a file length. That is, two files are
	 * considered equal if their lengths match.
	 *
	 * @param fromDirectory the directory from where the files are scanned
	 * @return the FileMap with the duplicates using the file length strategy. It consists of a Map<k,v> in
	 * 	which k is the length of the file in bytes and v is a set of FileInfos for all the files with that
	 * 	length.
	 * @throws IllegalArgumentException if fromDirectory is null or empty
	 */
	Map<Long, Set<FileInfo>> getDuplicatesByLength(String fromDirectory);
	
	/**
	 * Gets the duplicates FileMap from a given directory using a file name. That is, two files are
	 * considered equal if their names match.
	 *
	 * @param fromDirectory the directory from where the files are scanned
	 * @return the FileMap with the duplicates using the file name strategy. It consists of a Map<k,v> in
	 * 	which k is the name of the file and v is a set of FileInfos for all the files with that name.
	 * @throws IllegalArgumentException if fromDirectory is null or empty
	 */
	Map<String, Set<FileInfo>> getDuplicatesByName(String fromDirectory);
}
