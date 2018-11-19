package gr.charos.homeapp.finance.controller.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import gr.charos.homeapp.finance.config.ApplicationProperties;
import gr.charos.homeapp.finance.config.ApplicationProperties.Mail;
import gr.charos.homeapp.finance.dto.TransactionDTO;
import gr.charos.homeapp.finance.service.Poller;

public class MailTest extends SpringAwareTest {
	@Autowired
	ApplicationProperties properties;
	
	@Autowired
	Poller poller;

	@Test
	public void test() throws MessagingException, IOException {
		poller.poll();
		
	}

}
