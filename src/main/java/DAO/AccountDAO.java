package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public Account createAccount(String username, String password) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();


            while(resultSet.next()) {
                int generatedAccountID = (int) resultSet.getLong(1);
                return new Account(generatedAccountID, username, password);
            }
        } catch (Exception e) {
            System.out.println("createAccount: " + e.getMessage());
        }

        return null;
    }

    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                return new Account(resultSet.getInt("account_id"), resultSet.getString("username"), resultSet.getString("password"));
            }
        } catch (Exception e) {
            System.out.println("getAccountByUsername: " + e.getMessage());
        }

        return null;
    }

    public Account getAccountByID(int account_id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE account_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                return new Account(resultSet.getInt("account_id"), resultSet.getString("username"), resultSet.getString("password"));
            }
        } catch (Exception e) {
            System.out.println("getAccountByUsername: " + e.getMessage());
        }

        return null;
    }

    public Account loginAccount(String username, String password) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                return new Account(resultSet.getInt("account_id"), resultSet.getString("username"), resultSet.getString("password"));
            }
        } catch (Exception e) {
            System.out.println("getAccountByUsername: " + e.getMessage());
        }

        return null;
    }
}
