package com.bank.rest;


import com.bank.model.Client;
import com.bank.model.EntityUtils;
import com.bank.model.to.ClientTo;
import com.bank.model.to.ClientToWithId;
import com.bank.rest.JacksonUtils.JacksonUtils;
import com.bank.service.ClientService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("client")
public class ClientRestController {

    ClientService service = new ClientService();

    @GET
    @Path("/{id}")
    public String get(@PathParam("id") int id) {
        return JacksonUtils.writeValue(service.getById(id));
    }

    @GET
    @Path("/all")
    public String getAll() {
        return JacksonUtils.writeValue(service.getAll());
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(ClientTo clientTo) {
//        System.out.println(clientTo);
        Client newClient = service.addNewClient(EntityUtils.fromClientToToClient(clientTo));
        return Response.status(201).entity(newClient.getId()).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(ClientToWithId clientToWithId) {
        Client client = service.updateClient(EntityUtils.fromClientToWithIdToClient(clientToWithId));
        return Response.status(202).entity(client).build();
    }

    @GET
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") int id) {
        service.deleteClient(id);
        return Response.status(202).entity(String.valueOf(id)).build();
    }

//    @POST
//    @Path("/add")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response add(Client client) {
//        service.addClient(client);
//
//        return Response.status(201).entity(client).build();
//    }

    //    @GET
//    @Path("/get")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Track getTrackInJSON() {
//
//        Track track = new Track();
//        track.setTitle("Enter Sandman");
//        track.setSinger("Metallica");
//
//        return track;
//
//    }
//    @GET
//    @Path("/get")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Client getTrackInJSON() {
//        Client client = new Client();
//        client.setName("su");
//        client.setEmail("12@mail.ru");
//        return client;
//
//    }
//
//    @POST
//    @Path("/post")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createTrackInJSON(Client client) {
//        String result = "Track saved : " + client;
//        return Response.status(201).entity(result).build();
//
//    }
}
