package com.tgerstel.mybooks.adapter.persistence;

import org.springframework.data.repository.CrudRepository;

public interface BookSpringRepository extends CrudRepository<BookEntity, String> {

}
