package com.acterio.assessmentro.repositories;

import com.acterio.assessmentro.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    boolean existsByEmail(String email);

}
