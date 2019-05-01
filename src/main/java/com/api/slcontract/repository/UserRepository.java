package com.api.slcontract.repository;

import com.api.slcontract.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByUsername(String username);
}
