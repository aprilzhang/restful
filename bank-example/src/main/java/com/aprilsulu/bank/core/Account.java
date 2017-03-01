package com.aprilsulu.bank.core;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "account")
@NamedQueries(
		{
			@NamedQuery(
					name = "com.aprilsulu.bank.core.Account.findAll",
					query = "SELECT p FROM Account p"
					)
		}
		)
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "balance", nullable = false)
	private double balance;

	public Account() {
	}

	public Account(final double balance) {
		this.balance = balance;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(final double balance) {
		this.balance = balance;
	}

	@Override
	public boolean equals(final Object o) {
		if(o instanceof Account)
		{
			final Account that = (Account) o;
			return this.id==that.id
					&&Double.doubleToLongBits(this.balance)==Double.doubleToLongBits(that.balance);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, balance);
	}
}
