package com.example.bookingclinic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootTest
class BookingclinicApplicationTests {

	@MockBean
    private SimpMessagingTemplate messagingTemplate;

	@Test
	void contextLoads() {
	}

}
