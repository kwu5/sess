package com.example.sess.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sess.models.Task;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {

    Optional<Task> findById(Long id);

    Task findByIdAndOwnerId(Long id, long ownerId);

    Page<Task> findByOwnerId(long ownerId, PageRequest pageRequest);

    boolean existsByStartTimeAndOwnerId(LocalDateTime startTime, long ownerId);

    boolean existsByIdAndOwnerId(Long requestId, long ownerId);

    boolean existsByStartTimeAndEndTimeAndOwnerIdAndClientIdAndLocationAndTypeAndDescription(LocalDateTime startTime,
            LocalDateTime endTime, long ownerId, Long clientId, String location, String type, String description);

}
