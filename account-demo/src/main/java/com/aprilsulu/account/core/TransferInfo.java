package com.aprilsulu.account.core;

import java.util.Objects;
/**
 * Store transfer information
 * @author yzhang
 */
public final class TransferInfo {

	private long fromAccountId;
	private long toAccountId;
	private double amount;

	public TransferInfo() {
	}

	public TransferInfo(final long fromAccountId, final long toAccountId, final double amount) {
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.amount = amount;
	}

	public long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(final long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public long getToAccountId() {
		return toAccountId;
	}

	public void setAcountId(final long toAccountId) {
		this.toAccountId = toAccountId;
	}


	public double getAmount() {
		return amount;
	}

	public void setAmount(final double amount) {
		this.amount = amount;
	}

	@Override
	public boolean equals(final Object o) {
		if(o instanceof TransferInfo)
		{
			final TransferInfo that = (TransferInfo) o;
			return this.fromAccountId==that.fromAccountId
					&&this.toAccountId==that.toAccountId
					&&Double.doubleToLongBits(this.amount)==Double.doubleToLongBits(that.amount);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash( fromAccountId,toAccountId,amount);
	}
}
