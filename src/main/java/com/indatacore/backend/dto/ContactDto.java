package com.indatacore.backend.dto;

import com.indatacore.backend.common.CoreConstant;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
    private Long id;

    @NotBlank(message = CoreConstant.Messages.FIRST_NAME_BLANK)
    @Size(max = CoreConstant.Validation.DEFAULT_STRING_FIELD_SIZE, message = "First name must be at most " + CoreConstant.Validation.DEFAULT_STRING_FIELD_SIZE + " characters")
    private String firstName;

    @NotBlank(message = CoreConstant.Messages.LAST_NAME_BLANK)
    @Size(max = CoreConstant.Validation.DEFAULT_STRING_FIELD_SIZE, message = "Last name must be at most " + CoreConstant.Validation.DEFAULT_STRING_FIELD_SIZE + " characters")
    private String lastName;

    @NotBlank(message = CoreConstant.Messages.EMAIL_BLANK)
    @Email(message = CoreConstant.Messages.EMAIL_INVALID_FORMAT)
    @Size(max = CoreConstant.Validation.DEFAULT_EMAIL_FIELD_SIZE, message = "Email must be at most " + CoreConstant.Validation.DEFAULT_EMAIL_FIELD_SIZE + " characters")
    private String email;

    @NotBlank(message = CoreConstant.Messages.PHONE_NUMBER_BLANK)
    @Pattern(regexp = CoreConstant.Validation.PHONE_NUMBER_REGEX, message = CoreConstant.Messages.PHONE_NUMBER_INVALID_FORMAT)
    @Size(max = CoreConstant.Validation.MAX_PHONE_NUMBER_SIZE, message = "Phone number must be at most " + CoreConstant.Validation.MAX_PHONE_NUMBER_SIZE + " characters")
    private String phoneNumber;
}
