package gr.charos.homeapp.finance.service.impl;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import gr.charos.homeapp.finance.config.ApplicationProperties;
import gr.charos.homeapp.finance.config.ApplicationProperties.Mail;
import gr.charos.homeapp.finance.service.IncomingTransactionService;
import gr.charos.homeapp.finance.service.Poller;
import gr.charos.homeapp.finance.service.model.RawTransactionRequest;

@Service
public class IncomingMailTransactionService extends IncomingTransactionService implements Poller {
	@Autowired
	ApplicationProperties applicationProperties;

	private void pollForMails() {
		applicationProperties.getMails().forEach(m -> {
			try {
				Folder inbox = getInbox(m);
				inbox.open(Folder.READ_WRITE);

				Message[] messages = inbox.getMessages();
				List<RawTransactionRequest> requests = getTransactionRequests(messages);
				requests.forEach(rtr -> {
					this.createTransactions(rtr, m.getFamilyCode(), m.getMemberCode());
				});

				inbox.close(false);
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}

		});

	}

	private List<RawTransactionRequest> getTransactionRequests(Message[] messages)
			throws IOException, MessagingException {
		List<RawTransactionRequest> transactions = new ArrayList<>();
		for (int i = 0; i < messages.length; i++) {
			if (!messages[i].isSet(Flags.Flag.SEEN)) {
				Message mm = messages[i];
				String message = null;

				if (mm.getContent() instanceof Multipart) {
					Multipart mp = (Multipart) mm.getContent();
					for (int j = 0; j < mp.getCount(); j++) {
						BodyPart bodyPart = mp.getBodyPart(j);
						if (bodyPart.isMimeType("text/*")) {
							message = (String) bodyPart.getContent();
						}
					}
				} else if (mm.getContent() instanceof String) {
					message = mm.getContent().toString();
				}
				System.out.println(message);
				transactions.add(new RawTransactionRequest(mm.getSentDate(), message));
				messages[i].setFlag(Flags.Flag.SEEN, true);

			}
		}
		return transactions;
	}

	private Folder getInbox(Mail m) throws MessagingException {
		Session session = Session.getDefaultInstance(m.toProperties(), null);

		Store store;

		store = session.getStore(m.getProtocol());

		store.connect(m.getSmtpHost(), m.getAccount(), m.getPassword());
		
		return store.getFolder(m.getFolder());

	}

	@Override
	@Scheduled(fixedDelay=600000)
	public void poll() {
		System.out.println("Polling for mails");
		pollForMails();

	}

}
