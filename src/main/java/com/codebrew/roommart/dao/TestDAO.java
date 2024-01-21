package com.codebrew.roommart.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// Import the required packages
import java.io.File;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;
public class TestDAO {
    public static void main(String[] args) {
        RoomDAO rd = new RoomDAO();
        List<String> files = new ArrayList<>();
        files.add("F:\\AI pictures\\Grid_5.png");
        files.add("F:\\AI pictures\\tea.png");
        files.add("F:\\AI pictures\\tea2.png");
        List<String> roomFiles = new ArrayList<>();

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqp6vdayn",
                "api_key", "527664667972471",
                "api_secret", "HzMyAcz7DKbWinMpZEsLe64XkUo",
                "secure", true));
        cloudinary.config.secure = true;
        System.out.println(cloudinary.config.cloudName);
        // Set your Cloudinary credentials
        Map uploadResult=null;

// Upload the image
        Map params1 = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
        );

        try {
                for (String file : files){
                     uploadResult = cloudinary.uploader().upload(file, params1);
                    file = (String) uploadResult.get("url");
                    roomFiles.add(file);
                    System.out.println(uploadResult.get("url"));}



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(roomFiles);
        rd.addNewRoom(1,104,2,300,1,1,roomFiles);
    }
}
