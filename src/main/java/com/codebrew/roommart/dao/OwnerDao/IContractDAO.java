package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.Contract;

import java.util.ArrayList;

public interface IContractDAO {
    public Contract getContract(int roomID);
    public ArrayList<Contract> GetListContractByHostelYearQuater(String hostelName, String year, String quatertmp);
}
