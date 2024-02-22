package com.codebrew.roommart.dao;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import java.util.Map;

import com.codebrew.roommart.dto.Contract;
import com.codebrew.roommart.dto.InfrastructureItem;
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
    private static final String INSERT_ROOM ="DO $$\n" +
            "DECLARE \n" +
            "    roomID INT;\n" +
            "    restQuantity INT := ?;\n" +
            "    windowQuantity INT := ?;\n" +
            "    doorQuantity INT := ?;\n" +
            "    airConditionQuantity INT := ?;\n" +
            "BEGIN\n" +
            "INSERT INTO Rooms (hostel_id, room_number, capacity, room_area, has_attic, room_status)\n" +
            "VALUES (?, ?, ?, ?, ?, ?)  RETURNING room_id INTO roomID;\n" +
            "    INSERT INTO Consumes (number_electric, number_water, update_date, status, room_id)\n" +
            "    VALUES (0, 0, CURRENT_DATE, 0, roomID);\n" +
            "    LOOP\n" +
            "        EXIT WHEN restQuantity <= 0;\n" +
            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
            "        VALUES (roomID, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Nhà vệ sinh'));\n" +
            "        restQuantity := restQuantity - 1;\n" +
            "    END LOOP;\n" +
            "    LOOP\n" +
            "        EXIT WHEN windowQuantity <= 0;\n" +
            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
            "        VALUES (roomID, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Cửa sổ'));\n" +
            "        windowQuantity := windowQuantity - 1;\n" +
            "    END LOOP;\n" +
            "    LOOP\n" +
            "        EXIT WHEN doorQuantity <= 0;\n" +
            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
            "        VALUES (roomID, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Cửa ra vào'));\n" +
            "        doorQuantity := doorQuantity - 1;\n" +
            "    END LOOP;\n" +
            "    LOOP\n" +
            "        EXIT WHEN airConditionQuantity <= 0;\n" +
            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
            "        VALUES (roomID, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Máy lạnh'));\n" +
            "        airConditionQuantity := airConditionQuantity - 1;\n" +
            "    END LOOP;\n" +
            "END $$;\n";

    private static final String INSERT_MANY_ROOM=
            "DO $$\n" +
            "DECLARE \n" +
            "    roomID INT;\n" +
            "    restQuantity INT := 1;\n" +
            "    windowQuantity INT := 1;\n" +
            "    doorQuantity INT := 1;\n" +
            "    airConditionQuantity INT := 1;\n" +
            "    room_num INT;\n" +
            "\n" +
            "\n" +
            "BEGIN\n" +
            "\n" +
            "SELECT room_number INTO room_num\n" +
            "    FROM Rooms\n" +
            "    WHERE hostel_id = ?\n" +
            "    ORDER BY room_number DESC\n" +
            "    LIMIT 1;\n" +
            "\n" +
            "    IF room_num IS NULL THEN\n" +
            "        room_num := 1;\n" +
            "    ELSE\n" +
            "        room_num := room_num + 1;\n" +
            "    END IF;\n" +
            "\n" +
            "INSERT INTO Rooms (hostel_id, room_number, capacity, room_area, has_attic, room_status)\n" +
            "VALUES (?, room_num, ?, ?, ?, ?)  RETURNING room_id INTO roomID;\n" +
            "\n" +
            "    INSERT INTO Consumes (number_electric, number_water, update_date, status, room_id)\n" +
            "    VALUES (0, 0, CURRENT_DATE, 0, roomID);\n" +
            "\n" +
            "    LOOP\n" +
            "        EXIT WHEN restQuantity <= 0;\n" +
            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
            "        VALUES (roomID, ?, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Nhà vệ sinh'));\n" +
            "        restQuantity := restQuantity - 1;\n" +
            "    END LOOP;\n" +
            "\n" +
            "    LOOP\n" +
            "        EXIT WHEN windowQuantity <= 0;\n" +
            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
            "        VALUES (roomID, ?, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Cửa sổ'));\n" +
            "        windowQuantity := windowQuantity - 1;\n" +
            "    END LOOP;\n" +
            "\n" +
            "    LOOP\n" +
            "        EXIT WHEN doorQuantity <= 0;\n" +
            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
            "        VALUES (roomID, ?, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Cửa ra vào'));\n" +
            "        doorQuantity := doorQuantity - 1;\n" +
            "    END LOOP;\n" +
            "\n" +
            "    LOOP\n" +
            "        EXIT WHEN airConditionQuantity <= 0;\n" +
            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
            "        VALUES (roomID, ?, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Máy lạnh'));\n" +
            "        airConditionQuantity := airConditionQuantity - 1;\n" +
            "    END LOOP;\n" +
            "END $$;\n";

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
    public boolean deleteRoom(int roomID){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqp6vdayn",
                "api_key", "527664667972471",
                "api_secret", "HzMyAcz7DKbWinMpZEsLe64XkUo",
                "secure", true));
        Connection cn = null;
        PreparedStatement pst = null;
        boolean isSuccess = false;
        ResultSet rs = null;
        List<String> imageUrls = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                //get img url to delete in cloudinary
                System.out.println("step 1 - get url img to delete from cloudinary");
                String sql ="SELECT imgurl\n" +
                        "FROM roomimgs\n" +
                        "WHERE room_id = ?;";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);
                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        imageUrls.add(rs.getString("imgurl"));


                    }
                }else {imageUrls = null;}
                try {
                    // List chứa public ID của các ảnh cần xóa
                    List<String> publicIds = new ArrayList<>();

                    // Lặp qua danh sách các URL và lấy public ID từ mỗi URL
                    for (String imageUrl : imageUrls) {
                        String publicId = String.valueOf(cloudinary.url().publicId(imageUrl));
                        publicIds.add(publicId);
                    }

                    // Xóa lô ảnh từ Cloudinary bằng public ID của các ảnh
                    Map<String, Object> result = cloudinary.api().deleteResources(publicIds, ObjectUtils.emptyMap());

                    System.out.println("Đã xóa các ảnh thành công!");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Xảy ra lỗi khi xóa các ảnh từ Cloudinary.");
                }

                System.out.println("step 2 - delete imgurl from database");
                String sqlDeleteImg = "DELETE FROM roomimgs WHERE room_id = ?";

                pst = cn.prepareStatement(sqlDeleteImg);
                pst.setInt(1, roomID);
                if (pst.executeUpdate() > 0) {
                    isSuccess = true;
                } else {
                    isSuccess = false;
                }
                if (isSuccess){
                    System.out.println("-> delete oke");

                }else {
                    System.out.println("-> delete fail");

                }
                //delete r
                System.out.println("step 3 - delete remain from db");
                String sqlDeleteConsume = "DELETE FROM consumes WHERE room_id = ?";

                pst = cn.prepareStatement(sqlDeleteConsume);
                pst.setInt(1, roomID);
                if (pst.executeUpdate() == 0) {
                    isSuccess = false;

                    System.out.println("-> DELETE FROM consumes fail");
                } else {
                    System.out.println("-> DELETE FROM consumes oke");
                    isSuccess = true;

                }
                String sqlDeleteInfrastureRoom = "DELETE FROM infrastructuresroom WHERE room_id = ?";

                pst = cn.prepareStatement(sqlDeleteInfrastureRoom);
                pst.setInt(1, roomID);
                if (pst.executeUpdate() == 0) {
                    isSuccess = false;

                    System.out.println("-> DELETE FROM infrastructuresroom fail");
                } else {
                    System.out.println("-> DELETE FROM infrastructuresroom oke");
                    isSuccess = true;

                }
                String sqlDeleteRoom = "DELETE FROM rooms WHERE room_id = ?";

                pst = cn.prepareStatement(sqlDeleteRoom);
                pst.setInt(1, roomID);
                if (pst.executeUpdate() == 0) {
                    isSuccess = false;
                    System.out.println("-> DELETE FROM room fail");

                    cn.rollback();
                } else {
                    System.out.println("-> DELETE FROM room oke");
                    isSuccess = true;
                    cn.commit();
                }
                cn.setAutoCommit(true);
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
        return isSuccess;

    }
    public boolean addNewRoom(int hostelID, int roomNumber, int capacity, double roomArea, int attic, int roomStatus,List<String> imgUrls,
                              int quantity1, int status1,
                               int quantity2, int status2,
                               int quantity3, int status3,
                               int quantity4, int status4) {

        //connect db
        Connection cn = null;
        PreparedStatement pst = null;
        boolean isInserted = false;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                // Insert new room include Nhà vệ sinh, cửa sổ, cửa ra vào, máy lạnh theo thứ tự
                System.out.println("step1 - insert room");
                String sql ="    INSERT INTO rooms (hostel_id, room_number, capacity, room_area, has_attic, room_status)\n" +
                        "    VALUES (?, ?, ?, ?, ?, ?)\n";

                pst = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, hostelID);
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
                    System.out.println("step2-add remain");
                    String sql2 =  "DO $$\n" +
                            "DECLARE \n" +

                            "    roomID INT :='"+room_Id+"';\n" +
                            "    restQuantity INT := '"+quantity1+"';\n" +
                            "    windowQuantity INT := '"+quantity2+"';\n" +
                            "    doorQuantity INT := '"+quantity3+"';\n" +
                            "    airConditionQuantity INT := '"+quantity4+"';\n" +
                            "BEGIN\n" +
                            "    INSERT INTO Consumes (number_electric, number_water, update_date, status, room_id)\n" +
                            "    VALUES (0, 0, CURRENT_DATE, 0, roomID);\n" +

                            "    LOOP\n" +
                            "        EXIT WHEN restQuantity <= 0;\n" +
                            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                            "        VALUES (roomID, 1, "+status1+", (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Nhà vệ sinh'));\n" +
                            "        restQuantity := restQuantity - 1;\n" +
                            "    END LOOP;\n" +
                            "    LOOP\n" +
                            "        EXIT WHEN windowQuantity <= 0;\n" +
                            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                            "        VALUES (roomID, 1, "+status2+", (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Cửa sổ'));\n" +
                            "        windowQuantity := windowQuantity - 1;\n" +
                            "    END LOOP;\n" +
                            "    LOOP\n" +
                            "        EXIT WHEN doorQuantity <= 0;\n" +
                            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                            "        VALUES (roomID, 1, "+status3+", (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Cửa ra vào'));\n" +
                            "        doorQuantity := doorQuantity - 1;\n" +
                            "    END LOOP;\n" +
                            "    LOOP\n" +
                            "        EXIT WHEN airConditionQuantity <= 0;\n" +
                            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                            "        VALUES (roomID, 1, "+status4+", (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Máy lạnh'));\n" +
                            "        airConditionQuantity := airConditionQuantity - 1;\n" +
                            "    END LOOP;\n"+
                            "END $$;\n";

                    pst = cn.prepareStatement(sql2);

                    if (pst.executeUpdate() > 0) {
                        isInserted = true;
                    } else {
                        isInserted = false;}

                    if (imgUrls != null){
                    System.out.println("step 3 - add imgs");

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
                    }}


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
    public boolean deleteImgs(int roomId){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqp6vdayn",
                "api_key", "527664667972471",
                "api_secret", "HzMyAcz7DKbWinMpZEsLe64XkUo",
                "secure", true));
        cloudinary.config.secure = true;
        Connection cn = null;
        PreparedStatement pst = null;
        boolean isInserted = false;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                String sql ="SELECT imgurl\n" +
                        "FROM roomimgs\n" +
                        "WHERE room_id = ?;";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomId);
                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {



                    }
                }else {}

                if (pst.executeUpdate() > 0) {

                    pst = cn.prepareStatement(sql);

                    pst = cn.prepareStatement(ADD_IMGs);

                    // Thực hiện câu lệnh SQL và kiểm tra kết quả
                    if (pst.executeUpdate() > 0) {
                        isInserted = true;
                    } else {
                        isInserted = false;

                    }

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
    public boolean addNewManyRooms(int hostelID, int capacity, double roomArea, int attic, int roomStatus,String imgUrls,
                               int quantity1, int status1,
                               int quantity2, int status2,
                               int quantity3, int status3,
                               int quantity4, int status4) {

        //connect db
        Connection cn = null;
        PreparedStatement pst = null;
        boolean isInserted = false;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                int rn = 0;
                System.out.println("step 0 - get max room number");
                String sql3="SELECT COALESCE(MAX(room_number), 0) + 1 as rn FROM Rooms WHERE hostel_id = '"+hostelID+"'";
                pst = cn.prepareStatement(sql3);
                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                           rn = rs.getInt("rn");
                    }
                }


                // Insert new room include Nhà vệ sinh, cửa sổ, cửa ra vào, máy lạnh theo thứ tự
                System.out.println("step1 - insert room");
                String sql ="    INSERT INTO rooms (hostel_id, room_number, capacity, room_area, has_attic, room_status)\n" +
                        "    VALUES (?, ?, ?, ?, ?, ?)\n";

                pst = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, hostelID);
                pst.setInt(2, rn);
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
                    System.out.println("step2-add remain");
                    String sql2 = "DO $$\n" +
                            "DECLARE \n" +
                            "    roomID INT := '"+room_Id+"';\n" +
                            "    restQuantity INT := '"+quantity1+"';\n" +
                            "    windowQuantity INT := '"+quantity2+"';\n" +
                            "    doorQuantity INT := '"+quantity3+"';\n" +
                            "    airConditionQuantity INT := '"+quantity4+"';\n" +
                            "BEGIN\n" +
                            "    INSERT INTO Consumes (number_electric, number_water, update_date, status, room_id)\n" +
                            "    VALUES (0, 0, CURRENT_DATE, 0, roomID);\n" +
                            "    LOOP\n" +
                            "        EXIT WHEN restQuantity <= 0;\n" +
                            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                            "        VALUES (roomID, 1, "+status1+", (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Nhà vệ sinh'));\n" +
                            "        restQuantity := restQuantity - 1;\n" +
                            "    END LOOP;\n" +
                            "    LOOP\n" +
                            "        EXIT WHEN windowQuantity <= 0;\n" +
                            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                            "        VALUES (roomID, 1, "+status2+", (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Cửa sổ'));\n" +
                            "        windowQuantity := windowQuantity - 1;\n" +
                            "    END LOOP;\n" +
                            "    LOOP\n" +
                            "        EXIT WHEN doorQuantity <= 0;\n" +
                            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                            "        VALUES (roomID, 1, "+status3+", (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Cửa ra vào'));\n" +
                            "        doorQuantity := doorQuantity - 1;\n" +
                            "    END LOOP;\n" +
                            "    LOOP\n" +
                            "        EXIT WHEN airConditionQuantity <= 0;\n" +
                            "        INSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                            "        VALUES (roomID, 1, "+status4+", (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = 'Máy lạnh'));\n" +
                            "        airConditionQuantity := airConditionQuantity - 1;\n" +
                            "    END LOOP;\n" +
                            "END $$;\n";



                    pst = cn.prepareStatement(sql2);
                    if (pst.executeUpdate() > 0) {
                        isInserted = true;
                    } else {
                        isInserted = false;

                    }
                    System.out.println("step 3 - add imgs");
                    pst = cn.prepareStatement(ADD_IMGs);


                        // Set giá trị cho Prepared Statement
                        pst.setInt(1, room_Id);
                        pst.setString(2, imgUrls);

                        // Thực hiện câu lệnh SQL và kiểm tra kết quả
                        if (pst.executeUpdate() > 0) {
                            isInserted = true;
                        } else {
                            isInserted = false;

                        }

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
public boolean updateRoom(int roomID, int roomNumber, int capacity, double roomArea, int hasAttic,List<String> imgUrls) {
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dqp6vdayn",
            "api_key", "527664667972471",
            "api_secret", "HzMyAcz7DKbWinMpZEsLe64XkUo",
            "secure", true));
    Connection cn = null;
    PreparedStatement pst = null;
    boolean isSuccess = false;
    ResultSet rs = null;
    List<String> imageUrls = new ArrayList<>();
    try {
        cn = DatabaseConnector.makeConnection();
        if (cn != null) {
            cn.setAutoCommit(false);

            //get img url to delete in cloudinary
            if (!imgUrls.isEmpty()){
                System.out.println("step 1 - get url img to delete from cloudinary");
                String sql ="SELECT imgurl\n" +
                        "FROM roomimgs\n" +
                        "WHERE room_id = ?;";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);
                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        imageUrls.add(rs.getString("imgurl"));


                    }
                }else {imageUrls = null;}
                System.out.println("imageUrls: "+imageUrls);
                if (!imageUrls.isEmpty()){
                    try {
                        // List chứa public ID của các ảnh cần xóa
                        List<String> publicIds = new ArrayList<>();

                        // Lặp qua danh sách các URL và lấy public ID từ mỗi URL
                        for (String imageUrl : imageUrls) {
                            String publicId = String.valueOf(cloudinary.url().publicId(imageUrl));
                            publicIds.add(publicId);
                        }

                        // Xóa lô ảnh từ Cloudinary bằng public ID của các ảnh
                        Map<String, Object> result = cloudinary.api().deleteResources(publicIds, ObjectUtils.emptyMap());

                        System.out.println("Đã xóa các ảnh thành công!");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Xảy ra lỗi khi xóa các ảnh từ Cloudinary.");
                    }
                }

                System.out.println("step 2 - delete imgurl from database");
                String sqlDeleteImg = "DELETE FROM roomimgs WHERE room_id = ?";

                pst = cn.prepareStatement(sqlDeleteImg);
                pst.setInt(1, roomID);
                if (pst.executeUpdate() > 0) {
                    System.out.println("Records deleted successfully.");
                } else {
                    System.out.println("No records found to delete.");
                }
            }



            System.out.println("step 3 - update new list img and information");

            String sqlUpdateRoom = "UPDATE Rooms\n" +
                    "SET room_number = ?, capacity = ?, room_area = ?, has_attic = ?\n" +
                    "WHERE room_id = ?";


            pst = cn.prepareStatement(sqlUpdateRoom);
            pst.setInt(1, roomNumber);
            pst.setInt(2, capacity);
            pst.setDouble(3, roomArea);
            pst.setInt(4, hasAttic);
            pst.setInt(5, roomID);

            if (pst.executeUpdate() == 0) {
                cn.rollback();
            } else {
                if (!imgUrls.isEmpty()){
                    System.out.println("-> add imgs to db");

                    pst = cn.prepareStatement(ADD_IMGs);

                    for (int i = 0; i < imgUrls.size(); i++) {
                        // Set giá trị cho Prepared Statement
                        pst.setInt(1, roomID);
                        pst.setString(2, imgUrls.get(i));

                        // Thực hiện câu lệnh SQL và kiểm tra kết quả
                        if (pst.executeUpdate() > 0) {
                            isSuccess = true;
                        } else {
                            isSuccess = false;
                            break;  // Nếu một trong những lần thêm không thành công, thoát khỏi vòng lặp
                        }
                    }}

                cn.commit();
            }
            cn.setAutoCommit(true);
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
    return isSuccess;
}
    public List<String>getListImgByRoomId(int rid){
        System.out.println("getListImgByRoomId");
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
            System.out.println("getListImgByRoomId");
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
    public List<Room> getListRoomsByCondition(String city, String district, String ward, String inputText,int page, int page_Size) {
        System.out.println("get list condition method, CITY get: " +city);
        System.out.println("get by condition input text: " + inputText);
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                //room_id	property_id	room_number	room_area	attic	room_status
                String groupBySql=" GROUP BY \n" +
                                "    rooms.room_id, \n"+
                                "    room_number, \n" +
                                "    capacity, \n" +
                                "    room_area, \n" +
                                "    has_attic, \n" +
                                "    room_status,\n" +
                                "    hostels.name, \n" +
                                "    address,\n" +
                                "    city,\n" +
                                "    ward,\n" +
                                "    district\n"+" ORDER BY \n" +
                                "    rooms.room_id ASC\n";
                String sql = "SELECT \n" +
                        "    rooms.room_id, \n" +

                        "    room_number, \n" +
                        "    capacity, \n" +
                        "    room_area, \n" +
                        "    has_attic, \n" +
                        "    room_status,\n" +
                        "    hostels.name, \n" +
                        "    address,\n" +
                        "    city,\n" +
                        "    ward,\n" +
                        "    district,\n" +
                        "    MIN(roomimgs.imgurl) AS imgUrl,\n" +
                        "    count(roomimgs.imgurl) as count_img\n" +
                        "    \n" +
                        "FROM \n" +
                        "    rooms \n" +
                        "JOIN \n" +
                        "    hostels ON rooms.hostel_id = hostels.hostel_id \n" +
                        "JOIN \n" +
                        "    roomimgs ON rooms.room_id = roomimgs.room_id \n  where 1=1 ";


                if(inputText!=null){

                    try{
                        int input_number = Integer.parseInt(inputText);

                        sql += " AND ( rooms.room_number = '"+input_number+"'  OR rooms.room_area =  '"+input_number+"')";
                        sql += "OR ( CAST(LOWER(hostels.city) AS VARCHAR) LIKE "+"'%" + inputText + "%'"+" or CAST(LOWER(hostels.district) AS VARCHAR) LIKE "+"'%" + inputText + "%'"+" or CAST(LOWER(hostels.ward) AS VARCHAR) LIKE "+"'%" + inputText + "%' ";
                        sql += " OR CAST(LOWER(hostels.name) AS VARCHAR) LIKE "+"'%" + inputText + "%'"+"  OR CAST(LOWER(hostels.address) AS VARCHAR) LIKE  "+"'%" + inputText + "%')\n";

                    }catch (Exception e){
                        System.out.println("int parse error");
                        String inputTextLower = inputText.toLowerCase();
                        sql += "AND ( CAST(LOWER(hostels.city) AS VARCHAR) LIKE "+"'%" + inputTextLower + "%'"+" or CAST(LOWER(hostels.district) AS VARCHAR) LIKE "+"'%" + inputTextLower + "%'"+" or CAST(LOWER(hostels.ward) AS VARCHAR) LIKE "+"'%" + inputTextLower + "%' ";
                        sql += " OR CAST(LOWER(hostels.name) AS VARCHAR) LIKE "+"'%" + inputTextLower + "%'"+"  OR CAST(LOWER(hostels.address) AS VARCHAR) LIKE  "+"'%" + inputTextLower + "%')\n";

                    }


                }
                if  (!city.equalsIgnoreCase("all") && city!= null) {
                    System.out.println("CITY NOT EMPTY");
//                    String c = "Thành Phố Hà Nội";
//                    if(c.equalsIgnoreCase(city)){
//                        System.out.println(c + " == " +city);
//                    }else System.out.println(c + " != " +city);
                    sql += " AND( hostels.city LIKE '" + city + "'";
                    if (!district .equalsIgnoreCase("all") && district != "" && city!= null) {
                        System.out.println("district not empty : " +district);

                        sql += " AND hostels.district LIKE '" + district + "'";
                        if (!ward .equalsIgnoreCase("all") && district != "" && city!= null) {
                            System.out.println("ward not empty : " + ward);

                            sql += " AND hostels.ward = '" + ward + "'";
                        }

                    }
                    sql+=")\n";


                }
                sql+=groupBySql;
                sql+="OFFSET ("+page+" - 1) * "+page_Size+"\n" +
                        "LIMIT "+page_Size+";\n";
//                System.out.println(sql);

                pst = cn.prepareStatement(sql);


                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");

                        int roomNumber = rs.getInt("room_number");
                        int capacity = rs.getInt("capacity");
                        double roomArea = rs.getDouble("room_area");
                        int hasAttic = rs.getInt("has_attic");
                        int roomStatus = rs.getInt("room_status");
                        String hname = rs.getString("name");
                        String address = rs.getString("address");
                        String city2 = rs.getString("city");
                        String district2 = rs.getString("district");
                        String ward2 = rs.getString("ward");
                        int imgNumber = rs.getInt("count_img");
                        String img = rs.getString("imgUrl");
                        List<String> imgList = new ArrayList<>();
                        imgList.add(img);

                        RoomInformation roomInformation = new RoomInformation(hname,address,ward2,district2,city2);


                        rooms.add(Room.builder()
                                .roomId(roomID)
                                .hostelId(imgNumber)
                                .roomNumber(roomNumber)
                                .roomArea(roomArea)
                                .capacity(capacity)
                                .roomStatus(roomStatus)
                                .hasAttic(hasAttic)
                                .roomInformation(roomInformation)
                                .imgUrl(imgList)
                                .build());
                    }
                }else {rooms=null;}
            }
        } catch (Exception e) {
            System.out.println("error in getListRoomsByCondition");
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

    public int getTotalRoomsByCondition(String city, String district, String ward, String inputText) {

        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count=0;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                //room_id	property_id	room_number	room_area	attic	room_status


                String sql = "select count(*) as count \n" +

                        "FROM \n" +
                        "    rooms \n" +
                        "JOIN \n" +
                        "    hostels ON rooms.hostel_id = hostels.hostel_id \n where 1=1 " ;


                if(inputText!=null&&!inputText.isEmpty()){

                    try{
                        int input_number = Integer.parseInt(inputText);
                        sql += " AND ( rooms.room_number = '"+input_number+"'  OR rooms.room_area =  '"+input_number+"')";
                        sql += "OR ( CAST(LOWER(hostels.city) AS VARCHAR) LIKE "+"'%" + inputText + "%'"+" or CAST(LOWER(hostels.district) AS VARCHAR) LIKE "+"'%" + inputText + "%'"+" or CAST(LOWER(hostels.ward) AS VARCHAR) LIKE "+"'%" + inputText + "%' ";
                        sql += " OR CAST(LOWER(hostels.name) AS VARCHAR) LIKE "+"'%" + inputText + "%'"+"  OR CAST(LOWER(hostels.address) AS VARCHAR) LIKE  "+"'%" + inputText + "%')\n";

                    }catch (Exception e){
                        System.out.println("int parse error");
                        String inputTextLower = inputText.toLowerCase();
                        sql += "AND ( CAST(LOWER(hostels.city) AS VARCHAR) LIKE "+"'%" + inputTextLower + "%'"+" or CAST(LOWER(hostels.district) AS VARCHAR) LIKE "+"'%" + inputTextLower + "%'"+" or CAST(LOWER(hostels.ward) AS VARCHAR) LIKE "+"'%" + inputTextLower + "%' ";
                        sql += " OR CAST(LOWER(hostels.name) AS VARCHAR) LIKE "+"'%" + inputTextLower + "%'"+"  OR CAST(LOWER(hostels.address) AS VARCHAR) LIKE  "+"'%" + inputTextLower + "%')\n";

                    }


                }
                if  (city!= null&&(!city.equalsIgnoreCase("all")) ) {
                    System.out.println("CITY NOT EMPTY");
//                    String c = "Thành Phố Hà Nội";
//                    if(c.equalsIgnoreCase(city)){
//                        System.out.println(c + " == " +city);
//                    }else System.out.println(c + " != " +city);
                    sql += " AND( hostels.city LIKE '" + city + "'";
                    if (city!= null && !district .equalsIgnoreCase("all") && district != "" ) {
                        System.out.println("district not empty : " +district);

                        sql += " AND hostels.district LIKE '" + district + "'";
                        if (city!= null&&!ward .equalsIgnoreCase("all") && district != "") {
                            System.out.println("ward not empty : " + ward);

                            sql += " AND hostels.ward = '" + ward + "'";
                        }

                    }
                    sql+=")\n";
                }

//                System.out.println(sql);

                pst = cn.prepareStatement(sql);


                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                       count = rs.getInt("count");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error in getTotalRoomsByCondition");
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
        return count;
    }

    public List<Room> getAllRoom() {

        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                //room_id	property_id	room_number	room_area	attic	room_status
                String sql = "SELECT \n" +
                        "    rooms.room_id, \n" +

                        "    room_number, \n" +
                        "    capacity, \n" +
                        "    room_area, \n" +
                        "    has_attic, \n" +
                        "    room_status,\n" +
                        "    hostels.name, \n" +
                        "    address,\n" +
                        "    city,\n" +
                        "    ward,\n" +
                        "    district,\n" +
                        "    MIN(roomimgs.imgurl) AS imgUrl,\n" +
                        "    count(roomimgs.imgurl) as count_img\n" +
                        "    \n" +
                        "FROM \n" +
                        "    rooms \n" +
                        "JOIN \n" +
                        "    hostels ON rooms.hostel_id = hostels.hostel_id \n" +
                        "JOIN \n" +
                        "    roomimgs ON rooms.room_id = roomimgs.room_id \n" +
                        "GROUP BY \n" +
                        "    rooms.room_id, \n" +

                        "    room_number, \n" +
                        "    capacity, \n" +
                        "    room_area, \n" +
                        "    has_attic, \n" +
                        "    room_status,\n" +
                        "    hostels.name, \n" +
                        "    address,\n" +
                        "    city,\n" +
                        "    ward,\n" +
                        "    district \n"+"ORDER BY \n" +
                        "    rooms.room_id DESC;";



                pst = cn.prepareStatement(sql);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");

                        int roomNumber = rs.getInt("room_number");
                        int capacity = rs.getInt("capacity");
                        double roomArea = rs.getDouble("room_area");
                        int hasAttic = rs.getInt("has_attic");
                        int roomStatus = rs.getInt("room_status");
                        String hname = rs.getString("name");
                        String address = rs.getString("address");
                        String city = rs.getString("city");
                        String district = rs.getString("district");
                        String ward = rs.getString("ward");
                        String img = rs.getString("imgUrl");
                        int imgNumber = rs.getInt("count_img");
                        List<String> imgList = new ArrayList<>();
                        imgList.add(img);

                        RoomInformation roomInformation = new RoomInformation(hname,address,ward,district,city);
                        rooms.add(Room.builder()
                                .roomId(roomID)
                                .hostelId(imgNumber) // number of image :))
                                .roomNumber(roomNumber)
                                .roomArea(roomArea)
                                .capacity(capacity)
                                .roomStatus(roomStatus)
                                .hasAttic(hasAttic)
                                .roomInformation(roomInformation)
                                .imgUrl(imgList)
                                .build());
                    }
                }else {rooms=null;}




            }
        } catch (Exception e) {
            System.out.println("error getAllRoom");
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
    public List<Room> getAllRecommendRoom(int rid) {
        System.out.println("getAllRecommendRoom");
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                //room_id	property_id	room_number	room_area	attic	room_status
                String sql = "SELECT \n" +
                        "    rooms.room_id, \n" +
                        "    room_number, \n" +
                        "    capacity, \n" +
                        "    room_area, \n" +
                        "    has_attic, \n" +
                        "    room_status,\n" +
                        "    hostels.name, \n" +
                        "    address,\n" +
                        "    city,\n" +
                        "    ward,\n" +
                        "    district,\n" +
                        "    MIN(roomimgs.imgurl) AS imgUrl,\n" +
                        "    count(roomimgs.imgurl) as count_img\n" +
                        "    \n" +
                        "FROM \n" +
                        "    rooms \n" +
                        "JOIN \n" +
                        "    hostels ON rooms.hostel_id = hostels.hostel_id \n" +
                        "JOIN \n" +
                        "    roomimgs ON rooms.room_id = roomimgs.room_id \n" +
                        " WHERE rooms.room_id <> ?\n"+
                        "GROUP BY \n" +
                        "    rooms.room_id, \n" +
                        "    room_number, \n" +
                        "    capacity, \n" +
                        "    room_area, \n" +
                        "    has_attic, \n" +
                        "    room_status,\n" +
                        "    hostels.name, \n" +
                        "    address,\n" +
                        "    city,\n" +
                        "    ward,\n" +
                        "    district \n"+"ORDER BY \n" +
                        "    rooms.room_id DESC limit 5;";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, rid);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");

                        int roomNumber = rs.getInt("room_number");
                        int capacity = rs.getInt("capacity");
                        double roomArea = rs.getDouble("room_area");
                        int hasAttic = rs.getInt("has_attic");
                        int roomStatus = rs.getInt("room_status");
                        String hname = rs.getString("name");
                        String address = rs.getString("address");
                        String city = rs.getString("city");
                        String district = rs.getString("district");
                        String ward = rs.getString("ward");
                        String img = rs.getString("imgUrl");
                        int imgNumber = rs.getInt("count_img");
                        List<String> imgList = new ArrayList<>();
                        imgList.add(img);

                        RoomInformation roomInformation = new RoomInformation(hname,address,ward,district,city);
                        rooms.add(Room.builder()
                                .roomId(roomID)
                                .hostelId(imgNumber) // number of image :))
                                .roomNumber(roomNumber)
                                .roomArea(roomArea)
                                .capacity(capacity)
                                .roomStatus(roomStatus)
                                .hasAttic(hasAttic)
                                .roomInformation(roomInformation)
                                .imgUrl(imgList)
                                .build());
                    }
                }else {rooms=null;}




            }
        } catch (Exception e) {
            System.out.println("error getAllRoom");
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

    public List<Room> getListRoomsByHostelId(int hostelID) {
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
        System.out.println("getRoomInformationByRoomId");
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Room room = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT room_id, H.hostel_id, room_number, capacity, room_status, room_area, has_attic, name, address, ward, district, city , H.owner_account_id\n" +
                        "FROM Rooms R JOIN Hostels H ON R.hostel_id = H.hostel_id\n" +
                        "WHERE R.room_id = ?\n";


                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);


                rs = pst.executeQuery();
                if (rs != null && rs.next()) {

                    int hostelId = rs.getInt("hostel_id");
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
                            .hostelName(name)
                            .address(address)
                            .ward(ward)
                            .district(district)
                            .city(city)
                            .build();
                    room = Room.builder()
                            .roomId(roomID)
                            .hostelId(hostelId)
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
            System.out.println("error in getRoomInformationByRoomId");
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
                    int hostel_id = rs.getInt("hostel_id");
                    int roomNumber = rs.getInt("room_number");
                    double roomArea = rs.getInt("room_area");
                    int capacity = rs.getInt("capacity");
                    roomInfo = Room
                            .builder()
                            .roomId(room_id)
                            .hostelId(hostel_id)
                            .roomNumber(roomNumber)
                            .capacity(capacity)
                            .roomArea(roomArea)
                            .build();
                }
            }
        } catch (Exception e) {
            System.out.println("error in getRoomInfoByRenterId");
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

    public Date get_end_date_by_RoomId(int rid) {
        System.out.println("-> get_end_date_by_RoomId ");
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Contract contract = null;
        Date endDate = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_CONTRACT_BY_ROOM_ID);
//                System.out.println(GET_CONTRACT_BY_ROOM_ID);
                pst.setInt(1, rid);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    endDate = rs.getDate("end_date");
                }
            }
        } catch (Exception e) {
            System.out.println("get_end_date_by_RoomId error");
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
        return endDate;
    }
    private static final String
            GET_CONTRACT_BY_ROOM_ID = "SELECT cd.end_date\n" +
            "FROM contract_details cd\n" +
            "JOIN contract_main cm ON cd.contract_details_id = cm.contract_details_id\n" +
            "WHERE cm.room_id = ?;\n";

    public boolean checkRoomExist(int roomNumber,int hostelID){
        boolean isExist = false;
        System.out.println("-> checkRoomExist ");
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;


        String sql = "SELECT COUNT(*) AS count_roomber\n" +
                "FROM rooms\n" +
                "WHERE room_number = ? and hostel_id=?;";
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(sql);
//                System.out.println(sql);
                pst.setInt(1, roomNumber);
                pst.setInt(2, hostelID);


                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    count = rs.getInt("count_roomber");
                }
                if (count!=0){
                    isExist=true;
                }
            }
        } catch (Exception e) {
            System.out.println("checkRoomExist error");
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


        return isExist;
    }
}
