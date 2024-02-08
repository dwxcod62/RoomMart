package com.codebrew.roommart.dto.OwnerDTO;

import com.codebrew.roommart.dao.OwnerDao.IHostelServiceDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelServiceDAO;
import com.codebrew.roommart.dto.HostelService;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        IHostelServiceDAO hostelServiceDAO = new HostelServiceDAO();
        List<HostelService> list = hostelServiceDAO.getCurrentListServicesOfAHostel(1);

        for (HostelService hostel :
                list) {
            System.out.println(hostel.getStatus());
        }
    }
}
