package com.auto.service.entity;

import com.auto.service.entity.enums.Status;
import com.auto.service.entity.template.AbsEntity;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Users")
public class User extends AbsEntity implements UserDetails {

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    private String password;

    @Column(nullable = false)
    private String firstName;

    private String verificationCode;

    @Embedded
    private Company company;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")},
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


    public User(String phoneNumber, String password, String firstName, List<Role> roles) {
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
}
