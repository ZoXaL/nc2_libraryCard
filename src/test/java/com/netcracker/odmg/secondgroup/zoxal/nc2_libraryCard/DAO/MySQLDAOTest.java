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
	/*
	 Вопрос.
	 Проверять в тестах работу с базой данных — это нормально?
	 Я ещё плохо разбираюсь в тестах, но насколько я знаю, это уже тянет на интеграционные тесты.
	 Как тогда лучше тестировать, если юнит тесты на работу с БД — это плохой вариант?
	 */
	private static DAO dao;
	
	private static HashMap<String, String> oneRecord;
	private static HashMap<String, String> changedRecord;
	private static int changedRecordId;
	
	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException {
		dao = MySQLDAO.getInstance();
		
		
		oneRecord = new HashMap<>();
		oneRecord.put("bookTitle", "TestTitle");
		oneRecord.put("bookAuthor", "Jedi of JUnit");
		oneRecord.put("obtainDate", "1997-10-29");
		oneRecord.put("returnDate", "2100-01-01");
		
		changedRecord = new HashMap<>();
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
	
	
	@Test
	@Parameters(method = "recordsExample")
	public void getAllRecordsTest(int bookId, String bookTitle, String bookAuthor,
			String obtainDate, String returnDate) throws SQLException {
		ArrayList<HashMap<String, String>> result = dao.getAllRecords();
		HashMap<String, String> testMap = new HashMap<String,String>();
		testMap.put("bookId", String.valueOf(bookId));
		testMap.put("bookTitle", bookTitle);
		testMap.put("bookAuthor", bookAuthor);
		testMap.put("obtainDate", obtainDate);
		testMap.put("returnDate", returnDate);
		assertTrue(result.contains(testMap));
	}
	
	// Здесь совсем плохо, но ничего другого я не придумал, подскажите, как лучше.
	// Основная проблема (кроме нарушения главной идии юнит-тестирования) — при
	// ошибке удаления/обновления тестовая запись остаётся в базе.
	@Test
	public void addUpdateDeleteRecordTest() throws SQLException {
		// FIXME: separate tests
		changedRecordId = dao.addRecord(oneRecord);
		changedRecord.put("bookId", String.valueOf(changedRecordId));
		dao.updateRecord(changedRecord);
		dao.deleteRecordById(changedRecordId);		
	}

}
