package com.example.mydiary.dao;

import com.example.mydiary.entity.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfileRepository extends CrudRepository<UserProfile, String>{

    UserProfile findByUserName(String username);
    UserProfile findByEmailId(String emailId);

}
