package org.joolzminer.doopz.services;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joolzminer.doopz.entities.FileInfo;


/**
 * The Interface FileMapBuilder.
 */
public interface FileMapBuilder {

	/**
	 * Builds the file map grouping all the files that have the same length in bytes in a Map<k,v>, where
	 * 	k is the length of the file in bytes, and v is a set of all FileInfos for the files with that length.
	 *
	 * @param files the files
	 * @return the map
	 * @throws IllegalArgumentException if the list of files is null or empty
	 */
	Map<Long, Set<FileInfo>> buildFileMapByFileLength(List<File> files);
	
	/**
	 * Builds the file map by file name grouping all the files that have the same name in a Map<k,v>, where
	 * 	k is the name of the file, and v is a set of all FileInfos for the files with that name.
	 *
	 * @param files the files
	 * @return the map
	 * @throws IllegalArgumentException if the list of files is null or empty
	 */
	Map<String, Set<FileInfo>> buildFileMapByFileName(List<File> files);
}
