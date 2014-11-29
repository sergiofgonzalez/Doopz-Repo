package org.joolzminer.doopz.services;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.joolzminer.doopz.domain.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class FileMapBuilderService {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(FileMapBuilderService.class);
	
	public Map<Long, Set<FileInfo>> buildFileMapByFileLength(List<File> files) {
		Assert.notEmpty(files, "files must not be null or empty");
		
		Map<Long, Set<FileInfo>> fileMapByLen = new TreeMap<>();
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
	
	public Map<String, Set<FileInfo>> buildFileMapByFileName(List<File> files) {
		Assert.notEmpty(files, "files must not be null or empty");
		
		Map<String, Set<FileInfo>> fileMapByName = new TreeMap<>();
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
