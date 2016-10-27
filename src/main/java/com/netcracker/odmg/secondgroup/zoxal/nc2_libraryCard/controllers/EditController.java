package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.controllers;

import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO.*;
import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.model.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

public class EditController extends HttpServlet{

	private RecordManager recordManager;

	public EditController() {
		try {
			recordManager= new RecordManager(new MySQLDAO());
		} catch(SQLException | ClassNotFoundException e) {
			//TODO:logging			
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		if(recordManager == null) {
			//TODO:logging
			showErrorPage(req, res, "recordManager is not initialized");
		}

		String bookTitle = (String)req.getParameter("title");
		if(bookTitle == null) {
			res.sendRedirect(req.getContextPath()+"/app");
		}
		try {
			Record editRecord = recordManager.getRecordByTitle(bookTitle);
			req.setAttribute("record", editRecord);
			req.setAttribute("panelHeader", "Edit record");
		} catch (Exception e) {
			//TODO: logging
			showErrorPage(req, res, "Fault during getting book with title " + bookTitle);
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/add-edit.jsp");
		dispatcher.forward(req, res);
	}	

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		if(recordManager == null) {
			//TODO:logging
			showErrorPage(req, res, "recordManager is null");
		}
		String previousTitle = req.getParameter("previousTitle");
		try {
			Record newRecord = new Record();
			newRecord.setTitle(req.getParameter("bookTitle"));
			newRecord.setAuthor(req.getParameter("bookAuthor"));
			newRecord.setObtainDate(req.getParameter("obtainDate"));
			newRecord.setReturnDate(req.getParameter("returnDate"));
			recordManager.updateRecordByTitle(newRecord, previousTitle);
		} catch(Exception e) {
			// TODO: logging
			showErrorPage(req, res, "Exception during updating book with title "+previousTitle);
		}

		res.sendRedirect(req.getContextPath() + "/app");
	}

	private void showErrorPage(HttpServletRequest req, HttpServletResponse res, String message) throws ServletException, IOException {
		req.setAttribute("errorMessage", message);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/errorPage.jsp");
		dispatcher.forward(req, res);
	}
	public void destroy() {
		try {
			recordManager.destroyDAO();
		} catch (Exception e) {
			//TODO: logging
		}
	}
}