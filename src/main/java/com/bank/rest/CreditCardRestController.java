package com.bank.rest;

import com.bank.model.Account;
import com.bank.model.Client;
import com.bank.model.CreditCard;
import com.bank.model.to.CreditCardTo;
import com.bank.model.to.CreditCardToWithId;
import com.bank.model.utils.EntityUtils;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.service.CreditCardServiceImpl;
import com.bank.service.CreditCardService;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Path("client/{clientId}/account/{accountId}/card")
public class CreditCardRestController {

    private CreditCardService service;

    public CreditCardRestController() {
        this.service = new CreditCardServiceImpl();
    }

    @GET
    @Path("/{id}")
    public String getById(@PathParam("clientId") int clientId,
                          @PathParam("accountId") int accountId,
                          @PathParam("id") int cardId) {
        log.trace("clientId={}, accountId={}, cardId={}", clientId, accountId, cardId);
        return JacksonUtils.writeValue(service.getById(clientId, accountId, cardId));
    }

    @GET
    @Path("/all")
    public String getAll(@PathParam("clientId") int clientId,
                         @PathParam("accountId") int accountId) {
        log.trace("clientId={}, accountId={}", clientId, accountId);

        List<CreditCard> cards = service.getAll(clientId, accountId);
        return JacksonUtils.writeValue(cards);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAccount(@PathParam("clientId") int clientId,
                               @PathParam("accountId") int accountId,
                               CreditCardTo cardTo) {
        log.trace("clientId={}, accountId={}, card={}", clientId, accountId, cardTo);
        CreditCard cardForSave = EntityUtils.fromCreditCardTOToCreditCard(cardTo);
        Client client = Client.builder().id(clientId).build();
        cardForSave.setAccount(Account.builder().id(accountId).client(client).build());
        CreditCard savedCard = service.save(cardForSave);
        log.debug("Saved = {}", savedCard);
        return Response.status(201).entity(savedCard.getId()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("clientId") int clientId,
                         @PathParam("accountId") int accountId,
                         CreditCardToWithId cardToWithId) {
        log.trace("clientId={}, accountId={}, card={}", clientId, accountId, cardToWithId);

        CreditCard cardForUpdate= EntityUtils.fromCreditCardToWithIdToCreditCard(cardToWithId);
        Client client = Client.builder().id(clientId).build();
        cardForUpdate.setAccount(Account.builder().id(accountId).client(client).build());
        CreditCard savedCard = service.save(cardForUpdate);
        log.debug("Updated = {}", savedCard);
        return Response.status(202).entity(cardForUpdate).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAccount(@PathParam("clientId") int clientId,
                                @PathParam("accountId") int accountId,
                                @PathParam("id") int cardId) {
        return service.delete(clientId, accountId, cardId) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/{id}/incbalance/{value}")
    public boolean increaseBallance(@PathParam("clientId") int clientId,
                                    @PathParam("accountId") int accountId,
                                    @PathParam("id") int id,
                                    @PathParam("value") BigDecimal value) {
        return service.increaseBallance(clientId, accountId, id, value);
    }

    @PUT
    @Path("/{id}/decballanse/{value}")
    public boolean reduceBallance(@PathParam("clientId") int clientId,
                                  @PathParam("accountId") int accountId,
                                  @PathParam("id") int id,
                                  @PathParam("value") BigDecimal value) {
        return service.reduceBallance(clientId, accountId, id, value);
    }

}
