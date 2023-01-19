package ru.skypro.homework.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ads.CommentDto;
import ru.skypro.homework.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Override
    public CommentDto getComments(Integer id) {
        logger.info("Was invoked method for get comments");
        return null;
    }
}
