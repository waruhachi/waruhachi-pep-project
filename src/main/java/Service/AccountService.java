package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account getAccountByUsername(String username) {
        return this.accountDAO.getAccountByUsername(username);
    }

    public Account getAccountByID(int account_id) {
        return this.accountDAO.getAccountByID(account_id);
    }

    public Account createAccount(String username, String password) {
        return this.accountDAO.createAccount(username, password);
    }

    public Account loginAccount(String username, String password) {
        return this.accountDAO.loginAccount(username, password);
    }
}
