package com.tripwise.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tripwise.backend.pojo.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

}
