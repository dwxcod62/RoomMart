package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.UserInformation;

public interface IAccountDAO {
    public Account getAccountById(int id);
    public UserInformation getAccountInformationById(int accId);
}
