package com.aprilsulu.bank.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import com.aprilsulu.bank.core.Transaction;

import io.dropwizard.hibernate.AbstractDAO;

public class TransactionDAO  extends AbstractDAO<Transaction> {
	public TransactionDAO(final SessionFactory factory) {
		super(factory);
	}

	public Optional<Transaction> findById(final long id) {
		return Optional.ofNullable(get(id));
	}

	public Transaction create(final Transaction transaction) {
		return persist(transaction);
	}

	public List<Transaction> findAll() {
		return list(namedQuery("com.aprilsulu.bank.core.Transaction.findAll"));
	}
}