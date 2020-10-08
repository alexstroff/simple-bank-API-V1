package com.bank.rest;


import com.bank.model.Account;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.service.AccountServiceImpl;
import com.bank.service.ClientServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("client/account")
public class AccountRestController {

    //todo Боль!!! Спросить о зависимостях
//    private AccountService accountService = new AccountService(DBUtils.getDataSource());
//    private ClientService clientService = new ClientService();

    private AccountServiceImpl accountServiceImpl;
    private ClientServiceImpl clientService;

    public AccountRestController() {
        this.accountServiceImpl = new AccountServiceImpl();
        this.clientService = new ClientServiceImpl();
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
        return JacksonUtils.writeValue(accountServiceImpl.getAll(id));
    }

    @GET
    @Path("/{id}")
    public String getById(@PathParam("id") int id) {
        return JacksonUtils.writeValue(accountServiceImpl.getById(id));
    }

    @POST
    @Path("/add/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addAccount(@PathParam("id") int clientId, Account account) {
        System.out.println(account);
        accountServiceImpl.add(clientId, account);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Account account){
        accountServiceImpl.update(account);
    }


    @DELETE
    @Path("/delete/{id}")
    public String deleteAccount(@PathParam("id") int id) {
        if (accountServiceImpl.delete(id)) {
            return "true";
        } else return "true";
    }
}
