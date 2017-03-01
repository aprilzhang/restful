package com.aprilsulu.bank.resources;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.HibernateException;

import com.aprilsulu.bank.core.Account;
import com.aprilsulu.bank.core.TransferInfo;
import com.aprilsulu.bank.db.AccountDAO;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public final class AccountResource {

	private final AccountDAO accountDAO;

	public AccountResource(final AccountDAO accountDAO) {
		this.accountDAO = checkNotNull(accountDAO);
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

	@POST
	@Path("/transfer")
	@UnitOfWork
	public Response transfer(final TransferInfo transferInfo) {

		if(transferInfo==null)
		{
			return Response.status(Status.BAD_REQUEST).entity("transfer info cannot be null").build();
		}

		try
		{
			accountDAO.transfer(transferInfo);
			return Response.ok().build();
		}
		catch(IllegalStateException | NullPointerException e)
		{
			return Response.status(Status.BAD_REQUEST).entity("failed: "+ e.getMessage()).build();
		}
		catch( final HibernateException he)
		{
			return Response.serverError().build();
		}
	}

	@OPTIONS
	@Produces(MediaType.APPLICATION_XML)
	public String getSupportedOperations(){
		return "<operations>GET, POST</operations>";
	}
}
