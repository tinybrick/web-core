package com.htche.web.it;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.htche.web.WebCoreMainClass;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebCoreMainClass.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@DirtiesContext
public class FooBarControllerIT extends IntegrationTestBase {
	@Value("${local.server.port}") private int port;

	@Test
	public void testFoo() throws Exception {
		TestRestTemplate testRestTemplate = getRestTemplate(null, null);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

		ResponseEntity<String> entity = request(testRestTemplate, "http://localhost:" + this.port + "/foo",
				HttpMethod.GET, requestEntity, String.class, false);

		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
		Assert.assertTrue(entity.getBody().contains("foo"));
	}

	@Test
	public void testBar() throws Exception {
		TestRestTemplate testRestTemplate = getRestTemplate(null, null);

		ResponseEntity<String> entity = request(testRestTemplate, "http://localhost:" + this.port + "/bar",
				HttpMethod.GET, null, String.class, false);
		//ResponseEntity<String> entity = new TestRestTemplate().getForEntity("http://localhost:" + this.port + "/bar",
		//		String.class);
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
		Assert.assertTrue(entity.getBody().contains("Expected exception in controller"));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
}
