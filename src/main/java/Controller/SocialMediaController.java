package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // Get Routes
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessagesByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);

        // Post Routes
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);

        // Patch Routes
        app.patch("/messages/{message_id}", this::patchMessagesByIDHandler);

        // Delete Routes
        app.delete("/messages/{message_id}", this::deleteMessagesByIDHandler);

        return app;
    }

    // Get Route Handlers
    private void getMessagesHandler(Context context) {
        try {            
            List<Message> allMessages = messageService.getAllMessages();

            if (allMessages != null) {
                context.json(allMessages);
            } else {
                context.json("");
            }
        } catch (Exception e) {
            System.out.println("getMessagesHandler: " + e.getMessage());
            context.status(500);
        }
    }

    private void getMessagesByIDHandler(Context context) {
        try {
            int message_id = Integer.parseInt(context.pathParam("message_id"));
            Message message = messageService.getMessageByID(message_id);

            if (message != null) {
                context.json(message);
            } else {
                context.json("");
            }
        } catch (Exception e) {
            System.out.println("getMessagesByIDHandler: " + e.getMessage());
            context.status(500);
        }
    }

    private void getMessagesByAccountHandler(Context context) {
        try {            
            int account_id = Integer.parseInt(context.pathParam("account_id"));
            List<Message> allMessages = messageService.getAllMessagesByID(account_id);

            if (allMessages != null) {
                context.json(allMessages);
            } else {
                context.json("");
            }
        } catch (Exception e) {
            System.out.println("getMessagesByAccountHandler: " + e.getMessage());
            context.status(500);
        }
    }

    // Post Route Handlers
    private void postRegisterHandler(Context context) {
        String jsonBody = context.body();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Account newAccount = objectMapper.readValue(jsonBody, Account.class);

            String username = newAccount.getUsername();
            String password = newAccount.getPassword();
            
            Account duplicateAccount = accountService.getAccountByUsername(username);

            if (username.length() > 0 && password.length() >= 4 && duplicateAccount == null) {
                Account createdAccount = accountService.createAccount(username, password);
                context.json(createdAccount);
            } else {
                context.status(400);
            }
        } catch (Exception e) {
            System.out.println("postRegisterHandler: " + e.getMessage());
            context.status(500);
        }
    }

    private void postLoginHandler(Context context) {
        String jsonBody = context.body();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Account newAccount = objectMapper.readValue(jsonBody, Account.class);

            String username = newAccount.getUsername();
            String password = newAccount.getPassword();
            
            Account currentAccount = accountService.loginAccount(username, password);

            if (currentAccount != null) {
                context.json(currentAccount);
            } else {
                context.status(401);
            }
        } catch (Exception e) {
            System.out.println("postLoginHandler: " + e.getMessage());
            context.status(500);
        }
    }

    private void postMessagesHandler(Context context) {
        String jsonBody = context.body();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Message newMessage = objectMapper.readValue(jsonBody, Message.class);

            int messagePostedBy = newMessage.getPosted_by();
            String messageText = newMessage.getMessage_text();
            long messageEpoc = newMessage.getTime_posted_epoch();
            
            Account messageAuthor = accountService.getAccountByID(messagePostedBy);

            if (messageText.length() > 0 && messageText.length() <= 255 && messageAuthor != null) {
                Message createdMessage = messageService.createMessage(messagePostedBy, messageText, messageEpoc);
                context.json(createdMessage);
            } else {
                context.status(400);
            }
        } catch (Exception e) {
            System.out.println("postMessagesHandler: " + e.getMessage());
            context.status(500);
        }
    }

    // Patch Route Handlers
    private void patchMessagesByIDHandler(Context context) {
        String jsonBody = context.body();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Message newMessage = objectMapper.readValue(jsonBody, Message.class);
            
            int message_id = Integer.parseInt(context.pathParam("message_id"));
            String messageText = newMessage.getMessage_text();

            Message fetchedMessage = messageService.getMessageByID(message_id);

            if (messageText.length() > 0 && messageText.length() <= 255 && fetchedMessage != null) {
                Message updatedMessage = messageService.updateMessageByID(message_id, messageText);
                context.json(updatedMessage);
            } else {
                context.status(400);
            }
        } catch (Exception e) {
            System.out.println("patchMessagesByIDHandler: " + e.getMessage());
            context.status(500);
        }
    }

    // Delete Route Handlers
    private void deleteMessagesByIDHandler(Context context) {
        try {
            int message_id = Integer.parseInt(context.pathParam("message_id"));
            Message deletedMessage = messageService.deleteMessageByID(message_id);

            if (deletedMessage != null) {
                context.json(deletedMessage);
            } else {
                context.json("");
            }
        } catch (Exception e) {
            System.out.println("deleteMessagesByIDHandler: " + e.getMessage());
            context.status(500);
        }
    }
}