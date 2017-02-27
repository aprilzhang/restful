package com.aprilsulu.bank.resources;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.aprilsulu.bank.core.Account;
import com.aprilsulu.bank.db.AccountDAO;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public final class AccountResource {

	private final AccountDAO accountDAO;

	public AccountResource(final AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	@GET
	@UnitOfWork
	public List<Account> listAccount() {
		return accountDAO.findAll();
	}

	@GET
	@Path("/{accountid}")
	@UnitOfWork
	public Optional<Account> getAccount(@PathParam("accountid") final int accountid) {
		return accountDAO.findById(accountid);
	}

	@OPTIONS
	@Produces(MediaType.APPLICATION_XML)
	public String getSupportedOperations(){
		return "<operations>GET</operations>";
	}
}
