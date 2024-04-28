package com.searchapp.searchApp.controller;

import com.searchapp.searchApp.dto.UserDto;
import com.searchapp.searchApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user-details")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping()
    public List<UserDto> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String country) {
        return userService.searchUsers(name, age, email, mobile, country);
    }
    @PostMapping("/upload")
    public String add(@RequestBody(required = true) MultipartFile file){
        String fileFormat = file.getOriginalFilename();

        List<UserDto> userDataList = new ArrayList<>();
        if(fileFormat.endsWith(".docx")){
            try {
                XWPFDocument document = new XWPFDocument(file.getInputStream());
                int lineCount = 0;
                for (XWPFParagraph paragraph : document.getParagraphs()) {
                    if (lineCount<2) {
                        lineCount++;
                        continue;
                    }
                    String line = paragraph.getText().trim();
                    if (line.startsWith("|") && line.endsWith("|")) {
                        line = line.substring(1, line.length() - 1);
                        String[] values = line.split("\\|");
                        for (int i = 0; i < values.length; i++) {
                            values[i] = values[i].trim();
                        }
                        UserDto userDto = new UserDto(values[0], Integer.parseInt(values[1]), values[2], values[3], values[4]);
                        userDataList.add(userDto);
                    }
                }
                for (UserDto userDto  : userDataList) {
                    userService.addUserData(userDto);
                }
                document.close();
                return "{\"message\": \"Data Saved Successfully!\"}";
            }
            catch (IOException e) {
                e.printStackTrace();
                return "Error Occured";
            }
        }
        else if (fileFormat.endsWith(".txt")) {
            System.out.println("format "+ fileFormat);
            int lineCount = 0;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (lineCount < 2) {
                        lineCount++;
                        continue;
                    }
                    String[] values = line.trim().split("\\|");
                    for (int i = 0; i < values.length; i++) {
                        values[i] = values[i].trim();
                    }
                    if (values.length >= 5) {
                        String fullName = values[0]+values[1];
                        UserDto userDto = new UserDto(fullName, Integer.parseInt(values[2]), values[3], values[4], values[5]);
                        userDataList.add(userDto);
                    } else {
                        UserDto userDto = new UserDto(values[0], Integer.parseInt(values[1]), values[2], values[3], values[4]);
                        userDataList.add(userDto);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (UserDto userDto : userDataList) {
                userService.addUserData(userDto);
            }
            return "{\"message\": \"Data Saved Successfully!\"}";
        }
//        else{
//            return "{\"error\": \"Please upload the allowed format! (.txt, MS word .docx)\"}";
//        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//
    }

}
