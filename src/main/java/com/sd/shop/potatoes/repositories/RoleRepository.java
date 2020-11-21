package com.sd.shop.potatoes.repositories;

import com.sd.shop.potatoes.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
