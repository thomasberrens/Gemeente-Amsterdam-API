package org.gemeenteamsterdam.api.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gemeenteamsterdam.api.dto.CreateAccountDTO;
import org.gemeenteamsterdam.api.entities.Role;
import org.gemeenteamsterdam.api.entities.User;
import org.gemeenteamsterdam.api.repositories.RoleRepository;
import org.gemeenteamsterdam.api.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findByEmail(String email) {
        log.info("Fetching user {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user) {
        log.info("Creating new user {} to the database", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public User createUser(CreateAccountDTO createAccountDTO) {
        log.info("Creating new user {} to the database", createAccountDTO.getEmail());

        final User user = new User();
        user.setUserName(createAccountDTO.getUserName());
        user.setEmail(createAccountDTO.getEmail());
        user.setPassword(createAccountDTO.getPassword());
        user.setRoles(new HashSet<>());

        return createUser(user);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> findAll() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        final Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty())
            // TODO: check this exception
            throw new RuntimeException("User not found by email");

        final Role role = roleRepository.findByName(roleName);
        user.get().getRoles().add(role);
        userRepository.save(user.get());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByEmail(username);
        if (user.isEmpty()) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException(username);
        } else {
            log.info("User found in the database: {}", username);
        }

        final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.get().getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
                grantedAuthorities);
    }
}