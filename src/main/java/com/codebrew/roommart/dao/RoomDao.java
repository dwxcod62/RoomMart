package com.codebrew.roommart.dao;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.RoomInformation;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public boolean addNewManyRooms(int hostelID, int capacity, double roomArea, int attic, int roomStatus,String  imgUrls,
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
                String sql = "DECLARE @room_number int = (SELECT TOP 1 room_number\n" +
                        "FROM dbo.Rooms\n" +
                        "WHERE hostel_id = ?\n" +
                        "ORDER BY room_number DESC)\n" +
                        "IF @room_number is NULL\n" +
                        "\tSET @room_number = 1\n" +
                        "ELSE\n" +
                        "\tSET @room_number = @room_number + 1\n" +
                        "INSERT INTO Rooms (hostel_id, room_number, capacity, room_area, has_attic, room_status)\n" +
                        "VALUES (?, @room_number, ?, ?, ?, ?)\n" +
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

                pst = cn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, hostelID);
                pst.setInt(2, hostelID);
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

    public boolean updateRoom(int roomID, int roomNumber, int capacity, double roomArea, int hasAttic,List<String> imgUrls) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqp6vdayn",
                "api_key", "527664667972471",
                "api_secret", "HzMyAcz7DKbWinMpZEsLe64XkUo",
                "secure", true));

        List<String> imageUrls = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pst = null;
        boolean isSuccess = false;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                if (!imgUrls.isEmpty()){
                    System.out.println("step 1 - get url img to delete from cloudinary");
                    String sql ="SELECT url_img\n" +
                            "FROM imgURL\n" +
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
                    String sqlDeleteImg = "DELETE FROM imgURL WHERE room_id = ?";

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
                        }
                    }
//                    isSuccess = true;
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
}


