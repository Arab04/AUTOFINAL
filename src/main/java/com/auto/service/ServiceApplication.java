package com.auto.service;

import com.auto.service.firebase.MessageSender;
import com.auto.service.searchEngine.DataInitializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@NoArgsConstructor
public class ServiceApplication implements CommandLineRunner {

	private DataInitializer dataInitializer;
	private MessageSender messageSender;

	@Autowired
	public ServiceApplication(DataInitializer dataInitializer, MessageSender messageSender) {
		this.dataInitializer = dataInitializer;
		this.messageSender = messageSender;
	}

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//messageSender.notification("eZHzvk6WSTym__MPV24WG3:APA91bE93Q8voj56wp4Hh5Khb6ulvRUVPqhSTrl3FdBfKRe3mD9hiAhhRi66I-Y3zxsg_Ug5Z-gUQ2BFfjNC1kqB3NXyYVa8jgL2Go5Ixak7Llr2LGWD8hyDOrMv1Soh3GBPv4Zv21Pw","Notification","Qabul qilib olish");
		dataInitializer.initialize();
	}
}
