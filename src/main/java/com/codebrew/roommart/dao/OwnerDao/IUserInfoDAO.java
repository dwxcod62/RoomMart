package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.AccountInfo;

public interface IUserInfoDAO {
    public AccountInfo getAccountInformationById(int renterId);
}
