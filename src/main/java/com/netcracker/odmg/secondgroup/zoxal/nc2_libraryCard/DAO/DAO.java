package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO;

import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.model.Record;

import java.util.HashMap;
import java.util.ArrayList;
import java.sql.SQLException;

public interface DAO {	
	//Не уверен насчёт типа исключений
	Record getRecordById(int id) throws Exception;
	Record getRecordByTitle(String title) throws Exception;
	ArrayList<Record> getAllRecords() throws Exception;
	
	int addRecord(Record record) throws Exception;

	void updateRecordById(Record record) throws Exception;
	void updateRecordByTitle(Record record, String title) throws Exception;

	void deleteRecordById(int id) throws Exception;
	void deleteRecordByTitle(String title) throws Exception;

	void destroy() throws Exception;
	boolean isDestroyed();
}
