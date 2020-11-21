package com.sd.shop.potatoes.repositories;

import com.sd.shop.potatoes.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
