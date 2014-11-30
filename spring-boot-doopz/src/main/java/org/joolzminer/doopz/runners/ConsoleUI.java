package org.joolzminer.doopz.runners;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.joolzminer.doopz.domain.FileInfo;
import org.joolzminer.doopz.services.DuplicatesSeekerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleUI implements CommandLineRunner {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleUI.class);
	
	@Value("${application.fromDir}")
	private String fromDirectory;
	
	@Autowired
	private DuplicatesSeekerService duplicatesSeekerService;
		
	@Override
	public void run(String... args) throws Exception {
		Map<String, Set<FileInfo>> dupsByName = duplicatesSeekerService.getDuplicatesByName(fromDirectory);
		Map<Long, Set<FileInfo>> dupsByLen = duplicatesSeekerService.getDuplicatesByLength(fromDirectory);
		
		System.out.println("Listing possible duplicate files by name: ");
		prettyPrintDupsByName(dupsByName);
		
		System.out.println("Listing possible duplicate files by length: ");
		prettyPrintDupsByLen(dupsByLen);
	}

	private void prettyPrintDupsByName(Map<String, Set<FileInfo>> dups) {
		if (dups.isEmpty()) {
			System.out.println("No duplicate file names found");
		} else {
			for (Entry<String, Set<FileInfo>> dupEntry : dups.entrySet()) {
				System.out.println(dupEntry.getKey());
				for (FileInfo fileInfo : dupEntry.getValue()) {
					System.out.println("\t" + fileInfo.getFilePath() + " (" + FileUtils.byteCountToDisplaySize(fileInfo.getFileLength()) + ")");
				}
				System.out.println();				
			}
		}		
	}
	
	private void prettyPrintDupsByLen(Map<Long, Set<FileInfo>> dups) {
		if (dups.isEmpty()) {
			System.out.println("No files with same length found");
		} else {
			for (Entry<Long, Set<FileInfo>> dupEntry : dups.entrySet()) {
				System.out.println(dupEntry.getKey() + " bytes");
				for (FileInfo fileInfo : dupEntry.getValue()) {
					System.out.println("\t" + fileInfo.getFilePath() + " (" + FileUtils.byteCountToDisplaySize(fileInfo.getFileLength()) + ")");
				}				
				System.out.println();
			}
		}
	}
}
