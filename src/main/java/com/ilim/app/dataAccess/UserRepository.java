package com.ilim.app.dataAccess;

import com.ilim.app.entities.UserEntity;
import com.ilim.app.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
