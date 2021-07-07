package com.auto.service.entity;

import com.auto.service.entity.enums.AvtoServiceStatusEnum;
import com.auto.service.entity.enums.Status;
import com.auto.service.entity.template.AbsEntity;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ServiceEntityProvider implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String serviceType;

    private String number;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Float lat;

    private Float lan;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    private String password;

    @Column(nullable = false)
    private String firstName;

    private String verificationCode;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "service_role", joinColumns = {@JoinColumn(name = "service_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;

    @OneToMany
    private List<FirebaseToken> firebaseToken;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user")
    private List<ServiceEntityProvider> serviceList;


    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;


    public ServiceEntityProvider(String phoneNumber, String password, String firstName, List<Role> roles) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.firstName = firstName;
        this.roles = roles;
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }







    @JsonIgnore
    @ManyToOne
    private User user;

    @OneToMany
    private List<FirebaseToken> tokenList;

    @OneToMany
    private List<FeedBack> comments;

    public AvtoServiceStatusEnum getStatusEnum()
    {
        return AvtoServiceStatusEnum.getFromStringValue(serviceType);
    }

}
