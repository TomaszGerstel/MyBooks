package com.tgerstel.mybooks.adapter.persistence;

import com.tgerstel.mybooks.domain.UserRepository;
import com.tgerstel.mybooks.domain.model.Book;
import com.tgerstel.mybooks.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    UserSpringRepository springRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return springRepository.findByUsername(username).map(UserEntity::toUser);
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User save(User user) {
        return springRepository.save(UserEntity.fromUser(user)).toUser();
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public List<Book> findBooksByUserId(Long userId) {
        return List.of();
    }
}
