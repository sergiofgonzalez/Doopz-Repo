package org.joolzminer.doopz.services;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.inject.Inject;

import org.joolzminer.doopz.entities.FileInfo;
import org.springframework.stereotype.Service;

/**
 * The Class DuplicatesSeekerImpl provides the default implementation of DuplicatesSeeker operations.
 */
@Service
public class DuplicatesSeekerImpl implements DuplicatesSeeker {
	
	/** The file retriever. */
	@Inject
	private FileRetriever fileRetriever;
	
	/**
	 * Sets the file retriever.
	 *
	 * @param fileRetriever the new file retriever
	 */
	public void setFileRetriever(FileRetriever fileRetriever) {
		this.fileRetriever = fileRetriever;
	}

	/** The file map builder. */
	@Inject
	private FileMapBuilder fileMapBuilder;
	

	/**
	 * Sets the file map builder.
	 *
	 * @param fileMapBuilder the new file map builder
	 */
	public void setFileMapBuilder(FileMapBuilder fileMapBuilder) {
		this.fileMapBuilder = fileMapBuilder;
	}

	/* (non-Javadoc)
	 * @see org.joolzminer.doopz.services.DuplicatesSeeker#getDuplicatesByLength(java.lang.String)
	 */
	@Override
	public Map<Long, Set<FileInfo>> getDuplicatesByLength(String fromDirectory) {
		validateFromDir(fromDirectory);
		List<File> files = fileRetriever.getFiles(fromDirectory);
		Map<Long, Set<FileInfo>> fileMapByLen = fileMapBuilder.buildFileMapByFileLength(files);
		Map<Long, Set<FileInfo>> dupsByLen = new TreeMap<Long, Set<FileInfo>>();
		for (Entry<Long, Set<FileInfo>> entry : fileMapByLen.entrySet()) {
			if (entry.getValue().size() > 1) {
				dupsByLen.put(entry.getKey(), entry.getValue());
			}
		}
		
		return dupsByLen;
	}

	/* (non-Javadoc)
	 * @see com.joolz.ffdups.engine.DuplicatesSeeker#getDuplicatesByName(com.joolz.ffdups.entities.FileMap)
	 */
	@Override
	public Map<String, Set<FileInfo>> getDuplicatesByName(String fromDirectory) {
		validateFromDir(fromDirectory);
		List<File> files = fileRetriever.getFiles(fromDirectory);
		Map<String, Set<FileInfo>> fileMapByLen = fileMapBuilder.buildFileMapByFileName(files);
		Map<String, Set<FileInfo>> dupsByLen = new TreeMap<String, Set<FileInfo>>();
		for (Entry<String, Set<FileInfo>> entry : fileMapByLen.entrySet()) {
			if (entry.getValue().size() > 1) {
				dupsByLen.put(entry.getKey(), entry.getValue());
			}
		}		
		return dupsByLen;
	}


	/**
	 * Validates the from directory.
	 *
	 * @param fromDirectory the from directory to be validated
	 */
	private void validateFromDir(String fromDirectory) {
		if (fromDirectory == null) {
			throw new IllegalArgumentException("the argument 'fromDirectory' cannot be null");
		} else if (fromDirectory.length() == 0) {
			throw new IllegalArgumentException("the argument 'fromDirectory' cannot be empty");
		} else if (!new File(fromDirectory).exists()) {
			throw new IllegalArgumentException("the argument 'fromDirectory' must be a valid directory");
		}
	}
}
