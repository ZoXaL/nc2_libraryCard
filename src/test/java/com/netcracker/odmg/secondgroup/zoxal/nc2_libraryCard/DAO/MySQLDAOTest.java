package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.BeforeClass;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@RunWith(JUnitParamsRunner.class)
public class MySQLDAOTest {
	private static DAO dao;
	
	private static HashMap<String, String> oneRecord;
	private static HashMap<String, String> changedRecord;
	private static int changableRecordId;
	
	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException {
		dao = MySQLDAO.getInstance();
		
		changableRecordId = 666;
		
		oneRecord = new HashMap<>();
		oneRecord.put("bookId", String.valueOf(changableRecordId));
		oneRecord.put("bookTitle", "TestTitle");
		oneRecord.put("bookAuthor", "Jedi of JUnit");
		oneRecord.put("obtainDate", "1997-10-29");
		oneRecord.put("returnDate", "2100-01-01");
		
		changedRecord = new HashMap<>();
		changedRecord.put("bookId", String.valueOf(changableRecordId));
		changedRecord.put("bookTitle", "changedTestTitle");
		changedRecord.put("bookAuthor", "Jedi of Changes");
		changedRecord.put("obtainDate", "2000-01-01");
		changedRecord.put("returnDate", "2100-01-01");
	}
	
	@Parameters
	public Object[] recordsExample() {
		return new Object[] {
			new Object[] {1, "Thinking in Java", "Bruce Eckel", "2016-09-16", "2016-11-16"},
			new Object[] {2, "Mastring Regular Expressions", "Jeffrey Friedl", "2016-10-02", "2017-01-02"}
		};
	}
	@Parameters
	public Object[] oneRecordExample() {
		return new Object[] {666, "TestTitle", "Jedi of JUnit", "1997-10-29", "2100-01-01"};
	}
	
	@Test
	public void ok() {
		assertTrue(true);
	}
	
	@Test
	@Parameters(method = "recordsExample")
	public void getRecordByIdTest(int bookId, String bookTitle, String bookAuthor,
									String obtainDate, String returnDate) throws SQLException {
		HashMap<String, String> result = dao.getRecordById(bookId);
		assertEquals(result.get("bookTitle"), bookTitle);
		assertEquals(result.get("bookAuthor"), bookAuthor);
		assertEquals(result.get("obtainDate"), obtainDate);
		assertEquals(result.get("returnDate"), returnDate);
	}
	
	/*
	@Test
	@Parameters(method = "recordsExample")
	public void getAllRecordsTest(int bookId, String bookTitle, String bookAuthor,
			String obtainDate, String returnDate) throws SQLException {
		ArrayList<HashMap<String, String>> result = dao.getAllRecords();
		HashMap<String, String> testMap = new HashMap<String,String>();
		testMap.put("id", String.valueOf(id));
		testMap.put("bookTitle", bookTitle);
		testMap.put("bookAuthor", bookAuthor);
		testMap.put("obtainDate", obtainDate);
		testMap.put("returnDate", returnDate);
		assertTrue(result.contains(testMap));
	}
	
	@Test
	public void addRecordTest() throws SQLException {
		dao.addRecord(oneRecord);
		
	}
	@Test
	public void updateRecordTest() throws SQLException{
		dao.updateRecord(changedRecord);
	}
	@Test
	public void deleteRecordByIdTest() throws SQLException{
		dao.deleteRecordById(changableRecordId);
	}*/

}
