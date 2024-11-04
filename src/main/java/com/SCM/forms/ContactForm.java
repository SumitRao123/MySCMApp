package com.SCM.forms;

import org.springframework.web.multipart.MultipartFile;

import com.SCM.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactForm {
    
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email Required")
    @Email(message = "Invalid Email Address [ example@gmail.com ]")
    private String email;
    private String address;
    private String description;
    
    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phoneNumber;
    private boolean favourite;
    private String picture;
    
    @ValidFile(message = "Invalid File")
    private MultipartFile contactImage;
    private String webSiteLink;
    private String LinkedInLink;

     
}
