package com.searchapp.searchApp.service;

import com.searchapp.searchApp.dto.UserDto;
import com.searchapp.searchApp.entity.UserData;
import com.searchapp.searchApp.repository.UserDataRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserDataRepository userDataRepository;
    @Autowired
    private ModelMapper modelMapper;
    public void addUserData(UserDto userDto) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserData userData = this.modelMapper.map(userDto,UserData.class);
        userDataRepository.save(userData);
    }
    public List<UserDto> searchUsers(String name, Integer age, String email, String mobile, String country) {
        if (allParametersAreNull(name, age, email, mobile, country)) {
            List<UserData> userData = userDataRepository.findAll();
            this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            return userData.stream()
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .collect(Collectors.toList());
        } else {
            List<UserData> userData = userDataRepository.searchUsers(name, age, email, mobile, country);
            if(userData.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            System.out.println("userData "+ userData);
            this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            return userData.stream()
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .collect(Collectors.toList());

        }
    }
    private boolean allParametersAreNull(Object... parameters) {
        for (Object parameter : parameters) {
            if (parameter != null) {
                return false;
            }
        }
        return true;
    }
}
