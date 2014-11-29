package org.joolzminer.doopz.services;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class FileRetrieverService {
	
	public List<File> getFiles(String baseDirectory) {
		Assert.hasText(baseDirectory, "base directory must not be null or empty");
		return (List<File>) FileUtils.listFiles(new File(baseDirectory), null, true);
	}
}
