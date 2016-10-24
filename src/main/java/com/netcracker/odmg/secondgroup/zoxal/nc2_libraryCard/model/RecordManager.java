package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.model;

import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO.*;

import java.util.ArrayList;


public class RecordManager {
	private DAO dao;

	public RecordManager(DAO dao) {
		this.dao = dao;
	}

	Record getRecordById(int id) throws Exception {
		// TODO: trace log
		return dao.getRecordById(id);
	}
	ArrayList<Record> getAllRecords() throws Exception {
		// TODO: trace log
		return dao.getAllRecords();
	}
	
	int addRecord(Record record) throws Exception {
		// TODO: trace log
		return dao.addRecord(record);
	}
	void updateRecord(Record record) throws Exception {
		// TODO: trace log
		dao.updateRecord(record);
	}
	void deleteRecordById(int id) throws Exception {
		// TODO: trace log
		dao.deleteRecordById(id);
	}
}
