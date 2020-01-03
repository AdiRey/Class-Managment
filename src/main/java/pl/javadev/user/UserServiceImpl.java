package pl.javadev.user;

import org.springframework.beans.factory.annotation.Autowired;
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
import pl.javadev.user.dto.UserDeleteDto;
import pl.javadev.user.dto.UserDto;
import pl.javadev.user.dto.UserPasswordDto;
import pl.javadev.user.dto.UserRegistrationDto;
import pl.javadev.user.mapper.UserMapper;
import pl.javadev.user.mapper.UserRegistrationMapper;
import pl.javadev.userRole.UserRole;
import pl.javadev.userRole.UserRoleRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    private UserMapper userMapper;
    private UserRepository userRepository;
    private UserRoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    @Autowired
    public void setRoleRepository(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
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
            if (!user.getPassword().equals(passwordEncoder.encode(dto.getPassword())))
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
            assert !user.getPassword().equals(passwordEncoder.encode(dto.getOldPassword()));
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            return userMapper.map(user);
        } catch (NoSuchElementException e) {
            throw new InvalidIdException();
        } catch (AssertionError e) {
            throw new ConflictPasswordException();
        }
    }

    public UserDto findById(Long id) {
        return userRepository.findById(id).map(userMapper::map).orElse(null);
    }

}
