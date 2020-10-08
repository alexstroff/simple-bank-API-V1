package com.bank.rest;

import com.bank.model.Account;
import com.bank.model.CreditCard;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.service.CreditCardServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;

@Path("client/{clientId}/account/{accountId}/card")
public class CreditCardRestController {

    private CreditCardServiceImpl cardService;

    public CreditCardRestController() {
        this.cardService = new CreditCardServiceImpl();
    }

    @GET
    @Path("/all")
    public String getAll(@PathParam("clientId") int clientId,
                         @PathParam("accountId") int accountId) {
        List<CreditCard> cards = cardService.getAll(clientId, accountId);
        return JacksonUtils.writeValue(cards);
    }

    @GET
    @Path("/{id}")
    public String getById(@PathParam("clientId") int clientId,
                          @PathParam("accountId") int accountId,
                          @PathParam("id") int id) {
        return JacksonUtils.writeValue(cardService.getByClientId(clientId, accountId, id));
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public String addCard(@PathParam("clientId") int clientId,
                              @PathParam("accountId") int accountId,
                              CreditCard card) {
        CreditCard creditCard = cardService.addByClientId(clientId, accountId, card);
        return JacksonUtils.writeValue(creditCard.getId());
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public String update(@PathParam("clientId") int clientId,
                         @PathParam("accountId") int accountId, CreditCard card) {
        CreditCard card1 = cardService.update(clientId, accountId, card);
        return JacksonUtils.writeValue(card1.getId());
    }


    @DELETE
    @Path("/delete/{id}")
    public String deleteAccount(@PathParam("clientId") int clientId,
                                @PathParam("accountId") int accountId,
                                @PathParam("id") int id) {
        if (cardService.deleteByClientId(clientId, accountId, id)) {
            return "true";
        } else return "true";
    }

    @PUT
    @Path("/{id}/incbalance/{value}")
    public boolean increaseBallance(@PathParam("clientId") int clientId,
                                    @PathParam("accountId") int accountId,
                                    @PathParam("id") int id,
                                    @PathParam("value") BigDecimal value) {
        return cardService.increaseBallance(clientId, accountId, id, value);
    }

    @PUT
    @Path("/{id}/decballanse/{value}")
    public boolean reduceBallance(@PathParam("clientId") int clientId,
                                  @PathParam("accountId") int accountId,
                                  @PathParam("id") int id,
                                  @PathParam("value") BigDecimal value) {
        return cardService.reduceBallance(clientId, accountId, id, value);
    }


}
