package com.example.sess.Task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

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
import com.example.sess.models.Task;
import com.example.sess.models.Task;
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
        Task1.setId(2L);
        Task2.setId(3L);
        Task3.setId(4L);
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

}
