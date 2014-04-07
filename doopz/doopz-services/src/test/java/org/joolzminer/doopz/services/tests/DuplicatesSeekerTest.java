package org.joolzminer.doopz.services.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.joolzminer.doopz.entities.FileInfo;
import org.joolzminer.doopz.services.DuplicatesSeeker;
import org.joolzminer.doopz.services.DuplicatesSeekerImpl;
import org.joolzminer.doopz.services.FileMapBuilder;
import org.joolzminer.doopz.services.FileMapBuilderImpl;
import org.joolzminer.doopz.services.FileRetriever;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class DuplicatesSeekerTest provides the tests for DuplicatesSeekerImpl using the following strategy:
 * + Partition of the space:
 * - fromDir 	: the initial directory from which the DuplicatesSeekerImpl searches for duplicates
 * - fileMap	: the results of the duplicates search
 * 
 * + fromDir 	: null, empty, non-existing, invalid, root directory, middle directory, leaf directory
 * + fileMap	: byName, byLength, empty, single entry, several.
 */
public class DuplicatesSeekerTest {

	private DuplicatesSeeker duplicatesSeeker;
	
	private FileMapBuilder fileMapBuilder;
	
	private FileRetriever mockedFileRetriever;
	
	
	@Before
	public void setUpBefore() {
		mockedFileRetriever = mock(FileRetriever.class);
		fileMapBuilder = new FileMapBuilderImpl();
		duplicatesSeeker = new DuplicatesSeekerImpl();
		((DuplicatesSeekerImpl)duplicatesSeeker).setFileRetriever(mockedFileRetriever);
		((DuplicatesSeekerImpl)duplicatesSeeker).setFileMapBuilder(fileMapBuilder);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullFromDir() {
		duplicatesSeeker.getDuplicatesByLength(null);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEmptyDir() {
		duplicatesSeeker.getDuplicatesByLength("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNonExistingDir() {
		duplicatesSeeker.getDuplicatesByLength("z:\\non-existing-dir");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidDir() {
		duplicatesSeeker.getDuplicatesByLength("ñ:this is not a dirñ");
	}
	
	@Test
	public void testByNameNoDups() {
		List<File> files = new ArrayList<File>();
		files.add(new File("file1.txt"));
		files.add(new File("file2.txt"));
		
		when(mockedFileRetriever.getFiles(anyString())).thenReturn(files);
		
		Map<String, Set<FileInfo>> dupsByName = duplicatesSeeker.getDuplicatesByName("c:\\");
		
		assertTrue("fileMap should be empty", dupsByName.isEmpty());
	}

	@Test
	public void testByNameSingleDup() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\file1.txt");		
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getName()).thenReturn("file2.txt");
		when(mockedFile2.length()).thenReturn(1L);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\file2.txt");
		
		File mockedFile3 = mock(File.class);
		when(mockedFile3.getName()).thenReturn("file1.txt");
		when(mockedFile3.length()).thenReturn(1L);
		when(mockedFile3.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");		
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile1);
		files.add(mockedFile2);
		files.add(mockedFile3);
		
		when(mockedFileRetriever.getFiles(anyString())).thenReturn(files);

		Map<String, Set<FileInfo>> dupsByName = duplicatesSeeker.getDuplicatesByName("c:\\");
		
		assertTrue("fileMap should contain one entry", dupsByName.size() == 1);
		String[] expectedKeys = { "file1.txt" };
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile1), new FileInfo(mockedFile3) }};

		for (int d = 0; d < expectedFileInfos.length; d++) {
			Arrays.sort(expectedFileInfos[d]);
		}
		
		int i = 0;
		for (Entry<String, Set<FileInfo>> entry : dupsByName.entrySet()) {
			assertEquals("filename on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}

	@Test
	public void testByNameSeveralDups() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\file1.txt");
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getName()).thenReturn("file1.txt");
		when(mockedFile2.length()).thenReturn(1L);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");
		
		File mockedFile3 = mock(File.class);
		when(mockedFile3.getName()).thenReturn("file2.txt");
		when(mockedFile3.length()).thenReturn(1L);
		when(mockedFile3.getAbsolutePath()).thenReturn("c:\\dir\\file2.txt");
		
		File mockedFile4 = mock(File.class);
		when(mockedFile4.getName()).thenReturn("file1.txt");
		when(mockedFile4.length()).thenReturn(1L);
		when(mockedFile4.getAbsolutePath()).thenReturn("c:\\dir\\another\\file1.txt");
		
		File mockedFile5 = mock(File.class);
		when(mockedFile5.getName()).thenReturn("file2.txt");
		when(mockedFile5.length()).thenReturn(1L);
		when(mockedFile5.getAbsolutePath()).thenReturn("c:\\dir\\another\\file2.txt");
		
		File mockedFile6 = mock(File.class);
		when(mockedFile6.getName()).thenReturn("file3.txt");
		when(mockedFile6.length()).thenReturn(1L);
		when(mockedFile6.getAbsolutePath()).thenReturn("c:\\dir\\another\\file3.txt");
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile1);
		files.add(mockedFile2);
		files.add(mockedFile3);
		files.add(mockedFile4);
		files.add(mockedFile5);
		files.add(mockedFile6);
		
		when(mockedFileRetriever.getFiles(anyString())).thenReturn(files);
		
		Map<String, Set<FileInfo>> dupsByName = duplicatesSeeker.getDuplicatesByName("c:\\");
		
		assertTrue("fileMap should contain two entries", dupsByName.size() == 2);
		String[] expectedKeys = { "file1.txt", "file2.txt" };
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile1), new FileInfo(mockedFile2), new FileInfo(mockedFile4) },
											{ new FileInfo(mockedFile3), new FileInfo(mockedFile5)}};

		for (int d = 0; d < expectedFileInfos.length; d++) {
			Arrays.sort(expectedFileInfos[d]);
		}
		
		int i = 0;
		for (Entry<String, Set<FileInfo>> entry : dupsByName.entrySet()) {
			assertEquals("filename on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}
	
	@Test
	public void testByLenNoDups() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\file1.txt");
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getName()).thenReturn("file1.txt");
		when(mockedFile2.length()).thenReturn(2L);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile1);
		files.add(mockedFile2);
		
		when(mockedFileRetriever.getFiles(anyString())).thenReturn(files);
		
		Map<Long, Set<FileInfo>> dupsByLen = duplicatesSeeker.getDuplicatesByLength("c:\\");
		
		assertTrue("fileMap should be empty", dupsByLen.isEmpty());
	}
	
	@Test
	public void testByLenSingleDup() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\file1.txt");		
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getName()).thenReturn("file2.txt");
		when(mockedFile2.length()).thenReturn(1L);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\file2.txt");
		
		File mockedFile3 = mock(File.class);
		when(mockedFile3.getName()).thenReturn("file1.txt");
		when(mockedFile3.length()).thenReturn(2L);
		when(mockedFile3.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");		
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile1);
		files.add(mockedFile2);
		files.add(mockedFile3);
		
		when(mockedFileRetriever.getFiles(anyString())).thenReturn(files);

		Map<Long, Set<FileInfo>> dupsByLen = duplicatesSeeker.getDuplicatesByLength("c:\\");
		
		assertTrue("fileMap should contain one entry", dupsByLen.size() == 1);
		Long[] expectedKeys = { 1L };
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile1), new FileInfo(mockedFile2) }};

		for (int d = 0; d < expectedFileInfos.length; d++) {
			Arrays.sort(expectedFileInfos[d]);
		}
		
		int i = 0;
		for (Entry<Long, Set<FileInfo>> entry : dupsByLen.entrySet()) {
			assertEquals("filename on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}

	@Test
	public void testByLenSeveralDups() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\file1.txt");
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getName()).thenReturn("file2.txt");
		when(mockedFile2.length()).thenReturn(1L);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\dir\\file2.txt");
		
		File mockedFile3 = mock(File.class);
		when(mockedFile3.getName()).thenReturn("file3.txt");
		when(mockedFile3.length()).thenReturn(2L);
		when(mockedFile3.getAbsolutePath()).thenReturn("c:\\dir\\file3.txt");
		
		File mockedFile4 = mock(File.class);
		when(mockedFile4.getName()).thenReturn("file4.txt");
		when(mockedFile4.length()).thenReturn(1L);
		when(mockedFile4.getAbsolutePath()).thenReturn("c:\\dir\\another\\file4.txt");
		
		File mockedFile5 = mock(File.class);
		when(mockedFile5.getName()).thenReturn("file5.txt");
		when(mockedFile5.length()).thenReturn(2L);
		when(mockedFile5.getAbsolutePath()).thenReturn("c:\\dir\\another\\file5.txt");
		
		File mockedFile6 = mock(File.class);
		when(mockedFile6.getName()).thenReturn("file6.txt");
		when(mockedFile6.length()).thenReturn(3L);
		when(mockedFile6.getAbsolutePath()).thenReturn("c:\\dir\\another\\file6.txt");
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile1);
		files.add(mockedFile2);
		files.add(mockedFile3);
		files.add(mockedFile4);
		files.add(mockedFile5);
		files.add(mockedFile6);
		
		when(mockedFileRetriever.getFiles(anyString())).thenReturn(files);
		
		Map<Long, Set<FileInfo>> dupsByLen = duplicatesSeeker.getDuplicatesByLength("c:\\");
		
		assertTrue("fileMap should contain two entries", dupsByLen.size() == 2);
		Long[] expectedKeys = { 1L, 2L };
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile1), new FileInfo(mockedFile2), new FileInfo(mockedFile4) },
											{ new FileInfo(mockedFile3), new FileInfo(mockedFile5)}};

		for (int d = 0; d < expectedFileInfos.length; d++) {
			Arrays.sort(expectedFileInfos[d]);
		}
		
		int i = 0;
		for (Entry<Long, Set<FileInfo>> entry : dupsByLen.entrySet()) {
			assertEquals("filename on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}	
}
