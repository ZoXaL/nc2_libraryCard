package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.controllers;

import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO.*;
import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.model.*;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;

import javax.servlet.RequestDispatcher;

import java.sql.SQLException;

public class AddController extends HttpServlet {

	private RecordManager recordManager;

	public AddController() {
		try {
			recordManager= new RecordManager(MySQLDAO.getInstance());
		} catch(SQLException | ClassNotFoundException e) {
			//TODO:logging
		}
		
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setAttribute("tableHeader", "Add new record");
		req.setAttribute("formAction", req.getContextPath() + "/app/add");

		RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/add-edit.jsp");
		dispatcher.forward(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if(recordManager == null) {
			//TODO:logging
			showErrorPage(req, res);
		}

		try {
			Record newRecord = new Record();
			newRecord.setTitle(req.getParameter("bookTitle"));
			newRecord.setAuthor(req.getParameter("bookAuthor"));
			newRecord.setObtainDate(req.getParameter("obtainDate"));
			newRecord.setReturnDate(req.getParameter("returnDate"));
			recordManager.addRecord(newRecord);
		} catch(Exception e) {
			// TODO: logging
			showErrorPage(req, res);
		}

		res.sendRedirect(req.getContextPath() + "/app");
	}

	private void showErrorPage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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