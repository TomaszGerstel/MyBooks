package com.tgerstel.mybooks.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookSpringRepository extends JpaRepository<BookEntity, String> {

}
