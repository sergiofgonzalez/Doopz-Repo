package org.joolzminer.doopz.services.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.joolzminer.doopz.entities.FileInfo;
import org.joolzminer.doopz.services.FileMapBuilder;
import org.joolzminer.doopz.services.FileMapBuilderImpl;
import org.junit.Before;
import org.junit.Test;


/**
 * The Class FileMapBuilderTest provides the tests for the default implementation of FileMapBuilder:
 * + Partition of the space:
 * - files 		: the list of files uses to build the file map
 * - fileMap	: a map of files indexed by name or length whose value is a set of FileInfo instances
 * 
 * + files 		: null, empty, single, several, same directory, several directories
 * + fileMap	: empty, singleton-key-singleton-value, singleton-key-several-values, 
 * 					several-keys-singleton-values, several-keys-several-values	
 */
public class FileMapBuilderTest {

	private FileMapBuilder fileMapBuilder;
			
	@Before
	public void setUpBefore() {
		fileMapBuilder = new FileMapBuilderImpl();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullFileListByName() {
		fileMapBuilder.buildFileMapByFileName(null);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEmptyListByName() {
		fileMapBuilder.buildFileMapByFileName(new ArrayList<File>());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullFileListByLength() {
		fileMapBuilder.buildFileMapByFileLength(null);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEmptyListByLength() {
		fileMapBuilder.buildFileMapByFileLength(new ArrayList<File>());
	}
		
	
	@Test
	public void testByNameSingleKeySingleValue() {
		File mockedFile = mock(File.class);
		when(mockedFile.getName()).thenReturn("file1.txt");
		when(mockedFile.length()).thenReturn(1L);
		when(mockedFile.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile);
			
		Map<String, Set<FileInfo>> fileMapByName = fileMapBuilder.buildFileMapByFileName(files);
		
		assertTrue("fileMap should have single entry", fileMapByName.size() == 1);
		
		String[] expectedKeys = { "file1.txt" };
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile) }};
		int i = 0;
		for (Entry<String, Set<FileInfo>> entry : fileMapByName.entrySet()) {
			assertEquals("filename on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}
	
	@Test
	public void testByNameSingleKeySeveralValues() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getName()).thenReturn("file1.txt");
		when(mockedFile2.length()).thenReturn(2L);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\dir\\another-dir\\file1.txt");
		
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile1);
		files.add(mockedFile2);
			
		Map<String, Set<FileInfo>> fileMapByName = fileMapBuilder.buildFileMapByFileName(files);
		
		assertTrue("fileMap should have single entry", fileMapByName.size() == 1);
		
		String[] expectedKeys = { "file1.txt" };
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile1), new FileInfo(mockedFile2) }};
		int i = 0;
		for (Entry<String, Set<FileInfo>> entry : fileMapByName.entrySet()) {
			assertEquals("filename on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}
	
	@Test
	public void testByNameSeveralKeysSingleValues() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getName()).thenReturn("file2.txt");
		when(mockedFile2.length()).thenReturn(2L);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\dir\\another-dir\\file2.txt");
		
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile1);
		files.add(mockedFile2);
			
		Map<String, Set<FileInfo>> fileMapByName = fileMapBuilder.buildFileMapByFileName(files);
		
		assertTrue("fileMap should have two entries", fileMapByName.size() == 2);
		
		String[] expectedKeys = { "file1.txt", "file2.txt" };
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile1) },
										  { new FileInfo(mockedFile2) }};
		int i = 0;
		for (Entry<String, Set<FileInfo>> entry : fileMapByName.entrySet()) {
			assertEquals("filename on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}
	
	@Test
	public void testByNameSeveralKeysSeveralValues() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getName()).thenReturn("file1.txt");
		when(mockedFile2.length()).thenReturn(2L);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\dir\\another-dir\\file1.txt");
		
		File mockedFile3 = mock(File.class);		
		when(mockedFile3.getName()).thenReturn("file2.txt");
		when(mockedFile3.length()).thenReturn(2L);
		when(mockedFile3.getAbsolutePath()).thenReturn("c:\\file2.txt");	
		
		File mockedFile4 = mock(File.class);		
		when(mockedFile4.getName()).thenReturn("file2.txt");
		when(mockedFile4.length()).thenReturn(2L);
		when(mockedFile4.getAbsolutePath()).thenReturn("c:\\dir\\file2.txt");
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile1);
		files.add(mockedFile2);
		files.add(mockedFile3);
		files.add(mockedFile4);
			
		Map<String, Set<FileInfo>> fileMapByName = fileMapBuilder.buildFileMapByFileName(files);
		
		assertTrue("fileMap should have two entries", fileMapByName.size() == 2);
		
		String[] expectedKeys = { "file1.txt", "file2.txt" };
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile1), new FileInfo(mockedFile2) },
										  { new FileInfo(mockedFile3), new FileInfo(mockedFile4) }};
		
		for (int d = 0; d < expectedFileInfos.length; d++) {
			Arrays.sort(expectedFileInfos[d]);
		}
		
		int i = 0;
		for (Entry<String, Set<FileInfo>> entry : fileMapByName.entrySet()) {
			assertEquals("filename on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}
	
	@Test
	public void testByNameSeveralKeysMixedValues() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getName()).thenReturn("file1.txt");
		when(mockedFile2.length()).thenReturn(2L);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\dir\\another-dir\\file1.txt");
		
		File mockedFile3 = mock(File.class);		
		when(mockedFile3.getName()).thenReturn("file2.txt");
		when(mockedFile3.length()).thenReturn(2L);
		when(mockedFile3.getAbsolutePath()).thenReturn("c:\\file2.txt");	
		
		File mockedFile4 = mock(File.class);		
		when(mockedFile4.getName()).thenReturn("file2.txt");
		when(mockedFile4.length()).thenReturn(2L);
		when(mockedFile4.getAbsolutePath()).thenReturn("c:\\dir\\file2.txt");
		
		File mockedFile5 = mock(File.class);		
		when(mockedFile5.getName()).thenReturn("file3.txt");
		when(mockedFile5.length()).thenReturn(3L);
		when(mockedFile5.getAbsolutePath()).thenReturn("c:\\dir\\file3.txt");
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile1);
		files.add(mockedFile2);
		files.add(mockedFile3);
		files.add(mockedFile4);
		files.add(mockedFile5);
			
		Map<String, Set<FileInfo>> fileMapByName = fileMapBuilder.buildFileMapByFileName(files);
		
		assertTrue("fileMap should have three entries", fileMapByName.size() == 3);
		
		String[] expectedKeys = { "file1.txt", "file2.txt", "file3.txt" };
		Arrays.sort(expectedKeys);
		
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile1), new FileInfo(mockedFile2) },
										  { new FileInfo(mockedFile3), new FileInfo(mockedFile4) },
										  { new FileInfo(mockedFile5) }};
		
		for (int d = 0; d < expectedFileInfos.length; d++) {
			Arrays.sort(expectedFileInfos[d]);
		}
		
		int i = 0;
		for (Entry<String, Set<FileInfo>> entry : fileMapByName.entrySet()) {
			assertEquals("filename on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}
	
	@Test
	public void testByLengthSingleKeySingleValue() {
		File mockedFile = mock(File.class);
		when(mockedFile.getName()).thenReturn("file1.txt");
		when(mockedFile.length()).thenReturn(1L);
		when(mockedFile.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile);
			
		Map<Long, Set<FileInfo>> fileMapByLen = fileMapBuilder.buildFileMapByFileLength(files);
		
		assertTrue("fileMap should have single entry", fileMapByLen.size() == 1);
		
		Long[] expectedKeys = { 1L };
		
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile) }};
		
		int i = 0;
		for (Entry<Long, Set<FileInfo>> entry : fileMapByLen.entrySet()) {
			assertEquals("length on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}
	
	@Test
	public void testByLengthSingleKeySeveralValues() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getName()).thenReturn("file2.txt");
		when(mockedFile2.length()).thenReturn(1L);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\dir\\another-dir\\file1.txt");
		
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile1);
		files.add(mockedFile2);
			
		Map<Long, Set<FileInfo>> fileMapByLen = fileMapBuilder.buildFileMapByFileLength(files);
		
		assertTrue("fileMap should have single entry", fileMapByLen.size() == 1);
		
		Long[] expectedKeys = { 1L };
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile1), new FileInfo(mockedFile2) }};
		Arrays.sort(expectedFileInfos);
		
		int i = 0;
		for (Entry<Long, Set<FileInfo>> entry : fileMapByLen.entrySet()) {
			assertEquals("filelength on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}
	
	@Test
	public void testByLenSeveralKeysSingleValues() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getName()).thenReturn("file2.txt");
		when(mockedFile2.length()).thenReturn(2L);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\dir\\another-dir\\file2.txt");
		
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile1);
		files.add(mockedFile2);
			
		Map<Long, Set<FileInfo>> fileMapByLen = fileMapBuilder.buildFileMapByFileLength(files);
		
		assertTrue("fileMap should have two entries", fileMapByLen.size() == 2);
		
		Long[] expectedKeys = { 1L, 2L };
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile1) },
										  { new FileInfo(mockedFile2) }};
		int i = 0;
		for (Entry<Long, Set<FileInfo>> entry : fileMapByLen.entrySet()) {
			assertEquals("fileLength on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}	
	
	@Test
	public void testByLenSeveralKeysSeveralValues() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getName()).thenReturn("file2.txt");
		when(mockedFile2.length()).thenReturn(1L);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\dir\\another-dir\\file4.txt");
		
		File mockedFile3 = mock(File.class);		
		when(mockedFile3.getName()).thenReturn("file3.txt");
		when(mockedFile3.length()).thenReturn(2L);
		when(mockedFile3.getAbsolutePath()).thenReturn("c:\\file3.txt");	
		
		File mockedFile4 = mock(File.class);		
		when(mockedFile4.getName()).thenReturn("file4.txt");
		when(mockedFile4.length()).thenReturn(2L);
		when(mockedFile4.getAbsolutePath()).thenReturn("c:\\dir\\file4.txt");
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile1);
		files.add(mockedFile2);
		files.add(mockedFile3);
		files.add(mockedFile4);
			
		Map<Long, Set<FileInfo>> fileMapByLen = fileMapBuilder.buildFileMapByFileLength(files);
		
		assertTrue("fileMap should have two entries", fileMapByLen.size() == 2);
		
		Long[] expectedKeys = { 1L, 2L };
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile1), new FileInfo(mockedFile2) },
										  { new FileInfo(mockedFile3), new FileInfo(mockedFile4) }};
		
		for (int d = 0; d < expectedFileInfos.length; d++) {
			Arrays.sort(expectedFileInfos[d]);
		}
		
		int i = 0;
		for (Entry<Long, Set<FileInfo>> entry : fileMapByLen.entrySet()) {
			assertEquals("fileLength on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}
	
	@Test
	public void testByLengthSeveralKeysMixedValues() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\dir\\file1.txt");
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getName()).thenReturn("file2.txt");
		when(mockedFile2.length()).thenReturn(1L);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\dir\\another-dir\\file2.txt");
		
		File mockedFile3 = mock(File.class);		
		when(mockedFile3.getName()).thenReturn("file3.txt");
		when(mockedFile3.length()).thenReturn(2L);
		when(mockedFile3.getAbsolutePath()).thenReturn("c:\\file3.txt");	
		
		File mockedFile4 = mock(File.class);		
		when(mockedFile4.getName()).thenReturn("file4.txt");
		when(mockedFile4.length()).thenReturn(2L);
		when(mockedFile4.getAbsolutePath()).thenReturn("c:\\dir\\file4.txt");
		
		File mockedFile5 = mock(File.class);		
		when(mockedFile5.getName()).thenReturn("file5.txt");
		when(mockedFile5.length()).thenReturn(3L);
		when(mockedFile5.getAbsolutePath()).thenReturn("c:\\dir\\file5.txt");
		
		List<File> files = new ArrayList<File>();
		files.add(mockedFile1);
		files.add(mockedFile2);
		files.add(mockedFile3);
		files.add(mockedFile4);
		files.add(mockedFile5);
			
		Map<Long, Set<FileInfo>> fileMapByLen = fileMapBuilder.buildFileMapByFileLength(files);
		
		assertTrue("fileMap should have three entries", fileMapByLen.size() == 3);
		
		Long[] expectedKeys = { 1L, 2L, 3L };
		Arrays.sort(expectedKeys);
		
		FileInfo[][] expectedFileInfos = {{ new FileInfo(mockedFile1), new FileInfo(mockedFile2) },
										  { new FileInfo(mockedFile3), new FileInfo(mockedFile4) },
										  { new FileInfo(mockedFile5) }};
		
		for (int d = 0; d < expectedFileInfos.length; d++) {
			Arrays.sort(expectedFileInfos[d]);
		}
		
		int i = 0;
		for (Entry<Long, Set<FileInfo>> entry : fileMapByLen.entrySet()) {
			assertEquals("filename on key should match", expectedKeys[i], entry.getKey());
			int j = 0;
			for (FileInfo fileInfo : entry.getValue()) {
				assertEquals("FileInfo on value should match", expectedFileInfos[i][j++], fileInfo);
			}
			i++;
		}
	}	
}
