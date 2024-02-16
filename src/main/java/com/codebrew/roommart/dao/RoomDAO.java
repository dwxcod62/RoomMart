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
    private static final String INSERT_ROOM =
            "DO $$\n" +
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
                    pst.executeUpdate();
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
//    quantity1
//    quantity2
//    quantity3
//    quantity4
    public boolean addNewRoom2(int hostelID, int roomNumber, int capacity, double roomArea, int attic, int roomStatus,
                              int quantity1, int status1,
                              int quantity2, int status2,
                              int quantity3, int status3,
                              int quantity4, int status4) {
        System.out.println("Add new room - RoomDAO");
        Connection cn = null;
        PreparedStatement pst = null;
        boolean isInserted = false;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                // Insert new room include Nhà vệ sinh, cửa sổ, cửa ra vào, máy lạnh theo thứ tự
                String sql =  "DO $$\n" +
                        "DECLARE \n" +

                        "    roomID INT;\n" +
                        "    restQuantity INT := '"+quantity1+"';\n" +
                        "    windowQuantity INT := '"+quantity2+"';\n" +
                        "    doorQuantity INT := '"+quantity3+"';\n" +
                        "    airConditionQuantity INT := '"+quantity4+"';\n" +
                        "BEGIN\n" +
                        "INSERT INTO Rooms (hostel_id, room_number, capacity, room_area, has_attic, room_status)\n" +
                        "VALUES ("+hostelID+", "+roomNumber+", "+capacity+", "+roomArea+", "+attic+", "+roomStatus+")  RETURNING room_id INTO roomID;\n" +
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

                pst = cn.prepareStatement(sql);
                int rowsAffected = pst.executeUpdate(); // Thực hiện truy vấn và lưu số hàng ảnh hưởng

                System.out.println(rowsAffected);


                if (rowsAffected > 0) {
                    isInserted = true;
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
    public boolean addNewManyRooms2(int hostelID, int capacity, double roomArea, int attic, int roomStatus,
                                   int quantity1, int status1,
                                   int quantity2, int status2,
                                   int quantity3, int status3,
                                   int quantity4, int status4) {
        Connection cn = null;
        PreparedStatement pst = null;
        boolean isInserted = false;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "DO $$\n" +
                        "DECLARE \n" +
                        "    roomID INT;\n" +
                        "    restQuantity INT := '"+quantity1+"';\n" +
                        "    windowQuantity INT := '"+quantity2+"';\n" +
                        "    doorQuantity INT := '"+quantity3+"';\n" +
                        "    airConditionQuantity INT := '"+quantity4+"';\n" +
                        "    roomNumber INT;\n" +
                        "BEGIN\n" +
                        "    SELECT MAX(room_number) INTO roomNumber\n" +
                        "    FROM Rooms\n" +
                        "    WHERE hostel_id = '"+hostelID+"';\n" +
                        "\n" +
                        "    IF roomNumber IS NULL THEN\n" +
                        "        roomNumber := 1;\n" +
                        "    ELSE\n" +
                        "        roomNumber := roomNumber + 1;\n" +
                        "    END IF;\n" +
                        "    INSERT INTO Rooms (hostel_id, room_number, capacity, room_area, has_attic, room_status)\n" +
                        "    VALUES ("+hostelID+", roomNumber, "+capacity+", "+roomArea+", "+attic+", "+roomStatus+")  RETURNING room_id INTO roomID;\n" +
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



                pst = cn.prepareStatement(sql);


                if (pst.executeUpdate() > 0) {
                    isInserted = true;
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
    public List<Room> getListRoomsByCondition(String city, String district, String ward, String inputText) {
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
                                "    rooms.room_id ASC;";
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
}
