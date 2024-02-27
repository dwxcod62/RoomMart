package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IReportDetailDAO;
import com.codebrew.roommart.dao.ReportCategoryDAO;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.dto.OwnerDTO.ReportDetail;
import com.codebrew.roommart.dto.Report;
import com.codebrew.roommart.dto.ReportCategory;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDetailDAO implements IReportDetailDAO {
    private static final String GET_LIST_REPORTS =
            "SELECT * from Reports where reply_account_id = ? and status = ?";
    @Override
    public List<ReportDetail> getListReports(int hostelOwnerId, int status) {
        Connection conn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;

        List<ReportDetail> list = new ArrayList<>();

        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                System.out.println(hostelOwnerId + ", " + status);
                psm = conn.prepareStatement(GET_LIST_REPORTS);
                psm.setInt(1, hostelOwnerId);
                psm.setInt(2, status);

                rs = psm.executeQuery();

                if (rs != null) {
                    while (rs.next()) {
                        int id_report = rs.getInt("id_report");
                        String send_date = rs.getString("send_date");
                        String content = rs.getString("content");
                        int statuS = rs.getInt("status");
                        String reply_date = rs.getString("reply_date");
                        String reply = rs.getString("reply");
                        String complete_date = rs.getString("complete_date");
                        int reply_account_id = rs.getInt("reply_account_id");
                        int send_account_id = rs.getInt("send_account_id");
                        int cate_id = rs.getInt("cate_id");
                        Report report = Report.builder()
                                .reportID(id_report)
                                .sendDate(send_date)
                                .content(content)
                                .status(statuS)
                                .replyDate(reply_date)
                                .reply(reply)
                                .completeDate(complete_date)
                                .replyAccountID(reply_account_id)
                                .sendAccountID(send_account_id)
                                .cateID(cate_id).build();
//                        UserInformation renterInformation = new UserInfoDAO().getAccountInformationById(send_account_id);
//                        ReportCategory category = new ReportCategoryDAO().getReportCategoryById(cate_id);
//                        Room room = new RoomDAO().getRoomById(room_id);
//                        Hostel hostel = new HostelDAO().getHostelById(hostel_id);
//                        list.add(ReportDetail.builder()
//                                .renterInformation(renterInformation)
//                                .report(report)
//                                .category(category)
//                                .room(room)
//                                .hostel(hostel).build());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(conn, psm, rs);
        }
        return list;
    }
}
