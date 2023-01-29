package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.PasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserAlreadyCreatedException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto findUser(Long id) {
        logger.info("Was invoked method for find user");
        return userMapper.toDto(userRepository.findById(id).get());
    }

    public UserDto editUser(UserDto userDto, String userLogin) {
        logger.info("Was invoked method for edit user");
        System.out.println("login = " + userLogin);
        Optional<User> optionalUser = Optional.of(getUserByLogin(userLogin));

        optionalUser.ifPresent(userEntity -> {
            userEntity.setFirstName(userDto.getFirstName());
            userEntity.setLastName(userDto.getLastName());
            userEntity.setPhone(userDto.getPhone());

            userRepository.save(userEntity);
        });

        return optionalUser
                .map(userMapper::toDto)
                .orElse(null);
    }

    public Collection<UserDto> getAll() {
        logger.info("Was invoked method for get all users");
        return userMapper.toDtoCollection(userRepository.getAll());
    }

    public UserDto changePassword(PasswordDto passwordDto) {
        logger.info("Was invoked method for change password of user");
        if (passwordDto != null){
            User user = userRepository.getUserByPassword(passwordDto.getCurrentPassword());
            if (user != null) {
                user.setPassword(passwordDto.getNewPassword());
            }
            return userMapper.toDto(user);
        }
        return null;
    }

    public User getUserByLogin(String userLogin) {
        return userRepository.findByEmail(userLogin)
                .orElseThrow(UserAlreadyCreatedException::new);
    }
}
