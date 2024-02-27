package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.UserInformation;

public interface IUserInfoDAO {
    public UserInformation getAccountInformationById(int renterId);
}
