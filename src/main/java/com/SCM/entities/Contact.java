package com.SCM.entities;




import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
import com.SCM.entities.SocialLink;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class Contact {
   @Id
   private  String id;
   private String name;
   private String email;
   private String phoneNumber;
   private String  address;
   private String picture;
   @Column(length = 1000)
   private String  description;
   private boolean favourite = false;
   
   private String webSiteLink;
   private String LinkedInLink;
   

   @ManyToOne
   @JsonIgnore
   private User user;
   private String cloudinaryImagePublicId;
   @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
  private  List<SocialLink> socialLinks = new ArrayList();


}
