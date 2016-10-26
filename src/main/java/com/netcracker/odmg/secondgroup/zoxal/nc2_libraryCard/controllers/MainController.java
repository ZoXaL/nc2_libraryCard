package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.controllers;


import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO.*;
import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.model.*;

import java.util.ArrayList;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.Writer;

public class MainController extends HttpServlet {

	private RecordManager recordManager;

	public MainController() {
		try {
			recordManager= new RecordManager(MySQLDAO.getInstance());
		} catch(SQLException | ClassNotFoundException e) {
			//TODO:logging
		}
		
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if(recordManager == null) {
			//TODO:logging
			showErrorPage(req, res);
		}

		
		ArrayList<Record> recordsArray;
		try {
			recordsArray = recordManager.getAllRecords();
			req.setAttribute("records", recordsArray);
		} catch(Exception e) {
			//TODO: logging
			showErrorPage(req, res);
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/main.jsp");
		dispatcher.forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.doGet(req, res);
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