package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    public Message createMessage(int posted_by, String message_text, long time_posted_epoch) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, posted_by);
            preparedStatement.setString(2, message_text);
            preparedStatement.setLong(3, time_posted_epoch);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();


            while(resultSet.next()) {
                int generatedAccountID = (int) resultSet.getLong(1);
                return new Message(generatedAccountID, posted_by, message_text, time_posted_epoch);
            }
        } catch (Exception e) {
            System.out.println("createMessage: " + e.getMessage());
        }

        return null;
    }

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();

        try {
            List<Message> allMessages = new ArrayList<>();
            String sql = "SELECT * FROM message;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int fetchedMessage_id = resultSet.getInt("message_id");
                int fetchedPosted_by = resultSet.getInt("posted_by");
                String fetchedMessage_text = resultSet.getString("message_text");
                long fetchedTime_posted_epoch = resultSet.getLong("time_posted_epoch");

                allMessages.add(new Message(fetchedMessage_id, fetchedPosted_by, fetchedMessage_text, fetchedTime_posted_epoch));
            }

            return allMessages;
        } catch (Exception e) {
            System.out.println("getAllMessages: " + e.getMessage());
        }

        return null;
    }

    public Message getMessageByID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int fetchedMessage_id = resultSet.getInt("message_id");
                int fetchedPosted_by = resultSet.getInt("posted_by");
                String fetchedMessage_text = resultSet.getString("message_text");
                long fetchedTime_posted_epoch = resultSet.getLong("time_posted_epoch");

                return new Message(fetchedMessage_id, fetchedPosted_by, fetchedMessage_text, fetchedTime_posted_epoch);
            }
        } catch (Exception e) {
            System.out.println("getMessageByID: " + e.getMessage());
        }

        return null;
    }

    public Message deleteMessageByID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String selectSql = "SELECT * FROM message WHERE message_id = ?;";

            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
            selectStatement.setInt(1, message_id);
            
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int fetchedMessage_id = resultSet.getInt("message_id");
                int fetchedPosted_by = resultSet.getInt("posted_by");
                String fetchedMessage_text = resultSet.getString("message_text");
                long fetchedTime_posted_epoch = resultSet.getLong("time_posted_epoch");

                String deleteSql = "DELETE FROM message WHERE message_id = ?;";

                PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                deleteStatement.setInt(1, message_id);
                
                int rowsDeleted = deleteStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    return new Message(fetchedMessage_id, fetchedPosted_by, fetchedMessage_text, fetchedTime_posted_epoch);
                }
            }
        } catch (Exception e) {
            System.out.println("deleteMessageByID: " + e.getMessage());
        }

        return null;
    }

    public Message updateMessageByID(int message_id, String message_text) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, message_text);
            preparedStatement.setInt(2, message_id);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                String selectSql = "SELECT * FROM message WHERE message_id = ?;";
                PreparedStatement selectStatement = connection.prepareStatement(selectSql);
                selectStatement.setInt(1, message_id);
    
                ResultSet resultSet = selectStatement.executeQuery();
    
                if (resultSet.next()) {
                    int fetchedMessage_id = resultSet.getInt("message_id");
                    int fetchedPosted_by = resultSet.getInt("posted_by");
                    String fetchedMessage_text = resultSet.getString("message_text");
                    long fetchedTime_posted_epoch = resultSet.getLong("time_posted_epoch");
    
                    return new Message(fetchedMessage_id, fetchedPosted_by, fetchedMessage_text, fetchedTime_posted_epoch);
                }
            }
        } catch (Exception e) {
            System.out.println("updateMessageByID: " + e.getMessage());
        }

        return null;
    }

    public List<Message> getAllMessagesByID(int account_id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            List<Message> allMessages = new ArrayList<>();
            String sql = "SELECT * FROM message WHERE posted_by = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int fetchedMessage_id = resultSet.getInt("message_id");
                int fetchedPosted_by = resultSet.getInt("posted_by");
                String fetchedMessage_text = resultSet.getString("message_text");
                long fetchedTime_posted_epoch = resultSet.getLong("time_posted_epoch");

                allMessages.add(new Message(fetchedMessage_id, fetchedPosted_by, fetchedMessage_text, fetchedTime_posted_epoch));
            }

            return allMessages;
        } catch (Exception e) {
            System.out.println("getAllMessages: " + e.getMessage());
        }

        return null;
    }
}
