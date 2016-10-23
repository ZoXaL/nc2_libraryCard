package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO;

import java.util.ArrayList;
import java.util.HashMap;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class MySQLDAO implements DAO {
	private final String MYSQL_CONNECTOR_CLASS = "org.gjt.mm.mysql.Driver";
	
	private final String DBPATH = "jdbc:mysql://localhost/nc2_libraryCard";
	private final String TABLE_NAME = "libraryCard";
	private final String USER_LOGIN = "nc2";
	private final String USER_PASSWORD = "nc2_libraryCard";
	
	private static MySQLDAO singleton;
	private Connection connection;
	
	private final String findByIdQuery = "SELECT * FROM "+TABLE_NAME+" WHERE bookId = ?";
	
	public static MySQLDAO getInstance() throws SQLException, ClassNotFoundException{
		if(singleton != null) {
			return singleton;
		}
		return new MySQLDAO();
	}
	
	private MySQLDAO() throws SQLException, ClassNotFoundException{
		try {
			Class.forName(MYSQL_CONNECTOR_CLASS);
			try {
				connection = DriverManager.getConnection(DBPATH, USER_LOGIN, USER_PASSWORD);				
			}catch (SQLException cause) {
				// Вопрос
				// Стоит ли в message исключения писать, с какими 
				// значениями была попытка подключиться (путь к базе, логин и пароль)?
				// И вообще, стоит ли так оборачивать исключения? Может, лучше вообще их
				// не отлавливать и прямо пробрасывать? Ведь никакой новой
				// информации в моих сообщениях нет. 
				throw new SQLException("Cannot establish connection with dababase.", cause);
			}
			
		} catch (ClassNotFoundException cause) {
			throw new ClassNotFoundException("Cannot find mysql database driver.", cause);
		}	
	}

	public HashMap<String, String> getRecordById(int bookId) throws SQLException {
		HashMap<String, String> result = new HashMap<>();
		
		try (PreparedStatement statement = connection.prepareStatement(findByIdQuery)) {
			statement.setInt(1, bookId);
			ResultSet resultSet = statement.executeQuery();			
			boolean isFound = resultSet.next();
			if (isFound) {
				
				result.put("bookId", String.valueOf(bookId));
				result.put("bookTitle", resultSet.getString("bookTitle"));
				result.put("bookAuthor", resultSet.getString("bookAuthor"));
				result.put("obtainDate", resultSet.getString("obtainDate"));
				result.put("returnDate", resultSet.getString("returnDate"));
			} else {
				throw new SQLException("There is no record with id = "+bookId);
			}			
		}catch (SQLException cause) {
			// TODO: logging
			throw cause;
		}		
		return result;
	}

	public ArrayList<HashMap<String, String>> getAllRecords() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addRecord(HashMap<String, String> record) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void updateRecord(HashMap<String, String> record) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void deleteRecordById(int id) throws SQLException {
		// TODO Auto-generated method stub

	}

}
