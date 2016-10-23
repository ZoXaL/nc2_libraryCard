package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO;

import java.util.HashMap;
import java.util.ArrayList;
import java.sql.SQLException;

public interface DAO {	
	HashMap<String, String> getRecordById(int id) throws SQLException;
	ArrayList<HashMap<String, String>> getAllRecords() throws SQLException;
	
	int addRecord(HashMap<String, String> record) throws SQLException;
	void updateRecord(HashMap<String, String> record) throws SQLException;
	void deleteRecordById(int id) throws SQLException;
}
