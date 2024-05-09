package org.pwr.onlinecityticketsbackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
class OnlineCityTicketsBackendApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void simpleTest() {
		assertEquals(4, 2+2);
	}

}
