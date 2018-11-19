package gr.charos.homeapp.finance;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import gr.charos.homeapp.finance.config.ApplicationProperties;
import gr.charos.homeapp.finance.repository.PersistentFamilyRepository;

@SpringBootApplication
@EnableConfigurationProperties({ ApplicationProperties.class})
@EnableAutoConfiguration
@EnableScheduling
public class Application implements CommandLineRunner {
	
	@Autowired
	PersistentFamilyRepository repository;

	public static void main(String[] args) throws UnknownHostException {
		SpringApplication.run(Application.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);

	}
}
