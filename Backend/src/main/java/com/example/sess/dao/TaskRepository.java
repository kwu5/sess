package com.example.sess.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sess.models.Task;
import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByTaskId(Long taskId);

    boolean existsByStartTimeAndOwnerId(LocalDateTime startTime, long ownerId);

    Optional<Task> findByTaskIdAndOwnerId(Long taskId, long ownerId);

    Page<Task> findByOwnerId(long ownerId, Pageable pageable);

}
