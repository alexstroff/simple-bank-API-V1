package com.bank.rest;


import com.bank.model.Account;
import com.bank.model.Client;
import com.bank.model.EntityUtils;
import com.bank.repository.utils.DBUtils;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.service.AccountService;
import com.bank.service.ClientService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("client/account")
public class AccountRestController {

    //todo Боль!!! Спросить о зависимостях
//    private AccountService accountService = new AccountService(DBUtils.getDataSource());
//    private ClientService clientService = new ClientService();

    private AccountService accountService;
    private ClientService clientService;

    public AccountRestController() {
        this.accountService = new AccountService();
        this.clientService = new ClientService();
    }

    static final String REST_URL = "account";

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/all/{id}")
    public String getAll(@PathParam("id") int id) {
        return JacksonUtils.writeValue(accountService.getAll(id));
    }

    @GET
    @Path("/{id}")
    public String getById(@PathParam("id") int id) {
        return JacksonUtils.writeValue(accountService.getById(id));
    }

    @POST
    @Path("/add/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addAccount(@PathParam("id") int clientId, Account account) {
        System.out.println(account);
        accountService.add(clientId, account);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Account account){
        accountService.update(account);
    }


    @DELETE
    @Path("/delete/{id}")
    public String deleteAccount(@PathParam("id") int id) {
        if (accountService.delete(id)) {
            return "true";
        } else return "true";
    }
}
