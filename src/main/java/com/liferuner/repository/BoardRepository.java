package com.liferuner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.liferuner.entity.Board;
import com.liferuner.entity.User;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
	Optional<Board> findByUser(User user);
}
