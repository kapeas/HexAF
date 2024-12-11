package com.gftinditex.process;

import com.gftinditex.process.services.impl.ProductPriceServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);
	
	
	@Test
	void contextLoads() {
	}

	@Test
	void isH2Initialized() {
		logger.debug("isH2Initialized Add a loggger....");
	}


	@Test
	void countH2Records() {
		logger.debug("countH2Records Add a loggger....");
	}
}
