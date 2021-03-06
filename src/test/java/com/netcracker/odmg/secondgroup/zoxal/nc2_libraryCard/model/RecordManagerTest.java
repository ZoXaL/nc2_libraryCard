package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.model;

import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO.*;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


@RunWith(JUnitParamsRunner.class)
public class RecordManagerTest {
	/*
	 Вопрос.
	 Проверять в тестах работу с базой данных — это нормально?
	 Я ещё плохо разбираюсь в тестах, но насколько я знаю, это уже тянет на интеграционные тесты.
	 Как тогда лучше тестировать, если юнит тесты на работу с БД — это плохой вариант?
	 */
	private static RecordManager recordManager;
	
	private static Record oneRecord;
	private static Record changedRecord;
	
	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException {
		recordManager = new RecordManager(new MySQLDAO());		
		
		oneRecord = new Record();
		oneRecord.setTitle("TestTitle");
		oneRecord.setAuthor("Jedi of JUnit");
		oneRecord.setObtainDate("1997-10-29");
		oneRecord.setReturnDate("2100-01-01");
		
		changedRecord = new Record();
		changedRecord.setTitle("changedTestTitle");
		changedRecord.setAuthor("Jedi of Changes");
		changedRecord.setObtainDate("2000-01-01");
		changedRecord.setReturnDate("2100-01-01");
	}

	@AfterClass
	public static void destroy() throws Exception  {
		recordManager.destroyDAO();
	}
	
	@Parameters
	public Object[] recordsExample() {
		return new Object[] {
			new Object[] {1, "Thinking in Java", "Bruce Eckel", "2016-09-16", "2016-11-16"},
			new Object[] {2, "Mastring Regular Expressions", "Jeffrey Friedl", "2016-10-02", "2017-01-02"}
		};
	}
	
	@Test
	@Parameters(method = "recordsExample")
	public void getRecordByIdTest(int bookId, String bookTitle, String bookAuthor,
									String obtainDate, String returnDate) throws Exception {
		Record result = recordManager.getRecordById(bookId);
		assertEquals(result.getTitle(), bookTitle);
		assertEquals(result.getAuthor(), bookAuthor);
		assertEquals(result.getObtainDate(), obtainDate);
		assertEquals(result.getReturnDate(), returnDate);
	}

	@Test
	@Parameters(method = "recordsExample")
	public void getRecordByTitleTest(int bookId, String bookTitle, String bookAuthor,
									String obtainDate, String returnDate) throws Exception {
		Record result = recordManager.getRecordByTitle(bookTitle);
		assertEquals(result.getTitle(), bookTitle);
		assertEquals(result.getAuthor(), bookAuthor);
		assertEquals(result.getObtainDate(), obtainDate);
		assertEquals(result.getReturnDate(), returnDate);
	}
	
	
	@Test
	@Parameters(method = "recordsExample")
	public void getAllRecordsTest(int bookId, String bookTitle, String bookAuthor,
									String obtainDate, String returnDate) throws Exception {
		ArrayList<Record> result = recordManager.getAllRecords();
		Record testRecord = new Record();
		testRecord.setId(bookId);
		testRecord.setTitle(bookTitle);
		testRecord.setAuthor(bookAuthor);
		testRecord.setObtainDate(obtainDate);
		testRecord.setReturnDate(returnDate);
		assertTrue(result.contains(testRecord));
	}
	
	// Здесь совсем плохо, но ничего другого я не придумал, подскажите, как лучше.
	// Основная проблема (кроме нарушения главной идеи юнит-тестирования тестировать \
	// за один раз что-то одно) — при ошибке удаления/обновления тестовая запись остаётся в базе.
	@Test
	public void addUpdateDeleteByIdRecordTest() throws Exception {
		// FIXME: separate tests
		int changedRecordId = recordManager.addRecord(oneRecord);
		changedRecord.setId(changedRecordId);
		recordManager.updateRecordById(changedRecord);
		recordManager.deleteRecordById(changedRecordId);		
	}

	@Test
	public void addUpdateDeleteByTitleRecordTest() throws Exception {
		// FIXME: separate tests
		recordManager.addRecord(oneRecord);
		recordManager.updateRecordByTitle(changedRecord, oneRecord.getTitle());
		recordManager.deleteRecordByTitle(changedRecord.getTitle());		
	}
}
