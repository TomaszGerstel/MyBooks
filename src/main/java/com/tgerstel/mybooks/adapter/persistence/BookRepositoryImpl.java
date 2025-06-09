package com.tgerstel.mybooks.adapter.persistence;

import com.tgerstel.mybooks.domain.BookRepository;
import com.tgerstel.mybooks.domain.model.Book;

import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {

    private BookSpringRepository bookSpringRepository;

    @Override
    public Optional<Book> findById(String id) {
        return bookSpringRepository.findById(id).map(BookEntity::toDomain);
    }

//    @Override
//    public List<Book> findAllForUser(String userId) {
//        return bookSpringRepository.findAllByUserId(userId)
//                .stream()
//                .map(BookEntity::toDomain)
//                .toList();
//    }

    @Override
    public void save(Book book) {

    }

    @Override
    public void deleteById(String id) {
        bookSpringRepository.deleteById(id);
    }

}
