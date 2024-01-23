package com.codebrew.roommart.dao;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.RoomInformation;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    private static final String ADD_IMGs = "INSERT INTO roomimgs (room_id,imgurl) \n" +
            "VALUES (?, ?)";
    private static final String COUNT_ROOM = "SELECT COUNT(*) AS room_count FROM rooms";
    public boolean addImgbyId(int room_Id,List<String> imgUrls){
        Connection cn = null;
        PreparedStatement pst = null;
        boolean isInserted = false;
        ResultSet rs = null;
        try{
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
            // insert urlImg into table roomimgs
            pst = cn.prepareStatement(ADD_IMGs);

            for (int i = 0; i < imgUrls.size(); i++) {
                // Set giá trị cho Prepared Statement
                pst.setInt(1, room_Id);
                pst.setString(2, imgUrls.get(i));

                // Thực hiện câu lệnh SQL và kiểm tra kết quả
                if (pst.executeUpdate() > 0) {
                    isInserted = true;
                } else {
                    isInserted = false;
                    break;  // Nếu một trong những lần thêm không thành công, thoát khỏi vòng lặp
                }
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
    public boolean addNewRoom(int landID, int roomNumber, int capacity, double roomArea, int attic, int roomStatus,List<String> imgUrls) {

        //connect db
        Connection cn = null;
        PreparedStatement pst = null;
        boolean isInserted = false;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                // Insert new room include Nhà vệ sinh, cửa sổ, cửa ra vào, máy lạnh theo thứ tự
                String sql ="    INSERT INTO rooms (hostel_id, room_number, capacity, room_area, has_attic, room_status)\n" +
                        "    VALUES (?, ?, ?, ?, ?, ?)\n";

                pst = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, landID);
                pst.setInt(2, roomNumber);
                pst.setInt(3, capacity);
                pst.setDouble(4, roomArea);
                pst.setInt(5, attic);
                pst.setInt(6, roomStatus);




                if (pst.executeUpdate() > 0) {

                    int room_Id = -1;
                    rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        room_Id = rs.getInt(1);

                    }

                     isInserted =addImgbyId(room_Id,imgUrls);

// Kiểm tra kết quả cuối cùng và commit hoặc rollback
                    if (isInserted) {
                        cn.setAutoCommit(false);
                        cn.commit();
                        cn.setAutoCommit(true);
                    } else {

                        cn.rollback();
                        cn.setAutoCommit(true);
                    }



                }else {
                    cn.rollback();
                    cn.setAutoCommit(true);
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
    public List<String>getListImgByRoomId(int rid){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<String> imgs = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                //room_id	property_id	room_number	room_area	attic	room_status
                String sql = "SELECT imgurl\n" +
                        "FROM roomimgs\n" +
                        "where room_id = "+rid;




                pst = cn.prepareStatement(sql);


                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        imgs.add(rs.getString("imgurl"));
                    }
                }else {imgs=null;}
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
        return imgs;
    }
    public List<Room> getListRoomsByCondition(String city, String district, String ward) {
        System.out.println(city + " : city");
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                //room_id	property_id	room_number	room_area	attic	room_status
                String sql = "SELECT room_id, rooms.hostel_id, room_number, capacity, room_area, has_attic, room_status\n" +
                        "FROM rooms\n" +
                        "JOIN hostels ON rooms.hostel_id = hostels.hostel_id\n";

                if (city != "all" && city!= null) {
                    System.out.println("city not empty : " +city);
                    String c = "Thành Phố Hà Nội";
                    if(c.equals(city)){
                        System.out.println(c + " == " +city);
                    }else System.out.println(c + " != " +city);
                    sql += "WHERE hostels.city = '" + city + "'";
                    if (district != "all" && district != "" && city!= null) {
                        System.out.println("district not empty : " +district);

                        sql += " AND hostels.district = '" + district + "'";
                        if (ward != "all" && district != "" && city!= null) {
                            System.out.println("ward not empty : " + ward);

                            sql += " AND hostels.ward = '" + ward + "'";
                        }
                    }
                }


                pst = cn.prepareStatement(sql);


                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");
                        int landID = rs.getInt("hostel_id");
                        int roomNumber = rs.getInt("room_number");
                        int capacity = rs.getInt("capacity");
                        double roomArea = rs.getDouble("room_area");
                        int hasAttic = rs.getInt("has_attic");
                        int roomStatus = rs.getInt("room_status");
                        RoomInformation roomInformation = null;
                        List<String> urlImg = getListImgByRoomId(roomID);
                        rooms.add(Room.builder()
                                .roomId(roomID)
                                .landId(landID)
                                .roomNumber(roomNumber)
                                .roomArea(roomArea)
                                .capacity(capacity)
                                .roomStatus(roomStatus)
                                .hasAttic(hasAttic)
                                .roomInformation(roomInformation)
                                .imgUrl(urlImg)
                                .build());
                    }
                }else {rooms=null;}
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

    public List<Room> getListRoomsByLandId(int landID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                String sql = "SELECT room_id, hostel_id, room_number, capacity, room_area, has_attic, room_status\n" +
                        "FROM Rooms\n" +
                        "WHERE hostel_id = ?";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, landID);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");
                        int roomNumber = rs.getInt("room_number");
                        int capacity = rs.getInt("capacity");
                        double roomArea = rs.getDouble("room_area");
                        int hasAttic = rs.getInt("has_attic");
                        int roomStatus = rs.getInt("room_status");
                        RoomInformation roomInformation = null;
                        rooms.add(Room.builder()
                                .roomId(roomID)
                                .landId(landID)
                                .roomNumber(roomNumber)
                                .roomArea(roomArea)
                                .capacity(capacity)
                                .roomStatus(roomStatus)
                                .hasAttic(hasAttic)
                                .roomInformation(roomInformation)
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
    public Room getRoomInformationByRoomId(int roomID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Room room = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT room_id, H.hostel_id, room_number, capacity, room_status, room_area, has_attic, name, address, ward, district, city\n" +
                        "FROM Rooms R JOIN Hostels H ON R.hostel_id = H.hostel_id\n" +
                        "WHERE R.room_id = ?\n";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);


                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int landId = rs.getInt("hostel_id");
                    int roomNumber = rs.getInt("room_number");
                    int capacity = rs.getInt("capacity");
                    int roomStatus = rs.getInt("room_status");
                    double roomArea = rs.getDouble("room_area");
                    int hasAttic = rs.getInt("has_attic");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String ward = rs.getString("ward");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    List<String> urlImg = getListImgByRoomId(roomID);
                    RoomInformation roomInformation = RoomInformation.builder()
                            .landName(name)
                            .address(address)
                            .ward(ward)
                            .district(district)
                            .city(city)
                            .build();
                    room = Room.builder()
                            .roomId(roomID)
                            .landId(landId)
                            .roomNumber(roomNumber)
                            .roomStatus(roomStatus)
                            .capacity(capacity)
                            .roomArea(roomArea)
                            .hasAttic(hasAttic)
                            .roomInformation(roomInformation)
                            .imgUrl(urlImg)
                            .build();
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
        return room;
    }

public int countRoom(){

    Connection cn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int roomCount=0;
    try {
        cn = DatabaseConnector.makeConnection();
        if (cn != null) {
            String sql = COUNT_ROOM;

            pst = cn.prepareStatement(sql);



            rs = pst.executeQuery();
            if (rs != null && rs.next()) {
                roomCount = rs.getInt("room_count");
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
    return roomCount;
    }

    public Room getRoomInfoByRenterId(int renterId) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Room roomInfo = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT R.* FROM Rooms R " +
                        "INNER JOIN Contracts C ON R.room_id = C.room_id " +
                        "WHERE C.renter_id = ?";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int room_id = rs.getInt("room_id");
                    int land_id = rs.getInt("hostel_id");
                    int roomNumber = rs.getInt("room_number");
                    double roomArea = rs.getInt("room_area");
                    int capacity = rs.getInt("capacity");
                    roomInfo = Room
                            .builder()
                            .roomId(room_id)
                            .landId(land_id)
                            .roomNumber(roomNumber)
                            .capacity(capacity)
                            .roomArea(roomArea)
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return roomInfo;
    }
}
