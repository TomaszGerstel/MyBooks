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
    public Optional<User> findByUsername(String username) {
        return springRepository.findByUsername(username).map(UserEntity::toUser);
    }

    @Override
    public User save(User user) {
        return springRepository.save(UserEntity.fromUser(user)).toUser();
    }

    @Override
    public void delete(User user) {
        UserEntity userEntity = springRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + user.getUsername()));
        springRepository.delete(userEntity);
    }

    @Override
    public boolean existsByUsername(String username) {
        return springRepository.findByUsername(username).isPresent();
    }

}
