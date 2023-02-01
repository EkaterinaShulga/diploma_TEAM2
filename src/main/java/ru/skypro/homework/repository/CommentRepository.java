package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {


    List<Comment> findCommentsByAdsPk(Long adsPk);

    Comment findCommentByAdsPk(Long adsPk);

    Optional<Comment> findCommentsByAdsPkAndPk(Long adsPk, Integer pk);

}


