package org.joolzminer.doopz.runners;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.joolzminer.doopz.entities.FileInfo;
import org.joolzminer.doopz.services.DuplicatesSeeker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


public class ConsoleUI {
	
	private static final String APP_CONTEXT_XML = "console-application-context.xml";
	
	private static final String CONF_PROPERTIES_FILE = "doopz-console.properties";
	
	private static final String FROM_DIRECTORY_KEY = "doopz.from.directory";
	
	private static final Logger logger = LoggerFactory.getLogger(ConsoleUI.class);
	
	public static void main(String[] args) {
		@SuppressWarnings({ "resource" })
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(APP_CONTEXT_XML);
		
		DuplicatesSeeker duplicatesSeeker = (DuplicatesSeeker) context.getBean(DuplicatesSeeker.class);
		
		String fromDirectory = getFromDirectory();
		

		Map<String, Set<FileInfo>> dupsByName = duplicatesSeeker.getDuplicatesByName(fromDirectory);
		Map<Long, Set<FileInfo>> dupsByLen = duplicatesSeeker.getDuplicatesByLength(fromDirectory);
		
		prettyPrintDupsByName(dupsByName);
		prettyPrintDupsByLen(dupsByLen);
	}

	private static String getFromDirectory() {
		Properties configProperties = loadPropertiesFile();
		String fromDirectory = configProperties.getProperty(FROM_DIRECTORY_KEY);
		if (fromDirectory == null) {
			logger.error("could not find the property '{}' on the configuration properties file '{}'",
					FROM_DIRECTORY_KEY, CONF_PROPERTIES_FILE);
			throw new IllegalStateException("the property '" + FROM_DIRECTORY_KEY + "' was not found");
		}
		return fromDirectory;
	}
	
	private static Properties loadPropertiesFile() {
		Properties properties = null;
		try {
			properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(CONF_PROPERTIES_FILE));
		} catch (IOException e) {
			logger.error("Doopz Console UI requires the existence of a file named '{}' but the file" +
					" was not found", CONF_PROPERTIES_FILE);
			throw new IllegalStateException("could not find '" + CONF_PROPERTIES_FILE + "' in the classpath");
		}
		return properties;
	}
	
	private static void prettyPrintDupsByName(Map<String, Set<FileInfo>> dups) {
		if (dups.isEmpty()) {
			System.out.println("No duplicate file names found");
		} else {
			for (Entry<String, Set<FileInfo>> dupEntry : dups.entrySet()) {
				System.out.println(dupEntry.getKey());
				for (FileInfo fileInfo : dupEntry.getValue()) {
					System.out.println("\t" + fileInfo.getFilePath() + "(" + fileInfo.getFileLength() + " bytes)");
				}
			}
		}
	}
	
	private static void prettyPrintDupsByLen(Map<Long, Set<FileInfo>> dups) {
		if (dups.isEmpty()) {
			System.out.println("No duplicate file length found");
		} else {
			for (Entry<Long, Set<FileInfo>> dupEntry : dups.entrySet()) {
				System.out.println("files of " + dupEntry.getKey() + " bytes");
				for (FileInfo fileInfo : dupEntry.getValue()) {
					System.out.println("\t" + fileInfo.getFileName() + " on " + fileInfo.getFilePath());
				}
			}
		}
	}	
}
