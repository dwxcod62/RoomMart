package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.AccountInfo;


public interface IAccountDAO {
    public Account getAccountById(int id);
    public AccountInfo getAccountInformationById(int accId);
}
