package com.bank.rest;


import com.bank.model.Account;
import com.bank.model.Client;
import com.bank.model.to.AccountTo;
import com.bank.model.to.AccountToWithId;
import com.bank.model.utils.EntityUtils;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.service.AccountService;
import com.bank.service.AccountServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Slf4j
@Path("client/{clientId}/account")
public class AccountRestController {

    private AccountService service;

    public AccountRestController() {
        this.service = new AccountServiceImpl();
    }

    @GET
    @Path("/{id}")
    public String getById(@PathParam("id") int id, @PathParam("clientId") int clientId) {
        return JacksonUtils.writeValue(service.getById(clientId, id));
    }

    @GET
    @Path("/all")
    public String getAll(@PathParam("clientId") int clientId) {
        log.trace("got clientId = {}", clientId);
        List<Account> accounts = service.getAll(clientId);
        return JacksonUtils.writeValue(accounts);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAccount(@PathParam("clientId") int clientId, AccountTo accountTo) {
        log.trace("got clientId={}, accountTo={}", clientId, accountTo);
        Account accountForSave = EntityUtils.fromAccountTOToAccount(accountTo);
        accountForSave.setClient(Client.builder().id(clientId).build());
        Account newAccount = service.save(accountForSave);
        log.debug("Saved = {}", newAccount);
        return Response.status(201).entity(newAccount.getId()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("clientId") int clientId, AccountToWithId accountToWithId) {
        log.trace("got clientId={}, accountToWithId={}", clientId, accountToWithId);
        Account account = service.save(EntityUtils.fromAccountToWithIdToAccount(accountToWithId));
        account.setClient(Client.builder().id(clientId).build());
        Account updatedAccount = service.save(account);
        log.debug("return = {}", updatedAccount);
        return Response.status(202).entity(updatedAccount).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAccount(@PathParam("clientId") int clientId, @PathParam("id") int id) {
        return service.delete(clientId, id) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}
