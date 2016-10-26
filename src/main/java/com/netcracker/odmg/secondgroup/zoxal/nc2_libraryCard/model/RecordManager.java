package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.model;

import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO.*;

import java.util.ArrayList;


public class RecordManager {
	private DAO dao;

	public RecordManager(DAO dao) {
		this.dao = dao;
	}

	public Record getRecordById(int id) throws Exception {
		// TODO: trace log
		return dao.getRecordById(id);
	}
	public Record getRecordByTitle(String bookTitle) throws Exception {
		// TODO: trace log
		return dao.getRecordByTitle(bookTitle);
	}
	public ArrayList<Record> getAllRecords() throws Exception {
		// TODO: trace log
		return dao.getAllRecords();
	}	
	public int addRecord(Record record) throws Exception {
		// TODO: trace log
		return dao.addRecord(record);
	}
	public void updateRecordById(Record record) throws Exception {
		// TODO: trace log
		dao.updateRecordById(record);
	}
	public void updateRecordByTitle(Record record, String title) throws Exception {
		// TODO: trace log
		dao.updateRecordByTitle(record, title);
	}
	public void deleteRecordById(int id) throws Exception {
		// TODO: trace log
		dao.deleteRecordById(id);
	}
	public void deleteRecordByTitle(String title) throws Exception {
		// TODO: trace log
		dao.deleteRecordByTitle(title);
	}

	public void destroyDAO() throws Exception{
		if(!dao.isDestroyed()) {
			dao.destroy();
		}
	}
}
