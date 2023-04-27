package org.gemeenteamsterdam.api.services;

import org.gemeenteamsterdam.api.dto.CreateAccountDTO;
import org.gemeenteamsterdam.api.entities.Role;
import org.gemeenteamsterdam.api.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * Returns User with given email
     *
     * @param email
     * @return User
     */
    Optional<User> findByEmail(String email);

    /**
     * Saves given User to database
     *
     * @param user
     */
    User createUser(User user);

    /**
     * Returns list of all Users
     *
     * @return list of Users
     */
    List<User> findAll();

    Role saveRole(Role role);

    void addRoleToUser(String email, String roleName);

    User createUser(CreateAccountDTO createAccountDTO);

    Optional<User> findByUserName(String userName);

    void saveUser(User user);

    Optional<User> findById(Long userId);
}