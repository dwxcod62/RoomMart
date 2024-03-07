package com.codebrew.roommart.servlets.OwnerServlets.Statistic;

import com.codebrew.roommart.dao.OwnerDao.Impl.BillDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.ContractDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.RoomDAO;
import com.codebrew.roommart.dao.ReportDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Contract;
import com.codebrew.roommart.dto.OwnerDTO.Bill;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.dto.Report;
import com.codebrew.roommart.dto.Room;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "StatisticServlet", value = "/statistic")
public class StatisticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String URL = "statistic-page";
        HttpSession session = request.getSession();
        BillDAO billDAO = new BillDAO();
        HostelDAO hostelDAO = new HostelDAO();
        ContractDAO contractDAO = new ContractDAO();
        ReportDao reportDAO = new ReportDao();
        RoomDAO roomDAO = new RoomDAO();
        Account account = (Account) session.getAttribute("USER");
        int totalMoney = 0;
        int expenseMoney = 0;
        int revenueMoney = 0;
        int billLength = 0;
        ArrayList<String> listYear = new ArrayList<String>();

        try {

            List<Hostel> listHostel = hostelDAO.getHostelByOwnerId(account.getAccId());
            if (listHostel.size() == 0){
                request.setAttribute("error", "Ban chưa khu trọ nào!");
                session.setAttribute("CURRENT_PAGE", "statistic");
                return;
            }
            List<Room> listRoomToCheck = roomDAO.getListRoomsByHostelId(listHostel.get(0).getHostelID());
            request.setAttribute("listRoomToCheck", listRoomToCheck);
            ArrayList<Bill> listBillByHostel = billDAO.GetListBillByHostel(listHostel.get(0).getHostelName());
//            if (listBillByHostel.size() == 0){
//                request.setAttribute("error", "Phòng trọ của bạn chưa có bill!");
//                request.getRequestDispatcher(URL).forward(request, response);
//
//            }
            boolean check;
            for (int i = 0; i < listBillByHostel.size(); i++) {
                String tmpYear = listBillByHostel.get(i).getCreatedDate().substring(0, 4);
                if (listYear.size() == 0) {
                    listYear.add(tmpYear);
                }
                check = true;
                for (int j = 0; j < listYear.size(); j++) {
                    if (listYear.get(j).equalsIgnoreCase(tmpYear)) {
                        check = false;
                    }
                    if (check) {
                        listYear.add(tmpYear);
                        check = false;
                    }
                }
            }
            if (listYear.size() != 0) {
                ArrayList<Bill> listBillByFixValue = billDAO.GetListBillByHostelYearQuater(listHostel.get(0).getHostelName(), listYear.get(0), "quater_1");
                //lấy tất cả các năm mà khu trọ đã hoạt động
                for (int i = 0; i < listBillByFixValue.size(); i++) {
                    totalMoney += listBillByFixValue.get(i).getTotalMoney();
                }

                if (totalMoney > 100000000)
                    expenseMoney = (totalMoney / 100) * 5;
                revenueMoney = totalMoney - expenseMoney;
                int totalMoneyMonth1 = 0;
                int totalMoneyMonth2 = 0;
                int totalMoneyMonth3 = 0;

                for (int i = 0; i < listBillByFixValue.size(); i++) {
                    if (listBillByFixValue.get(i).getCreatedDate().substring(5, 7).equalsIgnoreCase("04")) {
                        totalMoneyMonth1 += listBillByFixValue.get(i).getTotalMoney();
                    } else if (listBillByFixValue.get(i).getCreatedDate().substring(5, 7).equalsIgnoreCase("05")) {
                        totalMoneyMonth2 += listBillByFixValue.get(i).getTotalMoney();
                    } else if (listBillByFixValue.get(i).getCreatedDate().substring(5, 7).equalsIgnoreCase("06")) {
                        totalMoneyMonth3 += listBillByFixValue.get(i).getTotalMoney();
                    }
                }

                request.setAttribute("totalMoneyMonth1", totalMoneyMonth1);
                request.setAttribute("totalMoneyMonth2", totalMoneyMonth2);
                request.setAttribute("listBillByFixValue", listBillByFixValue);
                request.setAttribute("totalMoneyMonth3", totalMoneyMonth3);

                ArrayList<Contract> listContractByFixValue;
                listContractByFixValue = contractDAO.GetListContractByHostelYearQuater(listHostel.get(0).getHostelName(), listYear.get(0), "quater_1");
                int contractCancelRate1 = 0;
                int contractCancelRate2 = 0;
                int contractCancelRate3 = 0;
                int contractCreateRate1 = 0;
                int contractCreateRate2 = 0;
                int contractCreateRate3 = 0;
                Date expriDate = new Date();
                Date cancelDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //String abc = listContractByFixValue.get(i).getExpiration().substring(0, 10);
                for (int i = 0; i < listContractByFixValue.size(); i++) {
                    if (listContractByFixValue.get(i).getStartDate().substring(5, 7).equals("01")) {
                        contractCreateRate1++;
                        expriDate = dateFormat.parse(listContractByFixValue.get(i).getExpiration().substring(0, 10));
                        String tmpDate = listContractByFixValue.get(i).getCancelDate() == null ? null : listContractByFixValue.get(i).getCancelDate().substring(0, 10);
                        if (tmpDate != null) {
                            cancelDate = dateFormat.parse(tmpDate);
                            if (cancelDate.before(expriDate)) {
                                contractCancelRate1++;
                            }
                        }

                    } else if (listContractByFixValue.get(i).getStartDate().substring(5, 7).equals("02")) {
                        contractCreateRate2++;
                        expriDate = dateFormat.parse(listContractByFixValue.get(i).getExpiration().substring(0, 10));
                        String tmpDate = listContractByFixValue.get(i).getCancelDate() == null ? null : listContractByFixValue.get(i).getCancelDate().substring(0, 10);
                        if (tmpDate != null) {
                            cancelDate = dateFormat.parse(tmpDate);
                            if (cancelDate.before(expriDate)) {
                                contractCancelRate2++;
                            }
                        }

                    } else if (listContractByFixValue.get(i).getStartDate().substring(5, 7).equals("03")) {
                        contractCreateRate3++;
                        expriDate = dateFormat.parse(listContractByFixValue.get(i).getExpiration().substring(0, 10));
                        String tmpDate = listContractByFixValue.get(i).getCancelDate() == null ? null : listContractByFixValue.get(i).getCancelDate().substring(0, 10);
                        if (tmpDate != null) {
                            cancelDate = dateFormat.parse(tmpDate);
                            if (cancelDate.before(expriDate)) {
                                contractCancelRate3++;
                            }
                        }
                    }
                }

                List<Report> listReport;
                listReport = reportDAO.getListReportByHostelId(listHostel.get(0).getHostelID());
                var rate = 0;
                int rep = 0;
                int notyet = 0;
                for (int i = 0; i < listReport.size(); i++) {
                    if (listReport.get(i).getReply() != null) {
                        rep++;
                    } else {
                        notyet++;
                    }
                }
                if (notyet != 0)
                    rate = rep / notyet;

                List<Room> listRoom;
                int numberEmpty = 0;
                int numberRenting = 0;
                int numberContract = 0;
                listRoom = roomDAO.getListRoomsByHostelId(listHostel.get(0).getHostelID());
                for (int i = 0; i < listRoom.size(); i++) {
                    if (listRoom.get(i).getRoomStatus() == 0) {
                        numberEmpty++;
                    } else if (listRoom.get(i).getRoomStatus() == 1) {
                        numberRenting++;
                    } else {
                        numberContract++;
                    }
                }
                request.setAttribute("rate", rate);
                request.setAttribute("numberEmpty", numberEmpty);
                request.setAttribute("numberRenting", numberRenting);
                request.setAttribute("numberContract", numberContract);
                request.setAttribute("contractCancelRate1", contractCancelRate1);
                request.setAttribute("contractCancelRate2", contractCancelRate2);
                request.setAttribute("contractCancelRate3", contractCancelRate3);
                request.setAttribute("contractCreateRate1", contractCreateRate1);
                request.setAttribute("contractCreateRate2", contractCreateRate2);
                request.setAttribute("contractCreateRate3", contractCreateRate3);
                request.setAttribute("listYear", listYear);
                request.setAttribute("totalMoney", totalMoney);
                request.setAttribute("expenseMoney", expenseMoney);
                request.setAttribute("revenueMoney", revenueMoney);
                request.setAttribute("listReport", listReport);
            }
            request.setAttribute("listHostel", listHostel);
            request.setAttribute("hostelName", null);
            request.setAttribute("year", null);
            request.setAttribute("quater", null);
            session.setAttribute("CURRENT_PAGE", "statistic");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            request.getRequestDispatcher(URL).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
