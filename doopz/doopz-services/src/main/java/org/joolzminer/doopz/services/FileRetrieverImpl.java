package org.joolzminer.doopz.services;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

/**
 * The Class FileRetrieverImpl provides the default implementation of FileRetriever.
 */
@Service
public class FileRetrieverImpl implements FileRetriever {

	/* (non-Javadoc)
	 * @see com.joolz.ffdups.engine.FileRetriever#getFiles(java.lang.String)
	 */
	@Override
	public List<File> getFiles(String baseDirectory) {
		if (baseDirectory == null) {
			throw new IllegalArgumentException("base directory must not be null");
		}
		
		return (List<File>) FileUtils.listFiles(new File(baseDirectory), null, true);
	}
}
