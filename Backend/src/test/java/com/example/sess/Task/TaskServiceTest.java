package com.example.sess.Task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.example.sess.dao.TaskRepository;
import com.example.sess.dto.TaskUpdateRequest;
import com.example.sess.models.Task;
import com.example.sess.models.User;
import com.example.sess.services.TaskService;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

        @Mock
        private TaskRepository taskRepository;

        @InjectMocks
        private TaskService taskService;

        private Task testTask;
        private static Page<Task> testtaskPage;

        @BeforeEach
        void setup() {
                testTask = new Task("01/01/2024 10:30:00", "01/01/2024 11:00:00", 1L, 51L,
                                "33 hl st, San Francisco, CA, 94444",
                                "appointment", "happy day");
                testTask.setTaskId(1L);
                testtaskPage = createTestTaskPage();
        }

        private Page<Task> createTestTaskPage() {
                java.util.List<Task> tasksList = new java.util.ArrayList<>();
                Task Task1 = new Task("02/01/2024 14:30:00", "02/01/2024 17:33:20", 1L, 51L,
                                "1234 st, San Francisco, CA, 94444", "appointment", null);
                Task Task2 = new Task("03/03/2024 14:30:00", "03/04/2024 18:23:55", 1L, 52L,
                                "3456 st, San Francisco, CA, 94444", "appointment", "sad day");
                Task Task3 = new Task("04/01/2024 12:30:00", "04/01/2024 13:23:30", 2L, 52L,
                                "6789 st, San Francisco, CA, 94444", "event", "no comment");
                Task1.setTaskId(2L);
                Task2.setTaskId(3L);
                Task3.setTaskId(4L);
                tasksList.add(Task1);
                tasksList.add(Task2);
                tasksList.add(Task3);

                PageRequest pageRequest = PageRequest.of(0, 10);
                Page<Task> TaskPage = new PageImpl<>(tasksList, pageRequest,
                                tasksList.size());
                return TaskPage;
        }

        @Test
        void test_creatOneTask() throws Exception {
                when(taskRepository.save(any(Task.class))).thenReturn(testTask);
                Task createdTask = taskService.createATask(testTask);

                assertThat(createdTask).isNotNull();
                assertThat(createdTask).isEqualTo(testTask);
        }

        @Test
        void test_getTaskById_existedTask() {

                Optional taskOptional = Optional.of(testTask);
                when(taskRepository.findByTaskId(any(Long.class))).thenReturn(taskOptional);
                Task returnedTask = taskService.getTaskById(testTask.getTaskId());
                assertThat(returnedTask).isNotNull();
                assertThat(returnedTask).isEqualTo(testTask);

        }

        @Test
        void test_getTaskById_NonexistedTask() {

                Optional taskOptional = Optional.empty();
                when(taskRepository.findByTaskId(any(Long.class))).thenReturn(taskOptional);

                Task returnedTask = taskService.getTaskById(testTask.getTaskId());
                assertThat(returnedTask).isNull();

        }

        @Test
        void test_getTaskByIdAndOwnerId() {

                Optional taskOptionalExisted = Optional.of(testTask);
                Optional taskOptionalNull = Optional.empty();

                when(taskRepository.findByTaskIdAndOwnerId(testTask.getTaskId(), testTask.getOwnerId()))
                                .thenReturn(taskOptionalExisted);
                Task returnedTask = taskService.getTaskByIdAndOwnerId(testTask.getTaskId(), testTask.getOwnerId());
                assertThat(returnedTask).isNotNull();
                assertThat(returnedTask).isEqualTo(testTask);

                when(taskRepository.findByTaskIdAndOwnerId(1000L, 999L))
                                .thenReturn(taskOptionalNull);
                returnedTask = taskService.getTaskByIdAndOwnerId(1000L, 999L);
                assertThat(returnedTask).isNull();
        }

        @Test
        void test_getTaskByOwner() {

                PageRequest pageRequest = PageRequest.of(0, 200);
                when(taskRepository.findByOwnerId(1L, pageRequest)).thenReturn(testtaskPage);
                Page<Task> returnedPage = taskService.getTaskByOwner(1L, pageRequest);
                assertThat(returnedPage).isNotEmpty();
                assertThat(returnedPage).isEqualTo(testtaskPage);

                Page<Task> emptyPage = Page.empty();
                when(taskRepository.findByOwnerId(1000L, pageRequest)).thenReturn(emptyPage);
                returnedPage = taskService.getTaskByOwner(1000L, pageRequest);
                assertThat(returnedPage).isEmpty();

        }

        @Test
        void test_updateTask_ExistingTask_adminUser() {

                TaskUpdateRequest updateRequest = new TaskUpdateRequest(
                                LocalDateTime.of(2023, 10, 10, 14, 0),
                                LocalDateTime.of(2023, 10, 10, 16, 0),
                                100L,
                                "Updated Location",
                                "Updated Type",
                                "Updated Description");
                User adminUser = new User();
                adminUser.setRole("ADMIN");
                when(taskRepository.findByTaskId(any(Long.class))).thenReturn(Optional.of(testTask));

                Optional<Task> updatedTaskOpt = taskService.updateTask(testTask.getTaskId(), updateRequest, adminUser);
                assertThat(updatedTaskOpt).isNotEmpty();
                Task task = updatedTaskOpt.get();
                assertThat(task.getClientId()).isEqualTo(updateRequest.getClientId());
                assertThat(task.getDescription()).isEqualTo(updateRequest.getDescription());
        }

        @Test
        void test_updateTask_ExistingTask_NormalUser() {

                TaskUpdateRequest updateRequest = new TaskUpdateRequest(
                                LocalDateTime.of(2023, 10, 10, 14, 0),
                                LocalDateTime.of(2023, 10, 10, 16, 0),
                                100L,
                                "Updated Location",
                                "Updated Type",
                                "Updated Description");
                User adminUser = new User();
                adminUser.setRole("ADMIN");
                when(taskRepository.findByTaskId(any(Long.class))).thenReturn(Optional.of(testTask));

                Optional<Task> updatedTaskOpt = taskService.updateTask(testTask.getTaskId(), updateRequest, adminUser);
                assertThat(updatedTaskOpt).isNotEmpty();
                Task task = updatedTaskOpt.get();
                assertThat(task.getClientId()).isEqualTo(testTask.getClientId());
                assertThat(task.getDescription()).isEqualTo(updateRequest.getDescription());
        }

        @Test
        void test_deleteTaskById_ExistTask() {
                Optional<Task> TaskOptional = Optional.of(testTask);
                when(taskRepository.findByTaskId(any(Long.class))).thenReturn(TaskOptional);
                assertTrue(taskService.deleteTaskById(testTask.getClientId()));
        }

}
