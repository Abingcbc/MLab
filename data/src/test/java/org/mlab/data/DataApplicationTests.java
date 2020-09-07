package org.mlab.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mlab.data.domain.oss.impl.MinIOService;
import oss.FileTypeEnum;

import java.nio.charset.StandardCharsets;

class DataApplicationTests {

	MinIOService service = new MinIOService();

	@Test
	void minioPutObject() {
		String test = "adfaasdfhasdhfoahoghasdlfhalsdhfahwoehgf";
		service.save(test.getBytes(StandardCharsets.UTF_8), "putObjectTest", FileTypeEnum.dataset);
	}

	@Test
	void minioExist() {
		Assertions.assertTrue(service.isExist("putObjectTest", FileTypeEnum.dataset));
	}

	@Test
	void minioGetObject() {
		System.out.println(new String(service.get("putObjectTest", FileTypeEnum.dataset)));
	}

	@Test
	void minioRemoveObject() {
		Assertions.assertTrue(service.remove("putObjectTest", FileTypeEnum.dataset));
		Assertions.assertFalse(service.isExist("putObjectTest", FileTypeEnum.dataset));
	}
}
