package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.RoomInformation;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

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
                String sql = "SELECT room_id, room.property_id, room_number, room_area, attic, room_status\n" +
                        "FROM room\n" +
                        "JOIN properties ON room.property_id = properties.property_id\n";

                if (city != "all" && city!= null) {
                    System.out.println("city not empty : " +city);
                    String c = "Thành Phố Hà Nội";
                    if(c.equals(city)){
                        System.out.println(c + " == " +city);
                    }else System.out.println(c + " != " +city);
                    sql += "WHERE properties.city = '" + city + "'";
                    if (district != "all" && district != "" && city!= null) {
                        System.out.println("district not empty : " +district);

                        sql += " AND properties.district = '" + district + "'";
                        if (ward != "all" && district != "" && city!= null) {
                            System.out.println("ward not empty : " + ward);

                            sql += " AND properties.ward = '" + ward + "'";
                        }
                    }
                }



// Continue with the rest of your code...


                pst = cn.prepareStatement(sql);
//                pst.setInt(1, hostelID);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");
                        int roomNumber = rs.getInt("room_number");
                        int propertyId = rs.getInt("property_id");
                        //int capacity = rs.getInt("capacity");
                        int roomArea = rs.getInt("room_area");
                        int hasAttic = rs.getInt("attic");
                        int roomStatus = rs.getInt("room_status");
                        //  private int roomId;
                        //    private int propertyId;
                        //    private int roomNumber;
                        //    private int roomArea;
                        //    private int attic;
                        //    private int roomStatus;
                        RoomInformation roomInformation = null;
                        rooms.add(Room.builder()
                                .roomId(roomID)
                                .propertyId(propertyId)
                                .roomNumber(roomNumber)
                                .roomArea(roomArea)
                                //.capacity(capacity)
                                .roomStatus(roomStatus)
                                .attic(hasAttic)
                                .roomInformation(roomInformation)
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
    public Room getRoomById(int roomId) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Room roomInfor = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(
                        "SELECT R.room_id, R.room_number, R.room_area R.has_attic, R.hostel_id, R.room_status\n" +
                                "FROM room AS R\n" +
                                "WHERE R.room_id= ?");
                pst.setInt(1, roomId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int roomID = rs.getInt("room_id");
                    int property = rs.getInt("property_id");
                    int roomNumber = rs.getInt("room_number");
                    int roomArea = rs.getInt("room_area");
                   // int capacity = rs.getInt("capacity");
                    int hasAttic = rs.getInt("attic");
                    int roomStatus = rs.getInt("room_status");
                    roomInfor = Room
                            .builder()
                            .roomId(roomID)
                            .propertyId(property)
                            .roomNumber(roomNumber)
                            .roomArea(roomArea)
                            //.capacity(capacity)
                            .attic(hasAttic)
                            .roomStatus(roomStatus)
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
        return roomInfor;
    }
    public Room getRoomInformationByRoomId(int roomID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Room room = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT room_id, P.property_id , room_number, room_status, room_area, attic, property_name, address, ward, district, city\n" +
                        "FROM room R JOIN properties P ON R.property_id = P.property_id\n" +
                        "WHERE R.room_id = ?\n";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);


                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int property_id = rs.getInt("property_id");
                    int roomNumber = rs.getInt("room_number");
                    //int capacity = rs.getInt("capacity");
                    int roomStatus = rs.getInt("room_status");
                    int roomArea = rs.getInt("room_area");
                    int attic = rs.getInt("attic");
                    String name = rs.getString("property_name");
                    String address = rs.getString("address");
                    String ward = rs.getString("ward");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    RoomInformation roomInformation = RoomInformation.builder()
                            .property_name(name)
                            .address(address)
                            .ward(ward)
                            .district(district)
                            .city(city)
                            .build();
                    room = Room.builder()
                            .roomId(roomID)
                            .propertyId(property_id)
                            .roomNumber(roomNumber)
                            .roomStatus(roomStatus)
                            //.capacity(capacity)
                            .roomArea(roomArea)
                            .attic(attic)
                            .roomInformation(roomInformation)
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
}
