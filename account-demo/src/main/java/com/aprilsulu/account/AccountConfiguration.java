package com.aprilsulu.account;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public final class AccountConfiguration extends Configuration {

	@Valid
	@NotNull
	private DataSourceFactory database = new DataSourceFactory();


	@JsonProperty("database")
	public DataSourceFactory getDataSourceFactory() {
		return database;
	}

	@JsonProperty("database")
	public void setDataSourceFactory(final DataSourceFactory dataSourceFactory) {
		this.database = dataSourceFactory;
	}
}
