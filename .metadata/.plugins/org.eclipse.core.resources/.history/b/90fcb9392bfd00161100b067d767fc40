package com.aprilsulu.bank.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import com.aprilsulu.bank.core.Account;

import io.dropwizard.hibernate.AbstractDAO;

public class AccountDAO extends AbstractDAO<Account> {
	public AccountDAO(final SessionFactory factory) {
		super(factory);
	}

	public Optional<Account> findById(final long id) {
		return Optional.ofNullable(get(id));
	}

	public Account create(final Account person) {
		return persist(person);
	}

	public Account updateBalance(final long id, final double balance)
	{
		final Account account = get(id);
		account.setBalance(balance);
		return persist(account);
	}

	public List<Account> findAll() {
		return list(namedQuery("com.aprilsulu.bank.core.Account.findAll"));
	}
}
