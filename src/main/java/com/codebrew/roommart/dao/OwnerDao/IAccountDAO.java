package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.AccountInfo;


public interface IAccountDAO {
    public Account getAccountById(int id);
    public AccountInfo getAccountInformationById(int accId);
    public Account getAccountByUsernameAndPassword(String username, String password);
    public boolean updateAccountPass(int accId, String pass);
}
