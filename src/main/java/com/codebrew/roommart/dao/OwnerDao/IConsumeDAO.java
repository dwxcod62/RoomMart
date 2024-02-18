package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.OwnerDTO.Consume;

import java.util.List;

public interface IConsumeDAO {
    public List<Consume> getConsumeHistory(int roomID);
    public Consume getNearestConsume(int roomID);
    public List<Consume> getConsumeThisMonth(int roomID);
    public Consume getConsumeByID(int consumeID);
}
