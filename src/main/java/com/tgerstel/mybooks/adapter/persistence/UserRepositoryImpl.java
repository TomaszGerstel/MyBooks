package com.tgerstel.mybooks.adapter.persistence;

import com.tgerstel.mybooks.domain.UserRepository;
import com.tgerstel.mybooks.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    UserSpringRepository springRepository;

    @Override
    public Optional<User> findByUsername(final String username) {
        return springRepository.findByUsername(username).map(UserEntity::toUser);
    }

    @Override
    public User save(final User user) {
        return springRepository.save(UserEntity.fromUser(user)).toUser();
    }

    @Override
    public void delete(final User user) {
        final var userEntity = springRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getUsername()));
        springRepository.delete(userEntity);
    }

    @Override
    public boolean existsByUsername(final String username) {
        return springRepository.findByUsername(username).isPresent();
    }

}
