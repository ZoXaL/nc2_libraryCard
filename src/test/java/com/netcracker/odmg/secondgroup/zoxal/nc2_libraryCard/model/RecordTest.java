package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.model;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class RecordTest {
	private final int recordIdExample = 5;
	private final String recordTitleExample = "The best Record ever";
	private final String recordAuthorExample = "Me";	
	private final String obtainDateExample = "2016-05-05";
	private final String returnDateExample = "2016-07-05";
	private Record testRecord;
	
	@Before
	public void init() {
		testRecord = new Record();
	}	
	
	@Test
	public void setIdTest() {
		testRecord.setId(recordIdExample);
		assertEquals(testRecord.getId(), recordIdExample);
	}
	@Test
	public void setTitleTest() {
		testRecord.setTitle(recordTitleExample);
		assertEquals(testRecord.getTitle(), recordTitleExample);
	}
	@Test
	public void setAuthorTest() {
		testRecord.setAuthor(recordAuthorExample);
		assertEquals(testRecord.getAuthor(), recordAuthorExample);
	}
	@Test
	public void setObtainDateTest() {
		testRecord.setObtainDate(obtainDateExample);
		assertEquals(testRecord.getObtainDate(), obtainDateExample);
	}
	@Test
	public void setReturnDateTest() {
		testRecord.setReturnDate(returnDateExample);
		assertEquals(testRecord.getReturnDate(), returnDateExample);
	}
	
}
