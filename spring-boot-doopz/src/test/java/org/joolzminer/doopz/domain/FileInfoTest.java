package org.joolzminer.doopz.domain;

import java.io.File;

import org.joolzminer.doopz.domain.FileInfo;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileInfoTest {

	@Test
	public void testConstructor() {
		File mockedFile = mock(File.class);
		when(mockedFile.getAbsolutePath()).thenReturn("c:\\this\\is\\the\\absolute\\path\\file.txt");
		when(mockedFile.getName()).thenReturn("file.txt");
		when(mockedFile.length()).thenReturn(1024L);
		
		FileInfo fileInfo = new FileInfo(mockedFile);
		assertThat(fileInfo.getFilePath(), is(equalTo("c:\\this\\is\\the\\absolute\\path")));
		assertThat(fileInfo.getFileName(), is(equalTo("file.txt")));
		assertThat(fileInfo.getFileLength(), is(equalTo(1024L)));
		assertThat(fileInfo.getCrc32(), is(equalTo(null)));		
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
		
		assertThat(hashCode, is(equalTo(hashCode2)));
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
		
		assertThat(fileInfo, is(equalTo(fileInfo2)));
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
		
		assertThat(fileInfo1.compareTo(fileInfo2), is(lessThan(0)));
		assertThat(fileInfo2.compareTo(fileInfo1), is(greaterThan(0)));
		assertThat(fileInfo1.compareTo(fileInfo3), is(equalTo(0)));
		assertThat(fileInfo3.compareTo(fileInfo1), is(equalTo(0)));
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
	
		assertThat(fileInfo1.compareTo(fileInfo2), is(lessThan(0)));
		assertThat(fileInfo2.compareTo(fileInfo1), is(greaterThan(0)));
		assertThat(fileInfo1.compareTo(fileInfo3), is(equalTo(0)));
		assertThat(fileInfo3.compareTo(fileInfo1), is(equalTo(0)));		
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
	
		assertThat(fileInfo1.compareTo(fileInfo2), is(lessThan(0)));
		assertThat(fileInfo2.compareTo(fileInfo1), is(greaterThan(0)));
		assertThat(fileInfo1.compareTo(fileInfo3), is(equalTo(0)));
		assertThat(fileInfo3.compareTo(fileInfo1), is(equalTo(0)));
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
	
		assertThat(fileInfo1.compareTo(fileInfo2), is(equalTo(0)));
		assertThat(fileInfo2.compareTo(fileInfo1), is(equalTo(0)));
	}	
}
