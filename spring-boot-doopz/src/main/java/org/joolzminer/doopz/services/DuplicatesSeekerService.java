package org.joolzminer.doopz.services;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.joolzminer.doopz.domain.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DuplicatesSeekerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DuplicatesSeekerService.class);
	
	@Autowired
	private FileRetrieverService fileRetrieverService;
	
	@Autowired
	private FileMapBuilderService fileMapBuilderService;
	
	public Map<Long, Set<FileInfo>> getDuplicatesByLength(String fromDirectory) {
		validateFromDir(fromDirectory);
		LOGGER.debug("loading files from `{}`", fromDirectory);
		List<File> files = fileRetrieverService.getFiles(fromDirectory);
		
		LOGGER.debug("building map of files with equal length");
		Map<Long, Set<FileInfo>> fileMapByLen = fileMapBuilderService.buildFileMapByFileLength(files);
		Map<Long, Set<FileInfo>> dupsByLen = new TreeMap<>();
		for (Entry<Long, Set<FileInfo>> entry : fileMapByLen.entrySet()) {
			if (entry.getValue().size() > 1) {
				dupsByLen.put(entry.getKey(), entry.getValue());
			}
		}
				
		return dupsByLen;
	}
	
	
	public Map<String, Set<FileInfo>> getDuplicatesByName(String fromDirectory) {
		validateFromDir(fromDirectory);
		LOGGER.debug("loading files from `{}`", fromDirectory);
		List<File> files = fileRetrieverService.getFiles(fromDirectory);
		
		LOGGER.debug("building map of files with same name");
		Map<String, Set<FileInfo>> fileMapByName = fileMapBuilderService.buildFileMapByFileName(files);
		Map<String, Set<FileInfo>> dupsByName = new TreeMap<>();
		for (Entry<String, Set<FileInfo>> entry : fileMapByName.entrySet()) {
			if (entry.getValue().size() > 1) {
				dupsByName.put(entry.getKey(), entry.getValue());
			}
		}

		return dupsByName;
	}
	
	private void validateFromDir(String fromDirectory) {
		LOGGER.debug("checking validity of `{}`", fromDirectory);
		
		Assert.hasText(fromDirectory, "the argument `fromDirectory` must not be null or empty");
		
		File dir = new File(fromDirectory);
		if (!dir.exists()) {
			throw new IllegalArgumentException("the argument `fromDirectory` must be an existing directory");
		}
		
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException("the argument `fromDirectory` must be a directory");
		}
	}
}
