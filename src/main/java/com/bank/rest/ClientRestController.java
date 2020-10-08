package com.bank.rest;


import com.bank.model.Client;
import com.bank.model.utils.EntityUtils;
import com.bank.model.to.ClientTo;
import com.bank.model.to.ClientToWithId;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.service.ClientService;
import com.bank.service.ClientServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.invoke.MethodHandles;

@Path("client")
public class ClientRestController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ClientService service; //todo to interface

    public ClientRestController() {
        service = new ClientServiceImpl();
    }

    @GET
    @Path("/{id}")
    public String get(@PathParam("id") int id) {
        logger.trace("got id = {}", id);
        return JacksonUtils.writeValue(service.getById(id));
    }

    @GET
    @Path("/all")
    public String getAll() {
        logger.trace("Get All");
        return JacksonUtils.writeValue(service.getAll());
    }

//    @POST
//    @Path("/add")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response add(ClientTo clientTo) {
//        Client newClient = service.add(EntityUtils.fromClientToToClient(clientTo));
//        logger.debug("return = {}", newClient);
//        return Response.status(201).entity(newClient.getId()).build();
//    }

//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response update(ClientToWithId clientToWithId) {
//        logger.trace("got id = {}", clientToWithId);
//        Client client = service.update(EntityUtils.fromClientToWithIdToClient(clientToWithId));
//        logger.debug("return = {}", clientToWithId);
//        return Response.status(202).entity(client).build();
//    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        logger.trace("got id = {}", id);
        service.delete(id);
        return Response.status(202).entity(String.valueOf(id)).build();
    }
}
