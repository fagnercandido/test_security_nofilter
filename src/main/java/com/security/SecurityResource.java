package com.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.http.HttpRequest;

@Path("/security")
@ApplicationScoped
@Component
@Produces("application/json")
@Consumes("application/json")
public class SecurityResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @PreAuthorize("@securityFilter.check(#headers, 'LINK')")
    public String hello(@Context HttpHeaders headers) {
        return "Hello RESTEasy";
    }

    @POST()
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    @Transactional
    public Response add(APIKey apiKey) {
        apiKey.persist();
        return Response.ok(apiKey).status(201).build();
    }
}