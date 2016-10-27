package com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.DAO;

import com.netcracker.odmg.secondgroup.zoxal.nc2_libraryCard.model.Record;

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
	
	private Connection connection;
	/*
	  Вопрос
	  Может, стоит сразу делать поля типа PreparedStatement и в конструкторе их инициализировать?
	 */
	private final String findByIdQuery = "SELECT * FROM " + TABLE_NAME + " WHERE bookId = ?";
	private final String findByTitleQuery = "SELECT * FROM " + TABLE_NAME + " WHERE bookTitle = ?";
	private final String findHundredRecordsQuery = "SELECT * FROM " + TABLE_NAME + " LIMIT 100";
	
	private final String insertRecordQuery = "INSERT INTO " + TABLE_NAME 
		+ "(`bookTitle`, `bookAuthor`, `obtainDate`, `returnDate`) VALUES (?, ?, ?, ?)";
	
	private final String updateRecordByIdQuery = "UPDATE " + TABLE_NAME 
		+ " SET bookTitle = ?, bookAuthor = ?, obtainDate = ?, returnDate = ? WHERE bookId = ?";
	private final String updateRecordByTitleQuery = "UPDATE " + TABLE_NAME 
		+ " SET bookTitle = ?, bookAuthor = ?, obtainDate = ?, returnDate = ? WHERE bookTitle = ?";
	
	private final String deleteRecordByIdQuery = "DELETE FROM " + TABLE_NAME + " WHERE bookId = ?";
	private final String deleteRecordByTitleQuery = "DELETE FROM " + TABLE_NAME + " WHERE bookTitle = ?";
	
	public MySQLDAO() throws SQLException, ClassNotFoundException{
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
			// TODO: logging
			throw new ClassNotFoundException("Cannot find mysql database driver.", cause);
		}	
	}

	public Record getRecordById(int bookId) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(findByIdQuery)) {
			statement.setInt(1, bookId);

			return getRecordFromStatement(statement);	
		}catch (SQLException cause) {
			// TODO: logging
			throw cause;
		}
	}

	public Record getRecordByTitle(String bookTitle) throws SQLException {		
		try (PreparedStatement statement = connection.prepareStatement(findByTitleQuery)) {
			statement.setString(1, bookTitle);

			return getRecordFromStatement(statement);	
		}catch (SQLException cause) {
			// TODO: logging
			throw cause;
		}		
	}

	private Record getRecordFromStatement(PreparedStatement statement) throws SQLException {
		Record result = new Record();
		try {
			ResultSet resultSet = statement.executeQuery();		
			if (resultSet.next()) {				
				result.setId(resultSet.getInt("bookId"));
				result.setTitle(resultSet.getString("bookTitle"));
				result.setAuthor(resultSet.getString("bookAuthor"));
				result.setObtainDate(resultSet.getString("obtainDate"));
				result.setReturnDate(resultSet.getString("returnDate"));
			} else {
				throw new SQLException("There is no record match query " + statement.toString());
			}			
		}catch (SQLException cause) {
			throw cause;
		}		
		return result;
	}

	public ArrayList<Record> getAllRecords() throws SQLException {
		ArrayList<Record> result = new ArrayList<>();

		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(findHundredRecordsQuery);
			while(resultSet.next()) {
				Record tmp = new Record();				
				tmp.setId(resultSet.getInt("bookId"));
				tmp.setTitle(resultSet.getString("bookTitle"));
				tmp.setAuthor(resultSet.getString("bookAuthor"));
				tmp.setObtainDate(resultSet.getString("obtainDate"));
				tmp.setReturnDate(resultSet.getString("returnDate"));
				
				result.add(tmp);
			}
		} catch(SQLException cause) {
			// TODO: logging
			throw cause;
		}
		return result;
	}

	public int addRecord(Record record) throws SQLException {
		int newRecordId = -1;
		
		try (PreparedStatement statement = connection.prepareStatement(insertRecordQuery, 
															Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, record.getTitle());
			statement.setString(2, record.getAuthor());
			statement.setString(3, record.getObtainDate());
			statement.setString(4, record.getReturnDate());
			
			if (statement.executeUpdate() == 0) {
				throw new SQLException("Fault during adding new element. The query was " + statement.toString());
			} else {
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					newRecordId = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Fault during adding new element. New book id "
							+ "has not been initialized. The query was " + statement.toString());
				}
			}
		} catch(SQLException cause) {
			// TODO: logging
			throw cause;
		}
		return newRecordId;
	}

	public void updateRecordById(Record record) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(updateRecordByIdQuery)) {
			statement.setString(1, record.getTitle());
			statement.setString(2, record.getAuthor());
			statement.setString(3, record.getObtainDate());
			statement.setString(4, record.getReturnDate());
			statement.setInt(5, record.getId());
			
			if (statement.executeUpdate() == 0) {
				throw new SQLException("Fault during updating element. There query was " + statement.toString());
			}
		} catch(SQLException cause) {
			// TODO: logging
			throw cause;
		}
	}

	public void updateRecordByTitle(Record record, String previousTitle) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(updateRecordByTitleQuery)) {
			statement.setString(1, record.getTitle());
			statement.setString(2, record.getAuthor());
			statement.setString(3, record.getObtainDate());
			statement.setString(4, record.getReturnDate());
			statement.setString(5, previousTitle);			
			
			if (statement.executeUpdate() == 0) {
				throw new SQLException("Fault during updating element. There query was " + statement.toString());
			}
		} catch(SQLException cause) {
			// TODO: logging
			throw cause;
		}
	}


	public void deleteRecordById(int bookId) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(deleteRecordByIdQuery)) {
			statement.setInt(1, bookId);
			
			if (statement.executeUpdate() == 0 ) {
				throw new SQLException("Fault during deleting element. There query was " + statement.toString());
			}
		} catch(SQLException cause) {
			// TODO: logging
			throw cause;
		}
	}

	public void deleteRecordByTitle(String bookTitle) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(deleteRecordByTitleQuery)) {
			statement.setString(1, bookTitle);
			
			if (statement.executeUpdate() == 0 ) {
				throw new SQLException("Fault during deleting element. There query was " + statement.toString());
			}
		} catch(SQLException cause) {
			// TODO: logging
			throw cause;
		}
	}

	public void destroy() throws SQLException{
		if (connection == null) return;

		try {
			connection.close();
		} catch(SQLException cause) {
			throw new SQLException("Faut during closing connection", cause);
		}
	}
	public boolean isDestroyed() {
		if(connection == null) {
			return true;
		}
		try {
			return connection.isClosed();
		} catch (SQLException e) {
			//TODO: logging
			return true;
		}
		
	}
}
