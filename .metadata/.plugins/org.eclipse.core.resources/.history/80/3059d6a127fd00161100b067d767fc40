package com.aprilsulu.bank.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.aprilsulu.bank.filter.DateRequired;

@Path("/filtered")
public class FilteredResource {

    @GET
    @DateRequired
    @Path("hello")
    public String sayHello() {
        return "hello";
    }
}
