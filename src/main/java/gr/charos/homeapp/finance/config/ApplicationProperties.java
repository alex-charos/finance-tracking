package gr.charos.homeapp.finance.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "finance", ignoreUnknownFields = false)
public class ApplicationProperties {
	private List<Mail> mails = new ArrayList<>();
	private Auth0 auth0 = new Auth0();

	public List<Mail> getMails() {
		return mails;
	}

	public Auth0 getAuth0() {
		return auth0;
	}

	public static class Auth0 {
		private String domain;
		private String clientId;
		private String clientSecret;
		private String audience;
		private String issuer;
		
		

		public String getAudience() {
			return audience;
		}

		public void setAudience(String audience) {
			this.audience = audience;
		}

		public String getIssuer() {
			return issuer;
		}

		public void setIssuer(String issuer) {
			this.issuer = issuer;
		}

		public String getClientId() {
			return clientId;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public String getClientSecret() {
			return clientSecret;
		}

		public void setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
		}

		public String getDomain() {
			return domain;
		}

		public void setDomain(String domain) {
			this.domain = domain;
		}

	}

	public static class Mail {
		private String smtpHost;
		private int smtpPort;
		private int socketFactoryPort;
		private String socketFactoryClass;
		private boolean smtpAuth;
		private String account;
		private String password;
		private String protocol;
		private String folder;
		private String familyCode;
		private String memberCode;

		public String getFamilyCode() {
			return familyCode;
		}

		public void setFamilyCode(String familyCode) {
			this.familyCode = familyCode;
		}

		public String getMemberCode() {
			return memberCode;
		}

		public void setMemberCode(String memberCode) {
			this.memberCode = memberCode;
		}

		public String getProtocol() {
			return protocol;
		}

		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}

		public String getFolder() {
			return folder;
		}

		public void setFolder(String folder) {
			this.folder = folder;
		}

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getSmtpHost() {
			return smtpHost;
		}

		public void setSmtpHost(String smtpHost) {
			this.smtpHost = smtpHost;
		}

		public int getSocketFactoryPort() {
			return socketFactoryPort;
		}

		public void setSocketFactoryPort(int socketFactoryPort) {
			this.socketFactoryPort = socketFactoryPort;
		}

		public String getSocketFactoryClass() {
			return socketFactoryClass;
		}

		public void setSocketFactoryClass(String socketFactoryClass) {
			this.socketFactoryClass = socketFactoryClass;
		}

		public int getSmtpPort() {
			return smtpPort;
		}

		public void setSmtpPort(int smtpPort) {
			this.smtpPort = smtpPort;
		}

		public boolean isSmtpAuth() {
			return smtpAuth;
		}

		public void setSmtpAuth(boolean smtpAuth) {
			this.smtpAuth = smtpAuth;
		}

		public Properties toProperties() {
			Properties props = new Properties();
			props.put("mail.smtp.host=", getSmtpHost());
			props.put("mail.smtp.socketFactory.port", getSocketFactoryPort());
			props.put("mail.smtp.socketFactory.class", getSocketFactoryClass());
			props.put("mail.smtp.auth", isSmtpAuth());
			props.put("mail.smtp.port", getSmtpPort());

			return props;
		}

	}

}
