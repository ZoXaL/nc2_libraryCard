package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO;

import java.util.ArrayList;
import java.util.HashMap;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
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
	/*
	  Вопрос
	  Может, стоит сразу делать поля типа PreparedStatement?
	 */
	private final String findByIdQuery = "SELECT * FROM "+TABLE_NAME+" WHERE bookId = ?";
	private final String findHundredRecordsQuery = "SELECT * FROM "+TABLE_NAME+" LIMIT 100";
	private final String insertRecordQuery = "INSERT INTO "+TABLE_NAME+"(`bookTitle`, `bookAuthor`, `obtainDate`, `returnDate`) VALUES (?, ?, ?, ?)";
	private final String updateRecordQuery = "UPDATE "+TABLE_NAME+" SET bookTitle = ?, bookAuthor = ?, obtainDate = ?, returnDate = ? WHERE bookId = ?";
	private final String deleteRecordQuery = "DELETE FROM "+TABLE_NAME+" WHERE bookId = ?";
	
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
		ArrayList<HashMap<String, String>> result = new ArrayList<>();
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(findHundredRecordsQuery);
			while(resultSet.next()) {
				HashMap<String, String> tmp = new HashMap<>();
				tmp.put("bookId", String.valueOf(resultSet.getInt("bookId")));
				tmp.put("bookTitle", resultSet.getString("bookTitle"));
				tmp.put("bookAuthor", resultSet.getString("bookAuthor"));
				tmp.put("obtainDate", resultSet.getString("obtainDate"));
				tmp.put("returnDate", resultSet.getString("returnDate"));
				
				result.add(tmp);
			}
		} catch(SQLException cause) {
			// TODO: logging
			throw cause;
		}
		return result;
	}

	public int addRecord(HashMap<String, String> record) throws SQLException {
		int newRecordId = -1;
		
		try (PreparedStatement statement = connection.prepareStatement(insertRecordQuery, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, record.get("bookTitle"));
			statement.setString(2, record.get("bookAuthor"));
			statement.setString(3, record.get("obtainDate"));
			statement.setString(4, record.get("returnDate"));
			
			if (statement.executeUpdate() == 0) {
				// TODO: logging
				throw new SQLException("Fault during adding new element.");
			} else {
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					newRecordId = generatedKeys.getInt(1);
				} else {
					// TODO: logging
					throw new SQLException("Fault during adding new element. New book id has not been initialized.");
				}
			}
		} catch(SQLException cause) {
			// TODO: logging
			throw cause;
		}
		return newRecordId;
	}

	public void updateRecord(HashMap<String, String> record) throws SQLException {
		try (PreparedStatement statement= connection.prepareStatement(updateRecordQuery)) {
			statement.setString(1, record.get("bookTitle"));
			statement.setString(2, record.get("bookAuthor"));
			statement.setString(3, record.get("obtainDate"));
			statement.setString(4, record.get("returnDate"));
			statement.setInt(5, Integer.parseInt(record.get("bookId")));
			
			System.out.println(Integer.parseInt(record.get("bookId")));
			
			if (statement.executeUpdate() == 0) {
				// TODO: logging
				throw new SQLException("Fault during updating element");
			}
		} catch(SQLException cause) {
			// TODO: logging
			throw cause;
		}
	}

	public void deleteRecordById(int bookId) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(deleteRecordQuery)) {
			statement.setInt(1, bookId);
			
			if (statement.executeUpdate() == 0 ) {
				// TODO: logging
				throw new SQLException("Fault during deleting element.");
			}
		} catch(SQLException cause) {
			// TODO: logging
			throw cause;
		}
	}

}
