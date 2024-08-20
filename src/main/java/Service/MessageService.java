package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(int posted_by, String message_text, long time_posted_epoch) {
        return this.messageDAO.createMessage(posted_by, message_text, time_posted_epoch);
    }

    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    public Message getMessageByID(int message_id) {
        return this.messageDAO.getMessageByID(message_id);
    }

    public Message deleteMessageByID(int message_id) {
        return this.messageDAO.deleteMessageByID(message_id);
    }

    public Message updateMessageByID(int message_id, String message_text) {
        return this.messageDAO.updateMessageByID(message_id, message_text);
    }

    public List<Message> getAllMessagesByID(int account_id) {
        return this.messageDAO.getAllMessagesByID(account_id);
    }
}
