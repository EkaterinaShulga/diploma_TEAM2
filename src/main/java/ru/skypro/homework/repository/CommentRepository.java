package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {


    List<Comment> getCommentsByAdsPk(Long adsPk);

    Comment getCommentByAdsPkAndPk(Long adsPk, Integer pk);

    Optional<Comment> findCommentByAdsPkAndPk(Long adsPk, Integer pk);


}


