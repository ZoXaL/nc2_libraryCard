package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.controllers;

import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO.*;
import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.model.*;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;

import java.sql.SQLException;

public class DeleteController extends HttpServlet {

	private RecordManager recordManager;

	public DeleteController() {
		try {
			recordManager= new RecordManager(new MySQLDAO());
		} catch(SQLException | ClassNotFoundException e) {
			//TODO:logging
		}		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if(recordManager == null) {
			//TODO:logging
			showErrorPage(req, res, "recordManager is null");
		}

		try {
			recordManager.deleteRecordByTitle(req.getParameter("deleteTitle"));
		} catch(Exception e) {
			// TODO: logging
			showErrorPage(req, res, "Fault during deleting record with title "+req.getParameter("deleteTitle"));
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