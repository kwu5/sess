package com.example.sess.services;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.sess.dao.*;
import com.example.sess.dto.TaskUpdateRequest;
import com.example.sess.models.*;
import com.example.sess.services.UserService;
import com.example.sess.services.TaskService;
import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;

    public Task createATask(Task task) {
        if (task == null ||
                taskRepository.existsByStartTimeAndOwnerId(task.getStartTime(),
                        task.getOwnerId())) {
            return null;
        } else {
            return taskRepository.save(task);
        }
    }

    public Task getTaskById(Long id) {

        Optional<Task> task = taskRepository.findByTaskId(id);
        if (task.isPresent()) {
            return task.get();
        }
        return null;
    }

    public Task getTaskByIdAndOwnerId(Long taskId, Long owerId) {

        Optional<Task> task = taskRepository.findByTaskIdAndOwnerId(taskId, owerId);
        if (task.isPresent()) {
            return task.get();
        }
        return null;
    }

    public Page<Task> getTaskByOwner(Long ownerId, Pageable pageable) {
        return taskRepository.findByOwnerId(ownerId, pageable);
    }

    // More getter if need...

    public Optional<Task> updateTask(Long taskId, TaskUpdateRequest updateRequest, User user) {

        Optional<Task> existingtask = taskRepository.findByTaskId(taskId);

        if (existingtask.isPresent()) {

            Task task = existingtask.get();

            if (user.isAdmin()) {
                if (updateRequest.getStartTime() != null) {
                    task.setStartTime(updateRequest.getStartTime());
                }
                if (updateRequest.getEndTime() != null) {
                    task.setEndTime(updateRequest.getEndTime());
                }
                if (updateRequest.getClientId() != null) {
                    task.setClientId(updateRequest.getClientId());
                }
                if (updateRequest.getLocation() != null && !updateRequest.getLocation().isEmpty()) {
                    task.setLocation(updateRequest.getLocation());
                }
                if (updateRequest.getType() != null && !updateRequest.getType().isEmpty()) {
                    task.setType(updateRequest.getType());
                }
                if (updateRequest.getDescription() != null) {
                    task.setDescription(updateRequest.getDescription());
                }

            } else {
                if (updateRequest.getDescription() != null) {
                    task.setDescription(updateRequest.getDescription());
                }
            }
            taskRepository.save(task);
            return Optional.of(task);
        }
        return existingtask;
    }

    public Boolean deleteTaskById(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findByTaskId(taskId);
        if (taskOptional.isPresent()) {
            taskRepository.delete(taskOptional.get());
            return true;
        }
        return false;
    }

}
