package pl.javadev.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.javadev.exception.other.ConflictIdException;
import pl.javadev.exception.other.ConflictPasswordException;
import pl.javadev.exception.other.InvalidIdException;
import pl.javadev.exception.web.DifferentPasswordException;
import pl.javadev.exception.web.DuplicateEmailException;
import pl.javadev.user.*;
import pl.javadev.userRole.UserRole;
import pl.javadev.userRole.UserRoleRepository;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserMapper userMapper;
    private UserRepository userRepository;
    private UserRoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, UserRoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto save(UserRegistrationDto dto) {
        Optional<User> foundUser = userRepository.findByEmail(dto.getEmail());
        foundUser.ifPresent(
                u -> {throw new DuplicateEmailException();
                }
        );
        if (!dto.getPassword().equals(dto.getRepeatedPassword()))
            throw new DifferentPasswordException();
        long userNumber = userRepository.lastIndexNumber();
        User user = UserRegistrationMapper.map(dto);
        UserRole role = roleRepository.findByName("ROLE_USER");
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.getRoles().add(role);
        user.setIndexNumber(String.valueOf(++userNumber));
        User savedUser = userRepository.save(user);
        return userMapper.map(savedUser);
    }

    public UserDto delete(Long id, UserDeleteDto dto) {
        try {
            if (!id.equals(dto.getId()))
                throw new ConflictIdException();
            Optional<User> foundUser = userRepository.findById(id);
            User user = foundUser.get();
            if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
                throw new ConflictPasswordException();
            UserDto deletedUserDto = userMapper.map(user);
            userRepository.delete(user);
            return deletedUserDto;
        } catch (NoSuchElementException e) {
            throw new InvalidIdException();
        }
    }

    public List<UserDto> deleteAll() {
        List<UserDto> users = new LinkedList<>();
        for (User user : userRepository.findAll()) {
            users.add(userMapper.map(user));
        }
        userRepository.deleteAll();
        return users;
    }

    public Page<UserDto> findAllUsersUsingPaging(int numberOfPage, String sortText, String text) {
        Sort sort;
        if (sortText.equals("DESC"))
            sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "lastName"));
        else
            sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "lastName"));
        return userRepository.findAllByLastNameContainingIgnoreCase
                (text, PageRequest.of(numberOfPage, 20, sort)).map(userMapper::map);
    }

    public UserDto findUser(Long id) {
        try {
            Optional<User> foundUser = userRepository.findById(id);
            return userMapper.map(foundUser.get());
        } catch (NoSuchElementException e) {
            throw new InvalidIdException();
        }
    }

    @Transactional
    public UserDto editUser(Long id, UserDto dto) {
        try {
            if (!id.equals(dto.getId()))
                throw new ConflictIdException();
            Optional<User> foundUser = userRepository.findById(id);
            User user = foundUser.get();
            if (!dto.getEmail().equals(user.getEmail())) {
                Optional<User> foundUser2 = userRepository.findByEmail(dto.getEmail());
                foundUser2.ifPresent(
                        u -> {throw new DuplicateEmailException();
                        }
                );
            }
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setEmail(dto.getEmail());
            user.setMajor(dto.getMajor());
            user.setGrade(dto.getGrade());
            return userMapper.map(user);
        } catch (NoSuchElementException e) {
            throw new InvalidIdException();
        }
    }

    @Transactional
    public UserDto editPassword(Long id, UserPasswordDto dto) {
        try {
            if (!id.equals(dto.getId()))
                throw new ConflictIdException();
            else if (!dto.getPassword().equals(dto.getRepeatedPassword()))
                throw new DifferentPasswordException();
            Optional<User> foundUser = userRepository.findById(id);
            User user = foundUser.get();
            if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword()))
                throw new ConflictPasswordException();
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            return userMapper.map(user);
        } catch (NoSuchElementException e) {
            throw new InvalidIdException();
        }
    }
}
