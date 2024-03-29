package com.codebrew.roommart.dao;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.RoomInformation;
import com.codebrew.roommart.dto.Contract;
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
    private static final String
            GET_CONTRACT_BY_ROOM_ID = "SELECT expiration\n" +
            "FROM contracts\n" +

            "WHERE room_id = ?;\n";
    private static final String
                GET_START_DATE_ROOM_ID = "SELECT start_date\n" +
            "FROM contracts\n" +

            "WHERE room_id = ?;\n";
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
                        List<String> imgUrl = getListImgByRoomId(roomID);
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
                String sql = "SELECT room_id, hostel_id, room_number, capacity, room_area, has_attic, room_status\n" +
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
                        List<String> imgUrl = getListImgByRoomId(roomID);
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

    public boolean addNewRoom(int hostelID, int roomNumber, int capacity, double roomArea, int attic, int roomStatus,List<String> imgUrls, int price,
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
                String sql = "INSERT INTO Rooms (hostel_id, room_number, capacity, room_area, has_attic, room_status, price,room_view)\n" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?,0)\n" ;

                String sql2="INSERT INTO Consumes (number_electric, number_water, update_date, status, room_id)\n" +
                        "VALUES (0, 0, GETDATE(), 0, ?)" +

                        "DECLARE @restQuantity int = ?\n" +
                        "WHILE ( @restQuantity > 0 )\n" +
                        "BEGIN\n" +
                        "\tINSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                        "\tVALUES (?, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = N'Nhà vệ sinh'))\n" +
                        "\tSET @restQuantity = @restQuantity - 1\n" +
                        "END\n" +

                        "DECLARE @windowQuantity int = ?\n" +
                        "WHILE ( @windowQuantity > 0 )\n" +
                        "BEGIN\n" +
                        "\tINSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                        "\tVALUES (?, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = N'Cửa sổ'))\n" +
                        "\tSET @windowQuantity = @windowQuantity - 1\n" +
                        "END\n" +

                        "DECLARE @doorQuantity int = ?\n" +
                        "WHILE ( @doorQuantity > 0 )\n" +
                        "BEGIN\n" +
                        "\tINSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                        "\tVALUES (?, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = N'Cửa ra vào'))\n" +
                        "\tSET @doorQuantity = @doorQuantity - 1\n" +
                        "END\n" +

                        "DECLARE @airConditionQuantity int = ?\n" +
                        "WHILE ( @airConditionQuantity > 0 )\n" +
                        "BEGIN\n" +
                        "\tINSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                        "\tVALUES (?, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = N'Máy lạnh'))\n" +
                        "\tSET @airConditionQuantity = @airConditionQuantity - 1\n" +
                        "END";

                pst = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, hostelID);
                pst.setInt(2, roomNumber);
                pst.setInt(3, capacity);
                pst.setDouble(4, roomArea);
                pst.setInt(5, attic);
                pst.setInt(6, roomStatus);
                pst.setInt(7, price);



                if (pst.executeUpdate() > 0) {
                    int room_Id = -1;
                    rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        room_Id = rs.getInt(1);
                        System.out.println("roomId 1:" +room_Id);


                    }
                    pst=pst = cn.prepareStatement(sql2);
                    pst.setInt(1, room_Id);

                    pst.setInt(2, quantity1);
                    pst.setInt(3, room_Id);
                    pst.setInt(4, status1);


                    pst.setInt(5, quantity2);
                    pst.setInt(6, room_Id);
                    pst.setInt(7, status2);

                    pst.setInt(8, quantity3);
                    pst.setInt(9, room_Id);
                    pst.setInt(10, status3);

                    pst.setInt(11, quantity4);
                    pst.setInt(12, room_Id);
                    pst.setInt(13, status4);
                    if (pst.executeUpdate() > 0) {
                        isInserted = true;
                    } else {
                        isInserted = false;

                    }
                    System.out.println("step add imgs");
                    pst = cn.prepareStatement(ADD_IMGs);
                    for (int i = 0; i < imgUrls.size(); i++) {
                        System.out.println("roomId 2:" +room_Id);
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

    public boolean addNewManyRooms(int hostelID, int capacity, double roomArea, int attic, int roomStatus,String imgUrls,int price,
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
                        "INSERT INTO Rooms (hostel_id, room_number, capacity, room_area, has_attic, room_status, price,room_view)\n" +
                        "VALUES (?, @room_number, ?, ?, ?, ?,?,0)\n";

                String sql2 = "INSERT INTO Consumes (number_electric, number_water, update_date, status, room_id)\n" +
                        "VALUES (0, 0, GETDATE(), 0, ?)" +

                        "DECLARE @restQuantity int = ?\n" +
                        "WHILE ( @restQuantity > 0 )\n" +
                        "BEGIN\n" +
                        "\tINSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                        "\tVALUES (?, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = N'Nhà vệ sinh'))\n" +
                        "\tSET @restQuantity = @restQuantity - 1\n" +
                        "END\n" +

                        "DECLARE @windowQuantity int = ?\n" +
                        "WHILE ( @windowQuantity > 0 )\n" +
                        "BEGIN\n" +
                        "\tINSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                        "\tVALUES (?, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = N'Cửa sổ'))\n" +
                        "\tSET @windowQuantity = @windowQuantity - 1\n" +
                        "END\n" +

                        "DECLARE @doorQuantity int = ?\n" +
                        "WHILE ( @doorQuantity > 0 )\n" +
                        "BEGIN\n" +
                        "\tINSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                        "\tVALUES (?, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = N'Cửa ra vào'))\n" +
                        "\tSET @doorQuantity = @doorQuantity - 1\n" +
                        "END\n" +

                        "DECLARE @airConditionQuantity int = ?\n" +
                        "WHILE ( @airConditionQuantity > 0 )\n" +
                        "BEGIN\n" +
                        "\tINSERT INTO InfrastructuresRoom (room_id, quantity, status, id_infrastructure_item)\n" +
                        "\tVALUES (?, 1, ?, (SELECT id_infrastructure_item FROM InfrastructureItem WHERE infrastructure_name = N'Máy lạnh'))\n" +
                        "\tSET @airConditionQuantity = @airConditionQuantity - 1\n" +
                        "END";

//                "INSERT INTO Rooms (hostel_id, room_number, capacity, room_area, has_attic, room_status, price,room_view)\n" +
//                        "VALUES (?, @room_number, ?, ?, ?, ?,?,0)\n";
                pst = cn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1, hostelID);
                pst.setInt(2, hostelID);
                pst.setInt(3, capacity);
                pst.setDouble(4, roomArea);
                pst.setInt(5, attic);
                pst.setInt(6, roomStatus);
                pst.setInt(7, price);




                if (pst.executeUpdate() > 0) {
                    int room_Id = -1;
                    rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        room_Id = rs.getInt(1);
                        System.out.println("rooom id : " + room_Id);
                    }

                    pst = cn.prepareStatement(sql2);
                    pst.setInt(1, room_Id);

                    pst.setInt(2, quantity1);
                    pst.setInt(3, room_Id);
                    pst.setInt(4, status1);


                    pst.setInt(5, quantity2);
                    pst.setInt(6, room_Id);
                    pst.setInt(7, status2);

                    pst.setInt(8, quantity3);
                    pst.setInt(9, room_Id);
                    pst.setInt(10, status3);

                    pst.setInt(11, quantity4);
                    pst.setInt(12, room_Id);
                    pst.setInt(13, status4);
                    if (pst.executeUpdate() > 0) {
                        isInserted = true;
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
                    } else {
                        isInserted = false;

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

    public boolean updateRoom(int roomID, int roomNumber, int capacity, double roomArea, int hasAttic,List<String> imgUrls, int price) {
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
                if (imgUrls!= null){
                    System.out.println("step 1 - get list url img from db and delete from cloudinary");
                    String sql ="SELECT url_img\n" +
                            "FROM imgURL\n" +
                            "WHERE room_id = ?;";

                    pst = cn.prepareStatement(sql);
                    pst.setInt(1, roomID);
                    rs = pst.executeQuery();
                    if (rs != null) {
                        while (rs.next()) {
                            imageUrls.add(rs.getString("url_img"));


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

                    System.out.println("step 2 - delete imgURL from database");
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
                        "SET room_number = ?, capacity = ?, room_area = ?, has_attic = ?, price = ?\n" +
                        "WHERE room_id = ?";

                pst = cn.prepareStatement(sqlUpdateRoom);
                pst.setInt(1, roomNumber);
                pst.setInt(2, capacity);
                pst.setDouble(3, roomArea);
                pst.setInt(4, hasAttic);
                pst.setInt(5, price);
                pst.setInt(6, roomID);

                if (pst.executeUpdate() == 0) {
                    cn.rollback();
                } else {
                    if (imgUrls != null){
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
//DatabaseConnector
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
            String sql = "SELECT url_img\n" +
                    "FROM imgURL\n" +
                    "where room_id = "+rid;

            pst = cn.prepareStatement(sql);

            rs = pst.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    imgs.add(rs.getString("url_img"));
                }
            }else {imgs=null;}
        }
    } catch (Exception e) {
        System.out.println("getListImgByRoomId error");
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
    public Room getRoomById(int roomId) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Room roomInfor = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(
                        "SELECT R.[room_id], R.[room_number], R.[room_area], R.[capacity], R.[has_attic], R.[hostel_id], R.[room_status],R.[room_view],R.[price]\n" +
                                "FROM [dbo].[Rooms] AS R join hostels on R.[hostel_id] = hostels.hostel_id\n" +
                                "WHERE R.[room_id]= ? and hostels.status = 0");
                pst.setInt(1, roomId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int roomID = rs.getInt("room_id");
                    int hostelId = rs.getInt("hostel_id");
                    int roomNumber = rs.getInt("room_number");
                    double roomArea = rs.getInt("room_area");
                    int capacity = rs.getInt("capacity");
                    int hasAttic = rs.getInt("has_Attic");
                    int roomStatus = rs.getInt("room_status");
                    int room_view = rs.getInt("room_view");
                    int price = rs.getInt("price");
                    List<String> imgUrl = new ArrayList<>();
                    imgUrl.add(getListImgByRoomId(roomID).get(0));
                    roomInfor = Room
                            .builder()
                            .roomId(roomID)
                            .hostelId(hostelId)
                            .roomNumber(roomNumber)
                            .roomArea(roomArea)
                            .capacity(capacity)
                            .hasAttic(hasAttic)
                            .roomStatus(roomStatus)
                            .roomView(room_view)
                            .price(price)
                            .imgUrl(imgUrl)
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
        System.out.println("getRoomInformationByRoomId");
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Room room = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT room_id, H.hostel_id, room_number, capacity, room_status, room_area, has_attic, name, address, ward,room_view , district, city,R.price , H.owner_account_id\n" +
                        "FROM Rooms R JOIN Hostels H ON R.hostel_id = H.hostel_id\n" +
                        "WHERE R.room_id = ? and H.status = 0\n";


                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);


                rs = pst.executeQuery();
                if (rs != null && rs.next()) {

                    int hostelId = rs.getInt("hostel_id");
                    int roomNumber = rs.getInt("room_number");
                    int capacity = rs.getInt("capacity");
                    int roomStatus = rs.getInt("room_status");
                    int price = rs.getInt("price");
                    int room_view = rs.getInt("room_view");
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
                            .price(price)
                            .roomView(room_view)
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

    public List<Room> getListRoomsByCondition(String city, String district, String ward, String inputText,int page, int page_Size,int lowPrice,int highPrice,int area,int hostelID,String expiration) {
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
                        "    ward, rooms.price,rooms.room_view,\n" +
                        "    district, hostels.hostel_id\n"+" ORDER BY \n" +

                        "    rooms.room_id ASC\n";
                String sql = "SELECT \n" +
                        "    rooms.room_id, \n" +
                        "    rooms.price, rooms.room_view, \n" +
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
                        "    MIN(imgURL.url_img) AS imgUrl,\n" +
                        "    count(imgURL.url_img) as count_img, hostels.hostel_id\n" +
                        "    \n" +
                        "FROM \n" +
                        "    rooms \n" +
                        "JOIN \n" +
                        "    hostels ON rooms.hostel_id = hostels.hostel_id \n" +
                        "left JOIN \n" +
                        "    contracts ON rooms.room_id = contracts.room_id \n" +
                        "JOIN \n" +
                        "    imgURL ON rooms.room_id = imgURL.room_id \n  where 1=1 and hostels.status = 0 ";


                if(!inputText.isEmpty()){

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
                if  (!city.equalsIgnoreCase("all") && !city.isEmpty()) {
                    System.out.println("CITY NOT EMPTY");
//                    String c = "Thành Phố Hà Nội";
//                    if(c.equalsIgnoreCase(city)){
//                        System.out.println(c + " == " +city);
//                    }else System.out.println(c + " != " +city);
                    sql += " AND( hostels.city LIKE '" + city + "'";
                    if (!district .equalsIgnoreCase("all") && !district.isEmpty() && !city.isEmpty()) {
                        System.out.println("district not empty : " +district);

                        sql += " AND hostels.district LIKE '" + district + "'";
                        if (!ward .equalsIgnoreCase("all") && !district.isEmpty() && !city.isEmpty()) {
                            System.out.println("ward not empty : " + ward);

                            sql += " AND hostels.ward = '" + ward + "'";
                        }

                    }
                    sql+=")\n";



                }
                if (!expiration.isEmpty()){
                    sql += " AND Contracts.expiration < " + "'"+expiration+"'"+"or Contracts.start_date > "+ "'"+expiration+"'"+"  or Contracts.expiration is null";
                }
                if (lowPrice>0){
                    sql += " AND rooms.price >= " + lowPrice;
                }
                if (highPrice>0){
                    sql += " AND rooms.price <= " + highPrice;
                }
                if (area>0){
                    sql += " AND rooms.room_area = " + area;
                }
                if (hostelID>0){
                    sql += " AND rooms.hostel_id = " + hostelID;
                }
                sql+=groupBySql;
                sql+=" OFFSET ("+page+" - 1) * "+page_Size+" ROWS\n" +
                        " FETCH NEXT  "+page_Size+" ROWS ONLY;\n";
//                System.out.println(sql);

                pst = cn.prepareStatement(sql);


                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");
                        int price = rs.getInt("price");
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
                        int room_view = rs.getInt("room_view");
                        int hostel_id = rs.getInt("hostel_id");

                        List<String> imgList = new ArrayList<>();
                        imgList.add(img);

                        RoomInformation roomInformation = new RoomInformation(hname,address,ward2,district2,city2);


                        rooms.add(Room.builder()
                                .roomId(roomID)
                                .price(price)
                                .hostelId(imgNumber)
                                .roomNumber(roomNumber)
                                .roomArea(roomArea)
                                .capacity(capacity)
                                .roomStatus(roomStatus)
                                .hasAttic(hasAttic)
                                .roomInformation(roomInformation)
                                .imgUrl(imgList)
                                .roomView(room_view).hostelId(hostel_id)
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

    public boolean checkRoomExist(int roomNumber,int hostelID,int updateRoomNumber){
        boolean isExist = false;
        System.out.println("-> checkRoomExist ");
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;
        String sql="";


    sql = "SELECT COUNT(*) AS count_roomber\n" +
            "FROM rooms \n" +
            "JOIN \n" +
            "    hostels ON rooms.hostel_id = hostels.hostel_id \n" +
            "WHERE room_number = ? and hostel_id=? and room_number <> ? and hostels.status = 0;";


        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(sql);
//                System.out.println(sql);
                pst.setInt(1, roomNumber);
                pst.setInt(2, hostelID);
                pst.setInt(3, updateRoomNumber);

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
    public static Date get_end_date_by_RoomId(int rid) {
//        System.out.println("-> get_end_date_by_RoomId ");
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
                    endDate = rs.getDate("expiration");
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
    public static Date get_start_date_by_RoomId(int rid) {
//        System.out.println("-> get_start_date_by_RoomId ");
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Contract contract = null;
        Date endDate = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_START_DATE_ROOM_ID);
//                System.out.println(GET_CONTRACT_BY_ROOM_ID);
                pst.setInt(1, rid);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    endDate = rs.getDate("start_date");
                }
            }
        } catch (Exception e) {
            System.out.println("get_start_date_by_RoomId error");
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
                String sql = "SELECT top 5 \n" +
                        "    rooms.room_id, \n" +
                        "    room_number, \n" +
                        "    capacity, \n" +
                        "    room_area,room_view, \n" +
                        "    has_attic, \n" +
                        "    room_status,\n" +
                        "    hostels.name, \n" +
                        "    address,\n" +
                        "    city,\n" +
                        "    ward,\n" +
                        "    district, price,\n" +
                        "    MIN(imgURL.url_img) AS imgUrl,\n" +
                        "    count(imgURL.url_img) as count_img\n" +
                        "    \n" +
                        "FROM \n" +
                        "    rooms \n" +
                        "JOIN \n" +
                        "    hostels ON rooms.hostel_id = hostels.hostel_id \n" +
                        "JOIN \n" +
                        "    imgURL ON rooms.room_id = imgURL.room_id \n" +
                        " WHERE hostels.status = 0 and rooms.room_id <> ? and (rooms.price BETWEEN rooms.price-500000 AND rooms.price+1000000) and rooms.hostel_id = (select hostel_id from rooms where room_id = ?)\n"+
                        "GROUP BY \n" +
                        "    rooms.room_id, \n" +
                        "    room_number, \n" +
                        "    capacity, \n" +
                        "    room_area,room_view, \n" +
                        "    has_attic, \n" +
                        "    room_status,price,\n" +
                        "    hostels.name, \n" +
                        "    address,\n" +
                        "    city,\n" +
                        "    ward,\n" +
                        "    district \n"+"ORDER BY \n" +
                        "    rooms.room_id DESC;";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, rid);
                pst.setInt(2, rid);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");
                        int price = rs.getInt("price");
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
                        int room_view = rs.getInt("room_view");

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
                                .price(price)
                                .roomView(room_view)
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
                String sql ="SELECT url_img\n" +
                        "FROM imgURL\n" +
                        "WHERE room_id = ?;";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);
                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        imageUrls.add(rs.getString("url_img"));


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
                String sqlDeleteImg = "DELETE FROM imgURL WHERE room_id = ?";

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
                        "    hostels ON rooms.hostel_id = hostels.hostel_id \n where 1=1 and hostels.status = 0 " ;


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

    public List<Integer> getRoomArea() {
        System.out.println("getRoomArea");
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Integer> room_area = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                //room_id	property_id	room_number	room_area	attic	room_status
                String sql = "select distinct room_area from Rooms";

                pst = cn.prepareStatement(sql);


                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        room_area.add(rs.getInt("room_area"));
                    }
                }else {room_area=null;}




            }
        } catch (Exception e) {
            System.out.println("error room_area");
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
        return room_area;
    }


    public boolean updateRoomStatus(int room_id,int status){
        boolean st = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "update [Rooms]\n" +
                        "set room_status = ?\n" +
                        "where room_id = ?";
                pst = cn.prepareStatement(sql);

                pst.setInt(1, status);
                pst.setInt(2, room_id);

                if (pst.executeUpdate() > 0) {
                    st = true;
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
        return st;
    }

    public int getRoomStatusByContractAndEmail(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        int status = 2;
        try {

            conn = DatabaseConnector.makeConnection();

            if (conn != null) {
                String sql = "select r.room_status from [Rooms] r\n" +
                        "join [Contracts] c on r.room_id = c.room_id\n" +
                        "join [Accounts] a on a.account_id = c.renter_id\n" +
                        "join [AccountInformations] ai on ai.account_id = a.account_id\n" +
                        "where ai.email = ? and c.status = 1";

                psm = conn.prepareStatement(sql);
                psm.setString(1, email);
                rs = psm.executeQuery();
                if (rs != null && rs.next()) {
                    status = rs.getInt("room_status");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) { rs.close(); }
            if (psm != null) { psm.close(); }
            if (conn != null) { conn.close(); }
        }
        return status;
    }

    public Room getRoomInfoByRenterId(int renterId) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Room roomInfo = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT R.* FROM Rooms AS R INNER JOIN Contracts AS C \n" +
                        "ON R.room_id = C.room_id WHERE C.renter_id = ?";

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

    public List<Room> getListRoomsByHostelOwnerId(int hostelOwnerID) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT room_id, Hostels.hostel_id as 'hostel_id', room_number, room_status\n" +
                        "FROM Hostels, Rooms\n" +
                        "WHERE Hostels.owner_account_id = ? and Hostels.status =0\n" +
                        "AND Hostels.hostel_id = Rooms.hostel_id\n";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, hostelOwnerID);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");
                        int hosteLId = rs.getInt("hostel_id");
                        int roomNumber = rs.getInt("room_number");
                        int roomStatus = rs.getInt("room_status");
                        rooms.add(Room.builder()
                                .roomId(roomID)
                                .hostelId(hosteLId)
                                .roomNumber(roomNumber)
                                .roomStatus(roomStatus)
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

    public void addView(int roomID){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                String sql = "update rooms set room_view = room_view + 1\n" +
                        "FROM Rooms\n" +
                        "WHERE room_id = ?\n";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);

                pst.executeUpdate();

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
    }

    public Room getRoomMostView() throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Room roomInfo = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT TOP 1 *\n" +
                        "FROM rooms\n" +
                        "ORDER BY room_view DESC;\n";

                pst = cn.prepareStatement(sql);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int room_id = rs.getInt("room_id");
                    int hostel_id = rs.getInt("hostel_id");
                    int roomNumber = rs.getInt("room_number");
                    double roomArea = rs.getInt("room_area");
                    int capacity = rs.getInt("capacity");
                    int roomView = rs.getInt("room_view");
                    int price = rs.getInt("price");
                    List<String> imgUrl = new ArrayList<>();
                    imgUrl.add(getListImgByRoomId(room_id).get(0));
                    roomInfo = Room
                            .builder()
                            .roomId(room_id)
                            .hostelId(hostel_id)
                            .roomNumber(roomNumber)
                            .capacity(capacity)
                            .roomArea(roomArea)
                            .roomView(roomView)
                            .price(price)
                            .imgUrl(imgUrl)
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
    public Room getbudgetRoom(int hostelId) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Room roomInfo = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT *\n" +
                        "FROM rooms\n" +
                        "SELECT TOP 1 *\n" +
                        "FROM rooms\n" +
                        "JOIN \n" +
                        "    hostels ON rooms.hostel_id = hostels.hostel_id \n "+
                        "WHERE price = (SELECT MIN(price) FROM rooms) and rooms.hostel_id = ? and hostels.status = 0\n" +
                        "ORDER BY room_area DESC;";

                pst = cn.prepareStatement(sql);
                pst.setInt(1,hostelId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int room_id = rs.getInt("room_id");
                    int hostel_id = rs.getInt("hostel_id");
                    int roomNumber = rs.getInt("room_number");
                    double roomArea = rs.getInt("room_area");
                    int capacity = rs.getInt("capacity");
                    int roomView = rs.getInt("room_view");
                    int price = rs.getInt("price");
                    List<String> imgUrl = new ArrayList<>();
                    imgUrl.add(getListImgByRoomId(room_id).get(0));
                    roomInfo = Room
                            .builder()
                            .roomId(room_id)
                            .hostelId(hostel_id)
                            .roomNumber(roomNumber)
                            .capacity(capacity)
                            .roomArea(roomArea)
                            .roomView(roomView)
                            .price(price)
                            .imgUrl(imgUrl)
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
    public void updateRecentlyRoomId(int roomID, int accId){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                String sql = "update Accounts set recently_Room = ? where account_id = ?";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);
                pst.setInt(2, accId);

                pst.executeUpdate();

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
    }

    public void updateExpiredDateRoomId(int accId){
        // update ngay dang nhap moi nhat
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                String sql = "update Accounts set expired_date = GETDATE() where account_id = ?";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, accId);

                pst.executeUpdate();

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
    }
}


