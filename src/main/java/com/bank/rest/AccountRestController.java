package com.bank.rest;


import com.bank.model.Client;
import com.bank.repository.utils.DBUtils;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.service.AccountService;
import com.bank.service.ClientService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("accounts")
public class AccountRestController {

    //todo Боль!!! Спросить о зависимостях
    private AccountService accountService = new AccountService(DBUtils.getDataSource());
    private ClientService clientService = new ClientService();


    static final String REST_URL = "account";

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/all")
    public String getAll() {
//        System.out.println(accountService);
        return JacksonUtils.writeValue(accountService.getAll(Client.builder().id(100000).build()));    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id) {
//        try {
//            Account acc = new Account(100002, null, "1111111111", 1000d, "RUB");
//            Double balance = accountService.checkBalance(acc);
//            System.out.println(balance);
//            return Response.ok(accountService.checkBalance(acc)).build();
//        } catch (Exception e) {
//            return Response.noContent().build();
//        }
        return null;
    }


}
