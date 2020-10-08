package com.bank.rest;

import com.bank.model.Account;
import com.bank.model.CreditCard;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.service.CreditCardService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("client/account/card")
public class CreditCardRestController {

    private CreditCardService cardService;

    public CreditCardRestController() {
        this.cardService = new CreditCardService();
    }

    @GET
    @Path("/all/{id}")
    public String getAll(@PathParam("id") int id){
        return JacksonUtils.writeValue(cardService.getAll(id));
    }

    @GET
    @Path("/{id}")
    public String getById(@PathParam("id") int id) {
        return JacksonUtils.writeValue(cardService.getById(id));
    }

    @POST
    @Path("/add/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addAccount(@PathParam("id") int clientId, CreditCard card) {
        System.out.println(card);
        cardService.add(clientId, card);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(Account account){
        cardService.update(account);
    }


    @DELETE
    @Path("/delete/{id}")
    public String deleteAccount(@PathParam("id") int id) {
        if (cardService.delete(id)) {
            return "true";
        } else return "true";
    }

}
