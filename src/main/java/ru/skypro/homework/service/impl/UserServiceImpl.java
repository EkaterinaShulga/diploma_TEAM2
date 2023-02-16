package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.PasswordDto;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserAlreadyCreatedException;
import ru.skypro.homework.exception.UserHasNoRightsException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

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

    /**
     * Find of user by id
     *
     * @param id user id
     * @return user
     */
    public UserDto findUser(Long id) {
        logger.info("Was invoked method for find user");
        return userMapper.toDto(userRepository.findById(id).get());
    }

    /**
     * Edit user
     *
     * @param userDto   data of user
     * @param userLogin login of user
     * @return updated user
     */
    public UserDto editUser(UserDto userDto, String userLogin) {
        logger.info("Was invoked method for edit user");
        System.out.println("login = " + userLogin);
        Optional<User> optionalUser = Optional.of(getUserByLogin(userLogin));

        optionalUser.ifPresent(userEntity -> {
           userMapper.toEntity(userDto);
            userRepository.save(userEntity);
        });

        return optionalUser
                .map(userMapper::toDto)
                .orElse(null);
    }

    /**
     * Getting all user
     *
     * @return Collection of User
     */
    public UserDto getUser(String userLogin) {
        logger.info("Was invoked method for get current user");
        return userMapper.toDto(getUserByLogin(userLogin));
    }

    /**
     * Changing password by user
     *
     * @param passwordDto dto for password
     * @param userLogin   username of user
     * @return updated password
     */
    public UserDto changePassword(PasswordDto passwordDto, String userLogin) {
        logger.info("Was invoked method for change password of user");
        if (passwordDto != null) {
            User user = userRepository.getUserByPassword(passwordDto.getCurrentPassword());
            if (user != null) {
                user.setPassword(passwordDto.getNewPassword());
            }
            return userMapper.toDto(user);
        }
        return null;
    }

    /**
     * Getting user by username
     *
     * @param userLogin username of user
     * @return user
     */
    public User getUserByLogin(String userLogin) {

        return userRepository.findByEmail(userLogin)
                .orElseThrow(UserAlreadyCreatedException::new);
    }

    @Override
    public boolean checkUserPermission(Authentication authentication, String username) {
        boolean matchUser = authentication.getName().equals(username);
        boolean userIsAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().contains(Role.ADMIN.name()));

        if (!(userIsAdmin || matchUser)) {
            logger.warn("Current user has NO rights to perform this operation.");
            throw new UserHasNoRightsException("Current user has NO rights to perform this operation.");
        }
        return true;
    }


}