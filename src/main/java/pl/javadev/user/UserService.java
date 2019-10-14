package pl.javadev.user;

import org.springframework.stereotype.Service;
import pl.javadev.exception.DuplicateIdxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::entityToDto).collect(Collectors.toList());
    }

    public UserDto save(UserDtoReg userDtoReg) {
        Optional<User> foundOne = userRepository.findByIndexNumber(userDtoReg.getIndexNumber());
        foundOne.ifPresent(
                u -> {
                    throw new DuplicateIdxException("Nie mozna duplikowac indeksow!");
                });
        User savedUser = userRepository.save(UserMapper.dtoRegToEntity(userDtoReg));
        return UserMapper.entityToDto(savedUser);
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(UserMapper::entityToDto);
    }

    public UserDto update(UserDtoReg dtoReg) {
        Optional<User> foundOne = userRepository.findByIndexNumber(dtoReg.getIndexNumber());
        foundOne.ifPresent(
                u -> {
                    if (!u.getId().equals(dtoReg.getId()))
                        throw new DuplicateIdxException("To nie to id!");
                });
        User savedUser = userRepository.save(UserMapper.dtoRegToEntity(dtoReg));
        return UserMapper.entityToDto(savedUser);
    }

    public UserDto delete(Long id) {
        UserDto deletedOne = null;
        Optional<User> foundOne = userRepository.findById(id);
        if (foundOne.isPresent()) {
            User user = foundOne.get();
            deletedOne = UserMapper.entityToDto(user);
            userRepository.delete(user);
        }
        return deletedOne;
    }

    public UserDto editPassword(Long id, String password) {
        UserDto userDto = null;
        Optional<User> foundOne = userRepository.findById(id);
        if (foundOne.isPresent()) {
            User user = foundOne.get();
            user.setPassword(password);
            User savedUser = userRepository.save(user);
            userDto = UserMapper.entityToDto(savedUser);
        }
        return userDto;
    }
}
