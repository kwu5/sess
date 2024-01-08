package com.example.sess;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

import com.example.sess.models.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SessApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	//Read
	@Test
	void shouldReturnATaskWhenATaskIsSaved() {
		ResponseEntity<String> response = testRestTemplate
				.withBasicAuth("john", "pwd123")
				.getForEntity("/tasks/99", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(99);
		String time = documentContext.read("$.time");
		assertThat(time).isEqualTo("1/1");
		
		ResponseEntity<String> badresponse = testRestTemplate
				.withBasicAuth("john", "pwd123")
				.getForEntity("/tasks/1199", String.class);

		assertThat(badresponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldReturnAllTasksWhenListIsRequested(){
		ResponseEntity<String> response = testRestTemplate
			.withBasicAuth("john","pwd123")
			.getForEntity("/tasks", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int taskCount = documentContext.read("$.length()");
		assertThat(taskCount).isEqualTo(3);

		JSONArray ids = documentContext.read("$..id");
		assertThat(ids).containsExactlyInAnyOrder(99,100,102);
		JSONArray times = documentContext.read("$..time");
		assertThat(times).containsExactlyInAnyOrder("1/1","2/1","4/1");
	}

	@Test
	void shouldNotReturnTaskWithUnknownId(){
		ResponseEntity<String> response = testRestTemplate
			.withBasicAuth("john", "pwd123")
			.getForEntity("/tasks/10000", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);	
		assertThat(response.getBody()).isBlank();
	}

	@Test
	void shouldReturnPageOfTasks(){
		ResponseEntity<String> response = testRestTemplate
			.withBasicAuth("john", "pwd123")
			.getForEntity("/tasks?page=0&size=1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		// System.out.println(response.getBody());

		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);

	}

	@Test
	void ShouldReturnPageOfTasksDesc(){
		ResponseEntity<String> response = testRestTemplate
			.withBasicAuth("john", "pwd123")
			.getForEntity("/tasks?page=0&size=1&sort=time,desc", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		// System.out.println(response.getBody());

		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);

		String time = documentContext.read("$[0].time");
		assertThat(time).isEqualTo("4/1");

	}

	@Test
	void ShouldReturnPageOfTasksASC(){
		ResponseEntity<String> response = testRestTemplate
			.withBasicAuth("john", "pwd123")
			.getForEntity("/tasks?page=0&size=1&sort=time,asc", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		// System.out.println(response.getBody());

		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);

		String time = documentContext.read("$[0].time");
		assertThat(time).isEqualTo("1/1");
	}

	@Test
	void shouldReturnASortedPageOfTaskWithNoParametersAndUseDefaultValues(){
		ResponseEntity<String> response = testRestTemplate
			.withBasicAuth("john", "pwd123")
			.getForEntity("/tasks", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(3);

		JSONArray times = documentContext.read("$..time");
		assertThat(times).containsExactly("1/1","2/1","4/1");

	}

	//create
	@Test
	@DirtiesContext
	void shouldCreateANewTask(){
		Task newTask = new Task(110L, "5/1", "john");
		ResponseEntity<Void> createResponse = testRestTemplate
			.withBasicAuth("john", "pwd123")
			.postForEntity("/tasks", newTask, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewTask = createResponse.getHeaders().getLocation();
		ResponseEntity<String> response = testRestTemplate
			.withBasicAuth("john", "pwd123")
			.getForEntity(locationOfNewTask, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number idNumber = documentContext.read("$.id");
		assertThat(idNumber).isNotNull();
		String time = documentContext.read("$.time");
		assertThat(time).isEqualTo("5/1");
	}


	//update
	




	//security
	@Test
	void shouldNotReturnTaskWithBadCredentials(){
		ResponseEntity<String> response = testRestTemplate
				.withBasicAuth("IamBad", "pwd123")
				.getForEntity("/tasks/99", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

		ResponseEntity<String> response1 = testRestTemplate
			.withBasicAuth("IamStillBad", "pwd123")
			.getForEntity("/tasks", String.class);
		assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	void shouldRejectUsersWhoIsNotTaskOwner(){
		ResponseEntity<String> response = testRestTemplate
				.withBasicAuth("jane", "abc456")
				.getForEntity("/tasks/99", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	// @Test
	// void shouldNotAllowAccessToTaskTheyDoNotOwn(){
	// 	ResponseEntity<String> response = testRestTemplate
	// 			.withBasicAuth("jane", "abc456")
	// 			.getForEntity("/tasks/101", String.class);
	// 	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
	// }

	

}
