package org.joolzminer.doopz.services.tests;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.joolzminer.doopz.services.FileRetriever;
import org.joolzminer.doopz.services.tests.utils.FileSystemUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * The Class FileRetrieverTest provides the tests for FileRetriever operations using the following
 * strategy:
 * + Partition of the space:
 * - baseDirectory 	: the initial directory from which the file system is scanned for files
 * - files			: the result list
 * 
 * + baseDirectory 	: null, invalid, inexistent, empty, root directory, middle directory, leaf directory
 * + files			: empty, single entry, several.
 * 
 * Note:
 * this test does not use mocking because FileRetriever depends on Apache FileUtils which defines static
 * methods for file operations.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/test-context-no-database.xml" })
public class FileRetrieverTest {

	/** The Constant logger. */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(FileRetrieverTest.class);
	
	@Inject
	private FileRetriever fileRetriever;
	
	@BeforeClass
	public static void setupBeforeClass() {
		FileSystemUtils.resetTestEnvironment();
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		FileSystemUtils.resetTestEnvironment();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDirNull() {
		fileRetriever.getFiles(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDirInvalid() {
		fileRetriever.getFiles("zz:\\~slkj@");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDirUnexisting() {
		fileRetriever.getFiles("c:\this-does-not-exist");
	}	
		
	@Test
	public void testEmpty() {
		String absDirName = FileSystemUtils.createDirectoryIfNotExists("empty-dir");
		List<File> files = fileRetriever.getFiles(absDirName);
		Assert.assertTrue("files must be empty", files.isEmpty());
	}
	
	@Test
	public void testSingleEntry() {
		String absDirName = FileSystemUtils.createDirectoryIfNotExists("singleton");
		String absFileName = FileSystemUtils.createFileInDirectory("singleton", "singleton.txt");
		List<File> files = fileRetriever.getFiles(absDirName);
		Assert.assertTrue("dir must contain a single file", files.size() == 1);
		Assert.assertEquals(absFileName, FileSystemUtils.getCanonicalPath(files.get(0)));
	}
	
	@Test
	public void testSeveralEntries() {
		String absDirName = FileSystemUtils.createDirectoryIfNotExists("several");
		String[] fileArray = { FileSystemUtils.createFileInDirectory("several", "file-0.txt"),
								FileSystemUtils.createFileInDirectory("several", "file-1.txt"),
								FileSystemUtils.createFileInDirectory("several", "file-2.txt") };
		
		List<File> files = fileRetriever.getFiles(absDirName);
		Assert.assertTrue("directory must contain three files", files.size() == 3);
		for (String file : fileArray) {
			Assert.assertTrue("files must contain file", files.contains(new File(file)));
		}
	}
}
