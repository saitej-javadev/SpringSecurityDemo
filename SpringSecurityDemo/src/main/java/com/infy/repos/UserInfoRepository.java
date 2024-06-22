package com.infy.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{

	Optional<UserInfo>findByName(String name);

}
