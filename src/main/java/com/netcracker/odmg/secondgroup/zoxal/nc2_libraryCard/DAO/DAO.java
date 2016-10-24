package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO;

import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.model.Record;

import java.util.HashMap;
import java.util.ArrayList;
import java.sql.SQLException;

public interface DAO {	
	Record getRecordById(int id) throws SQLException;
	ArrayList<Record> getAllRecords() throws SQLException;
	
	int addRecord(Record record) throws SQLException;
	void updateRecord(Record record) throws SQLException;
	void deleteRecordById(int id) throws SQLException;
}
