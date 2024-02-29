package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.RoomInformation;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//DatabaseConnector
public class RoomDao {
    //--------------------------------SQL query----------------------------------
    private static final String ADD_IMGs = "INSERT INTO imgURL (room_id,url_img) \n" +
            "VALUES (?, ?)";
    //--------------------------------Method-------------------------------------
    public List<Room> getListRoomsByHostelId(int hostelID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                String sql = "SELECT room_id, hostel_id, room_number, capacity, room_area, has_attic, room_status, imgUrl\n" +
                        "FROM Rooms\n" +
                        "WHERE hostel_id = ?";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, hostelID);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");
                        int roomNumber = rs.getInt("room_number");
                        int capacity = rs.getInt("capacity");
                        double roomArea = rs.getDouble("room_area");
                        int hasAttic = rs.getInt("has_attic");
                        int roomStatus = rs.getInt("room_status");
                        String imgUrl = rs.getString("imgUrl");
                        RoomInformation roomInformation = null;
                        rooms.add(Room.builder()
                                .roomId(roomID)
                                .hostelId(hostelID)
                                .roomNumber(roomNumber)
                                .roomArea(roomArea)
                                .capacity(capacity)
                                .roomStatus(roomStatus)
                                .hasAttic(hasAttic)
                                .roomInformation(roomInformation)
                                .imgUrl(imgUrl)
                                .build());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return rooms;
    }
    public List<Room> getListRooms() {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                String sql = "SELECT room_id, hostel_id, room_number, capacity, room_area, has_attic, room_status, imgUrl\n" +
                        "FROM Rooms\n";

                pst = cn.prepareStatement(sql);


                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");
                        int hostelID = rs.getInt("hostel_id");
                        int roomNumber = rs.getInt("room_number");
                        int capacity = rs.getInt("capacity");
                        double roomArea = rs.getDouble("room_area");
                        int hasAttic = rs.getInt("has_attic");
                        int roomStatus = rs.getInt("room_status");
                        String imgUrl = rs.getString("imgUrl");
                        RoomInformation roomInformation = null;
                        rooms.add(Room.builder()
                                .roomId(roomID)
                                .hostelId(hostelID)
                                .roomNumber(roomNumber)
                                .roomArea(roomArea)
                                .capacity(capacity)
                                .roomStatus(roomStatus)
                                .hasAttic(hasAttic)
                                .roomInformation(roomInformation)
                                .imgUrl(imgUrl)
                                .build());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return rooms;
    }

    public boolean addNewRoom(int hostelID, int roomNumber, int capacity, double roomArea, int attic, int roomStatus,List<String> imgUrls,
                              int quantity1, int status1,
                              int quantity2, int status2,
                              int quantity3, int status3,
                              int quantity4, int status4) {
        Connection cn = null;
        PreparedStatement pst = null;
        boolean isInserted = false;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                // Insert new room include Nhà vệ sinh, cửa sổ, cửa ra vào, máy lạnh theo thứ tự
                String sql = "INSERT INTO Rooms (hostel_id, room_number, capacity, room_area, has_attic, room_status)\n" +
                        "VALUES (?, ?, ?, ?, ?, ?)\n" +
                        "DECLARE @roomID int = SCOPE_IDENTITY()\n" +
                        "INSERT INTO Consumes (number_electric, number_water, update_date, status, room_id)\n" +
                        "VALUES (0, 0, GETDATE(), 0, @roomID)" +
                        "DECLARE @restQuantity int = ?\n" +
                        "WHILE ( @restQuantity > 0 )\n" +
                        "BEGIN\n" +
                        "\tINSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                        "\tVALUES (@roomID, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = N'Nhà vệ sinh'))\n" +
                        "\tSET @restQuantity = @restQuantity - 1\n" +
                        "END\n" +
                        "DECLARE @windowQuantity int = ?\n" +
                        "WHILE ( @windowQuantity > 0 )\n" +
                        "BEGIN\n" +
                        "\tINSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                        "\tVALUES (@roomID, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = N'Cửa sổ'))\n" +
                        "\tSET @windowQuantity = @windowQuantity - 1\n" +
                        "END\n" +
                        "DECLARE @doorQuantity int = ?\n" +
                        "WHILE ( @doorQuantity > 0 )\n" +
                        "BEGIN\n" +
                        "\tINSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                        "\tVALUES (@roomID, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = N'Cửa ra vào'))\n" +
                        "\tSET @doorQuantity = @doorQuantity - 1\n" +
                        "END\n" +
                        "DECLARE @airConditionQuantity int = ?\n" +
                        "WHILE ( @airConditionQuantity > 0 )\n" +
                        "BEGIN\n" +
                        "\tINSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                        "\tVALUES (@roomID, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = N'Máy lạnh'))\n" +
                        "\tSET @airConditionQuantity = @airConditionQuantity - 1\n" +
                        "END";

                pst = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, hostelID);
                pst.setInt(2, roomNumber);
                pst.setInt(3, capacity);
                pst.setDouble(4, roomArea);
                pst.setInt(5, attic);
                pst.setInt(6, roomStatus);

                pst.setInt(7, quantity1);
                pst.setInt(8, status1);

                pst.setInt(9, quantity2);
                pst.setInt(10, status2);

                pst.setInt(11, quantity3);
                pst.setInt(12, status3);

                pst.setInt(13, quantity4);
                pst.setInt(14, status4);

                if (pst.executeUpdate() > 0) {
                    int room_Id = -1;
                    rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        room_Id = rs.getInt(1);

                    }
                    System.out.println("step add imgs");
                    pst = cn.prepareStatement(ADD_IMGs);
                    for (int i = 0; i < imgUrls.size(); i++) {
                        pst.setInt(1, room_Id);
                        pst.setString(2, imgUrls.get(i));
                        if (pst.executeUpdate() > 0) {
                            isInserted = true;
                        } else {
                            isInserted = false;
                            break;  // Nếu một trong những lần thêm không thành công, thoát khỏi vòng lặp
                        }
                    }

//                    isInserted = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return isInserted;
    }


}


