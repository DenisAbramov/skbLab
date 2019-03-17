package ru.dabramov.skblab.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dabramov.skblab.persistence.model.User;

/**
 * @author Dabramov
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByEmail(String email);
}
