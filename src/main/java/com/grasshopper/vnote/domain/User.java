package com.grasshopper.vnote.domain;

import com.grasshopper.vnote.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.beans.Transient;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Anisul on 8/14/2015.
 */

@Entity
@Table(name = "t_user")
public class User extends AbstractAuditable implements UserDetails {

    private String email;
    private String name;
    private Boolean active = false;
    private String roles = "";
    private Date dateLastActivity;

    private Set<GrantedAuthority> grantedAuthorities;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Date getDateLastActivity() {
        return dateLastActivity;
    }

    public void setDateLastActivity(Date dateLastActivity) {
        this.dateLastActivity = dateLastActivity;
    }

    @Override
    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        if (this.grantedAuthorities == null) {
            this.grantedAuthorities = new HashSet<GrantedAuthority>();
            if (this.roles != null) {
                String[] roles = this.roles.split(",");
                for (String role : roles) {
                    if (!role.trim().isEmpty()) {
                        this.grantedAuthorities.add(new SimpleGrantedAuthority(role.trim()));
                    }
                }
            }
        }
        return grantedAuthorities;
    }

    @Override
    @Transient
    public String getPassword() {
        return null;
    }

    @Override
    @Transient
    public String getUsername() {
        return null;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return false;
    }

    @Transient
    public boolean hasRole(Role role) {
        String roleText = role.toString();
        for (GrantedAuthority ga : this.getAuthorities()) {
            if (ga.getAuthority().equals(roleText)) {
                return true;
            }
        }
        return false;
    }

    public void updateWith(User u) {
        this.setName(u.getName());
        this.setRoles(u.getRoles());
        this.setEmail(u.getEmail());
        this.setActive(u.getActive());
        this.setStatus(u.getStatus());
        this.setDateCreated(u.getDateCreated());
        this.setDateLastUpdated(u.getDateLastUpdated());
        this.setCreatedBy(u.getCreatedBy());
        this.setLastUpdatedBy(u.getLastUpdatedBy());
        this.setDateLastActivity(u.getDateLastActivity());
    }
}
