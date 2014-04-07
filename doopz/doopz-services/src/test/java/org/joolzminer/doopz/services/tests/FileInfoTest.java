package org.joolzminer.doopz.services.tests;

import java.io.File;

import org.joolzminer.doopz.entities.FileInfo;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * The Class FileInfoTest tests FileInfo domain model entity class.
 * Usually, domain model classes do not need tests, but FileInfo includes comparable so it makes sense.
 */
public class FileInfoTest {
	
	@Before
	public void setUpBefore() {
	}
	
	@Test
	public void testConstructor() {
		File mockedFile = mock(File.class);
		when(mockedFile.getAbsolutePath()).thenReturn("c:\\this\\is\\the\\absolute\\path\\file.txt");
		when(mockedFile.getName()).thenReturn("file.txt");
		when(mockedFile.length()).thenReturn(1024L);

		FileInfo fileInfo = new FileInfo(mockedFile);
		
		assertEquals("filePath must match",	fileInfo.getFilePath(), "c:\\this\\is\\the\\absolute\\path");
		assertEquals("fileName must match",	fileInfo.getFileName(), "file.txt");
		assertEquals("fileLength must match", fileInfo.getFileLength(), Long.valueOf(1024L));
		assertNull("crc32 must be null on creation", fileInfo.getCrc32());		
	}
	
	@Test
	public void testHashCodeIgnoresCrc32() {
		File mockedFile = mock(File.class);
		when(mockedFile.getAbsolutePath()).thenReturn("c:\\this\\is\\the\\absolute\\path\\file.txt");
		when(mockedFile.getName()).thenReturn("file.txt");
		when(mockedFile.length()).thenReturn(1024L);
		
		FileInfo fileInfo = new FileInfo(mockedFile);
		long hashCode = fileInfo.hashCode();
		
		fileInfo.setCrc32(1234L);
		long hashCode2 = fileInfo.hashCode();
		
		assertEquals("hashCodes must match", hashCode, hashCode2);
	}
	
	@Test
	public void testEqualsIgnoresCrc32() {
		File mockedFile = mock(File.class);
		when(mockedFile.getAbsolutePath()).thenReturn("c:\\this\\is\\the\\absolute\\path\\file.txt");
		when(mockedFile.getName()).thenReturn("file.txt");
		when(mockedFile.length()).thenReturn(1024L);		
		
		FileInfo fileInfo = new FileInfo(mockedFile);
		FileInfo fileInfo2 = new FileInfo(mockedFile);
		fileInfo2.setCrc32(1024L);
		
		
		assertEquals("fileInfos must match", fileInfo, fileInfo2);
	}	
	
	@Test
	public void testCompareNameIsFirst() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getAbsolutePath()).thenReturn("d:\\file1.txt");
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\file2.txt");
		when(mockedFile2.getName()).thenReturn("file2.txt");
		when(mockedFile2.length()).thenReturn(2L);
		
		FileInfo fileInfo1 = new FileInfo(mockedFile1);
		FileInfo fileInfo2 = new FileInfo(mockedFile2);
		FileInfo fileInfo3 = new FileInfo(mockedFile1);
		
		assertTrue("fileInfo1 should be less than fileInfo2", fileInfo1.compareTo(fileInfo2) < 0);
		assertTrue("fileInfo2 should be greater than fileInfo1", fileInfo2.compareTo(fileInfo1) > 0);
		assertTrue("fileInfo1 should match fileInfo3", fileInfo1.compareTo(fileInfo3) == 0);
		assertTrue("fileInfo3 should match fileInfo1", fileInfo3.compareTo(fileInfo1) == 0);
	}
	
	@Test
	public void testComparePathIsSecond() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\file1.txt");
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(2L);
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getAbsolutePath()).thenReturn("d:\\file1.txt");
		when(mockedFile2.getName()).thenReturn("file1.txt");
		when(mockedFile2.length()).thenReturn(1L);
		
		FileInfo fileInfo1 = new FileInfo(mockedFile1);
		FileInfo fileInfo2 = new FileInfo(mockedFile2);
		FileInfo fileInfo3 = new FileInfo(mockedFile1);
		
		assertTrue("fileInfo1 should be less than fileInfo2", fileInfo1.compareTo(fileInfo2) < 0);
		assertTrue("fileInfo2 should be greater than fileInfo1", fileInfo2.compareTo(fileInfo1) > 0);
		assertTrue("fileInfo1 should match fileInfo3", fileInfo1.compareTo(fileInfo3) == 0);
		assertTrue("fileInfo3 should match fileInfo1", fileInfo3.compareTo(fileInfo1) == 0);
	}
	
	@Test
	public void testComparelengthIsThird() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\file1.txt");
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		
		File mockedFile2 = mock(File.class);
		when(mockedFile2.getAbsolutePath()).thenReturn("c:\\file1.txt");
		when(mockedFile2.getName()).thenReturn("file1.txt");
		when(mockedFile2.length()).thenReturn(2L);
		
		FileInfo fileInfo1 = new FileInfo(mockedFile1);
		FileInfo fileInfo2 = new FileInfo(mockedFile2);
		FileInfo fileInfo3 = new FileInfo(mockedFile1);
		
		assertTrue("fileInfo1 should be less than fileInfo2", fileInfo1.compareTo(fileInfo2) < 0);
		assertTrue("fileInfo2 should be greater than fileInfo1", fileInfo2.compareTo(fileInfo1) > 0);
		assertTrue("fileInfo1 should match fileInfo3", fileInfo1.compareTo(fileInfo3) == 0);
		assertTrue("fileInfo3 should match fileInfo1", fileInfo3.compareTo(fileInfo1) == 0);
	}	
	
	@Test
	public void testCompareCrcIsIgnored() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\file1.txt");
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		
		
		FileInfo fileInfo1 = new FileInfo(mockedFile1);
		FileInfo fileInfo2 = new FileInfo(mockedFile1);
		fileInfo2.setCrc32(1L);
		
		assertTrue("fileInfo1 should match fileInfo2", fileInfo1.compareTo(fileInfo2) == 0);
		assertTrue("fileInfo2 should match fileInfo1", fileInfo2.compareTo(fileInfo1) == 0);
	}	
	
	@Test
	public void testToString() {
		File mockedFile1 = mock(File.class);
		when(mockedFile1.getAbsolutePath()).thenReturn("c:\\file1.txt");
		when(mockedFile1.getName()).thenReturn("file1.txt");
		when(mockedFile1.length()).thenReturn(1L);
		
		
		FileInfo fileInfo1 = new FileInfo(mockedFile1);
		fileInfo1.setCrc32(1L);
		
		String expectedToString = "FileInfo [filePath=c:, fileName=file1.txt, fileLength=1, crc32=1]";
		assertEquals("toString must match", expectedToString, fileInfo1.toString());
		
	}
}
