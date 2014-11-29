package org.joolzminer.doopz.services;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class FileRetrieverService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileRetrieverService.class);
	
	public List<File> getFiles(String baseDirectory) {
		Assert.hasText(baseDirectory, "base directory must not be null or empty");
		
		LOGGER.debug("collecting files under directory `{}`", baseDirectory);
		List<File> files = (List<File>) FileUtils.listFiles(new File(baseDirectory), null, true);
		LOGGER.debug("{} file(s) found in `{}`", files.size(), baseDirectory); 
		
		return files;
	}
}
