package com.tgerstel.mybooks.adapter.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserBookSpringRepository extends CrudRepository<UserBookEntity, Long> {
    void deleteByBookIdAndUserUsername(String bookId, String username);
    Optional<UserBookEntity> findByBookIdAndUserUsername(String bookId, String username);
    List<UserBookEntity> findByUserUsername(String username);
}
