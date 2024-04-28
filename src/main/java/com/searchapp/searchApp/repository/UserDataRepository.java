package com.searchapp.searchApp.repository;

import com.searchapp.searchApp.dto.UserDto;
import com.searchapp.searchApp.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDataRepository extends JpaRepository<UserData,Integer>{
    @Query("SELECT u FROM UserData u " +
            "WHERE (:name IS NULL OR u.name = :name) " +
            "AND (:age IS NULL OR u.age = :age) " +
            "AND (:email IS NULL OR u.email = :email) " +
            "AND (:mobile IS NULL OR u.mobile = :mobile) " +
            "AND (:country IS NULL OR u.country = :country)")
    List<UserData> searchUsers(@Param("name") String name,
                               @Param("age") Integer age,
                               @Param("email") String email,
                               @Param("mobile") String mobile,
                               @Param("country") String country);
}
