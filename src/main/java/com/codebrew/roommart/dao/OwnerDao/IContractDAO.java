package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.Contract;

public interface IContractDAO {
    public Contract getContract(int roomID);
}
