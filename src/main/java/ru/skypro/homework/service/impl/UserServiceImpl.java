package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.User.Password;
import ru.skypro.homework.dto.User.UserDto;
import ru.skypro.homework.service.UserService;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

 //   private final UserRepository userRepository;

  /*  public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/

    public UserDto editUser(UserDto user) {
        logger.info("Was invoked method for edit user");
        return null/*userRepository.save(user)*/;
    }

    public UserDto findUser(Long id) {
        logger.info("Was invoked method for find user");
        return null/*userRepository.findById(id).orElseThrow()*/;
    }

    public Collection<UserDto> getAll() {
        logger.info("Was invoked method for get all users");
        return null/*userRepository.getAllWithoutPassword()*/;
    }

    public UserDto changePassword(Password password) {
        logger.info("Was invoked method for change password of user");
        if (password != null){
            UserDto user = null/*userRepository.getUserDtoByPassword(password.getCurrentPassword())*/;
            user.setPassword(password.getNewPassword());
            return user;
        }
        return null;
    }
}
