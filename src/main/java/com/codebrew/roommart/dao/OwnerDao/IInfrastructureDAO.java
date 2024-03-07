package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.InfrastructureItem;
import com.codebrew.roommart.dto.Infrastructures;

import java.util.List;

public interface IInfrastructureDAO {
    public List<InfrastructureItem> getAllInfrastructure();
    public List<Infrastructures> getRoomInfrastructures(int roomID);
    public boolean updateInfrastructureStatus(int idInfrastructureRoom, int status);
    public Boolean addNewInfrastructure(int roomID, int quantity, int status, int idInfrastructureItem);
}
