package com.aprilsulu.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import com.aprilsulu.account.AccountApplication;
import com.aprilsulu.account.AccountConfiguration;
import com.aprilsulu.account.core.Account;
import com.aprilsulu.account.core.TransferInfo;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

public final class IntegrationTest {

	private static final String TMP_FILE = createTempFile();
	private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-example.yml");

	@ClassRule
	public static final DropwizardAppRule<AccountConfiguration> RULE = new DropwizardAppRule<>(
			AccountApplication.class, CONFIG_PATH,
			ConfigOverride.config("database.url", "jdbc:h2:" + TMP_FILE));

	@BeforeClass
	public static void migrateDb() throws Exception {
		RULE.getApplication().run("db", "migrate", CONFIG_PATH);
	}

	@Test
	public void testGetAccounts() throws Exception {
		final List<Account> accounts = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/accounts")
				.request()
				.get(new GenericType<List<Account>>() {});
		assertThat(accounts.get(2).getBalance()).isEqualTo(1809.19);
	}

	@Test
	public void testGetIndividualAccount() throws Exception {
		final Account account = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/accounts/1")
				.request()
				.get()
				.readEntity(Account.class);
		assertThat(account.getId()).isNotNull();
		assertThat(account.getBalance()).isEqualTo(500.0);
	}

	@Test
	public void testGetIndividualAccountNotFound() throws Exception {
		final Response response  = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/accounts/12")
				.request()
				.get();
		assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Status.NOT_FOUND.getStatusCode());
	}

	@Test
	public void testTransfer() throws Exception {
		final TransferInfo info = new TransferInfo(1,3,200);
		final Response response = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/accounts/transfer")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(info, MediaType.APPLICATION_JSON_TYPE));
		assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Status.OK.getStatusCode());

		final Account account1 = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/accounts/1")
				.request()
				.get()
				.readEntity(Account.class);
		assertThat(account1.getBalance()).isEqualTo(300.0);

		final Account account3 = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/accounts/3")
				.request()
				.get()
				.readEntity(Account.class);
		assertThat(account3.getBalance()).isEqualTo(2009.19);
	}

	@Test
	public void testTransferNullInfo() throws Exception {
		final Response response = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/accounts/transfer")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(null, MediaType.APPLICATION_JSON_TYPE));
		assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
	}


	@Test
	public void testTransferIdNotFound() throws Exception {
		final TransferInfo info = new TransferInfo(17,3,200);
		final Response response = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/accounts/transfer")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(info, MediaType.APPLICATION_JSON_TYPE));
		assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
	}


	@Test
	public void testTransferNotEnoughBalance() throws Exception {
		final TransferInfo info = new TransferInfo(1,3,2000);
		final Response response = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/accounts/transfer")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(info, MediaType.APPLICATION_JSON_TYPE));
		assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
	}

	private static String createTempFile() {
		try {
			return File.createTempFile("test", null).getAbsolutePath();
		} catch (final IOException e) {
			throw new IllegalStateException(e);
		}
	}
}