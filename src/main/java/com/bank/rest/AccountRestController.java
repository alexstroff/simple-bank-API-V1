package com.bank.rest;


import com.bank.model.Account;
import com.bank.repository.Utils;
import com.bank.service.AccountService;
import org.h2.jdbcx.JdbcDataSource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(AccountRestController.REST_URL)
public class AccountRestController {

    //todo Боль!!! Спросить о зависимостях
    private AccountService accountService = new AccountService(Utils.getDataSource());


    static final String REST_URL = "account";

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
//        System.out.println(accountService);
        return "Account got it!";
    }

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
