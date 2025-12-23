package com.andre.myapi.user;

import com.andre.myapi.user.dto.PasswordUpdateDTO;
import com.andre.myapi.user.dto.UserRequestDTO;
import com.andre.myapi.user.dto.UserResponseDTO;
import com.andre.myapi.user.dto.UserUpdateDTO;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService service;

    @POST
    public Response create(@Valid UserRequestDTO dto) {
        UserResponseDTO created = service.create(dto);
        return Response.status(Response.Status.CREATED)
                .entity(created)
                .build();
    }

    @GET
    public List<UserResponseDTO> list() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        try {
            UserResponseDTO user = service.getById(id);
            return Response.ok(user).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, UserUpdateDTO dto) {
        try {
            UserResponseDTO updated = service.update(id, dto);
            return Response.ok(updated).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}/password")
    public Response updatePassword(@PathParam("id") Long id, @Valid PasswordUpdateDTO dto) {
        service.updatePassword(id, dto);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            service.delete(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
