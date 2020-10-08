package com.bank.rest;


import com.bank.model.Account;
import com.bank.model.to.AccountTO;
import com.bank.model.utils.EntityUtils;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.service.AccountServiceImpl;
import com.bank.service.ClientServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("client/{clientId}/account")
public class AccountRestController {

    //todo Боль!!! Спросить о зависимостях
//    private AccountService accountService = new AccountService(DBUtils.getDataSource());
//    private ClientService clientService = new ClientService();

    private AccountServiceImpl accountService;
    private ClientServiceImpl clientService;

    public AccountRestController() {
        this.accountService = new AccountServiceImpl();
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
    @Path("/all")
    public String getAll(@PathParam("clientId") int id) {
        List<AccountTO> accountsTO = EntityUtils.fromAccountToAccountTO(accountService.getAll(id));
        return JacksonUtils.writeValue(accountsTO);
    }

    @GET
    @Path("/{id}")
    public String getById(@PathParam("id") int id, @PathParam("clientId") int clientId) {
        Account account = accountService.getById(clientId, id);
        AccountTO accountTO = EntityUtils.fromAccountToAccountTO(account);
        return JacksonUtils.writeValue(accountTO);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addAccount(@PathParam("clientId") int clientId, Account account) {
        accountService.add(clientId, account);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("clientId") int clientId, Account account) {
        accountService.update(clientId, account);
    }


    @DELETE
    @Path("/delete/{id}")
    public String deleteAccount(@PathParam("clientId") int clientId, @PathParam("id") int id) {
        if (accountService.delete(clientId, id)) {
            return "true";
        } else return "false";
    }
}
