package io.kennethmartens.ckm.service;

import io.kennethmartens.ckm.data.ContactForm;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;

@Slf4j
@ApplicationScoped
public class EmailServiceImpl {

    @ConfigProperty(name = "ckm.mail.toAddress")
    String MAIL_TO_ADDRESS;


    @Inject
    ReactiveMailer reactiveMailer;

    public Uni<Response> send(ContactForm contactForm) {
        return reactiveMailer.send(
                Mail.withText(MAIL_TO_ADDRESS,
                        "Hidden Hues Contact - Subject:" + contactForm.getSubject(),
                        "First name: " + contactForm.getFirstname() + " Last Name: "+ contactForm.getLastname() + "\n" +
                                "Email-address: " + contactForm.getEmail() + "\n" +
                                "Phone number: " + contactForm.getPhoneNumber() + "\n" +
                                "Message: \n" + contactForm.getMessage()

                )
        )
        .onItem().transform(item -> Response.accepted().build())
        // ToDo: Catch more exceptions.
        .onFailure().transform(error -> {
            // Print stacktrace
            error.printStackTrace();
            log.error("Failure while mailing inquiry: {}.", error.getMessage());
            return new InternalServerErrorException("Something went terribly wrong whilst submitting your inquiry. Please try again later.");
        });
    }
}
