package io.kennethmartens.ckm.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactForm {
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String subject;
    private String message;
}
