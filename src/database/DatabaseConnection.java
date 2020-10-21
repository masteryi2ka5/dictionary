package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import management.*;

public class DatabaseConnection {

	public Connection connection = null;
	
	public ResultSet selectSet() {
		Statement stm;
		ResultSet rsSet = null;
		try {
			stm = connection.createStatement();
			String selectStatement = "SELECT * FROM tbl_edict";
			rsSet = stm.executeQuery(selectStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rsSet;
	}
	
	public void Connection() throws SQLException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionURL = "jdbc:sqlserver://JARVIS:1433;databaseName=Dictionary;integratedSecurity=true;";
			connection = DriverManager.getConnection(connectionURL, "sa", "masteryi2K");
			System.out.println("Connection succeeded!");
		}
		catch (ClassNotFoundException e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
		}	
	}
	
	public List<Word> showResult(String userInput) throws SQLException {
		Statement stm;
		ResultSet rsSet = null;
		try {
			stm = connection.createStatement();
			String selectStatement = "SELECT idx, word, detail FROM tbl_edict WHERE word = '" + userInput + "'";
			rsSet = stm.executeQuery(selectStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Word> wordList = new ArrayList<Word>();
		while (rsSet.next()) {
			if (rsSet.getString(2).equals(userInput)) {
				wordList.add(new Word(rsSet.getInt("idx"), rsSet.getString("word"), rsSet.getString("detail")));
			}
		}
		return wordList;
	}
	
	public static void main(String[] args) throws SQLException {
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.Connection();
//		ResultSet selectSet = dbConnect.selectSet();
		List<Word> resultList = dbConnect.showResult("Account");
		for (Word i : resultList) {
			System.out.println(i.getIdx()
							+ "\t" + i.getWord()
							+ "\t" + i.getDetail());
		}
	}
	
}
