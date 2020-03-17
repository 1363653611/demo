package com.zbcn.demo.alarm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmTest {

	@Autowired
	private  EmailAlarm emailAlarm;

	@Test
	public void alarm() {
	}

	@Test
	public void testAlarm() {
		emailAlarm.alarm("zbcn810@163.com","测试一下报警。");
	}
}
