package org.joolzminer.doopz.services;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.joolzminer.doopz.entities.FileInfo;
import org.springframework.stereotype.Service;


/**
 * The Class FileMapBuilderImpl provides the default implementation of FileMapBuilder.
 */
@Service
public class FileMapBuilderImpl implements FileMapBuilder {

	/** The Constant ILLEGAL_ARGUMENT_ERROR_MSG. */
	private static final String ILLEGAL_ARGUMENT_ERROR_MSG = "argument 'files' must not be empty or null";

	/* (non-Javadoc)
	 * @see org.joolzminer.doopz.services.FileMapBuilder#buildFileMapByFileLength(java.util.List)
	 */
	@Override
	public Map<Long, Set<FileInfo>> buildFileMapByFileLength(List<File> files) {
		if (files == null || files.isEmpty()) {
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_ERROR_MSG);
		}
		
		Map<Long, Set<FileInfo>> fileMapByLen = new TreeMap<Long, Set<FileInfo>>();
		for (File file : files) {
			FileInfo fileInfo = new FileInfo(file);
			
			Set<FileInfo> fileInfosForLen = fileMapByLen.get(fileInfo.getFileLength());
			if (fileInfosForLen == null) {
				fileInfosForLen = new TreeSet<FileInfo>();
			}
			
			fileInfosForLen.add(fileInfo);
			fileMapByLen.put(fileInfo.getFileLength(), fileInfosForLen);
		}
		
		return fileMapByLen;
	}

	/* (non-Javadoc)
	 * @see org.joolzminer.doopz.services.FileMapBuilder#buildFileMapByFileName(java.util.List)
	 */
	@Override
	public Map<String, Set<FileInfo>> buildFileMapByFileName(List<File> files) {
		if (files == null || files.isEmpty()) {
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_ERROR_MSG);
		}
		
		Map<String, Set<FileInfo>> fileMapByName = new TreeMap<String, Set<FileInfo>>();
		for (File file : files) {
			FileInfo fileInfo = new FileInfo(file);
			
			Set<FileInfo> fileInfosForName = fileMapByName.get(fileInfo.getFileName());
			if (fileInfosForName == null) {
				fileInfosForName = new TreeSet<FileInfo>();
			}
			
			fileInfosForName.add(fileInfo);
			fileMapByName.put(fileInfo.getFileName(), fileInfosForName);
		}
		
		return fileMapByName;
	}
}
