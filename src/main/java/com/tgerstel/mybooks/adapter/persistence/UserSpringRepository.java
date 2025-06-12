package com.tgerstel.mybooks.adapter.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserSpringRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
