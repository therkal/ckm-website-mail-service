package io.kennethmartens.ckm.v1.resources;

import io.kennethmartens.ckm.data.ContactForm;
import io.kennethmartens.ckm.service.EmailServiceImpl;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Path(EmailResource.API_EMAIL)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmailResource {

    private final EmailServiceImpl emailService;
    public static final String API_EMAIL = "/email";

    public EmailResource(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    /**
     * Todo: Replace endpoint by Kafka stream
     * Takes a contact form and sends it as email
     * @param contactForm the contact form
     * @return An Accepted Response
     */
    @POST
    public Uni<Response> sendEmail(ContactForm contactForm) {
        log.info("POST request to {} with {}", API_EMAIL, contactForm);
        return this.emailService.send(contactForm).map(
                x -> Response.status(Response.Status.ACCEPTED).build()
        );
    }
}
