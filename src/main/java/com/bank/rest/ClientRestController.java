package com.bank.rest;


import com.bank.model.Client;
import com.bank.model.utils.EntityUtils;
import com.bank.model.to.ClientTo;
import com.bank.model.to.ClientToWithId;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.service.ClientService;
import com.bank.service.ClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.invoke.MethodHandles;

@Slf4j
@Path("client")
public class ClientRestController {

    private final ClientService service; //todo to interface

    public ClientRestController() {
        service = new ClientServiceImpl();
    }

    @GET
    @Path("/{id}")
    public String get(@PathParam("id") int id) {
        log.trace("got id = {}", id);
        return JacksonUtils.writeValue(service.getById(id));
    }

    @GET
    @Path("/all")
    public String getAll() {
        log.trace("Get All");
        return JacksonUtils.writeValue(service.getAll());
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(ClientTo clientTo) {
        Client newClient = service.save(EntityUtils.fromClientToToClient(clientTo));
        log.debug("return = {}", newClient);
        return Response.status(201).entity(newClient.getId()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(ClientToWithId clientToWithId) {
        log.trace("got id = {}", clientToWithId);
        Client client = service.save(EntityUtils.fromClientToWithIdToClient(clientToWithId));
        log.debug("return = {}", clientToWithId);
        return Response.status(202).entity(client).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        log.trace("got id = {}", id);
        service.delete(id);
        return Response.status(202).entity(String.valueOf(id)).build();
    }
}
