package com.howzit.java.model;

import com.howzit.java.model.master.Role;
import com.howzit.java.model.type.PasswordType;
import com.howzit.java.util.interceptor.IndexUserInterceptor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.security.Principal;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 21/8/14
 * Time: 11:03 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Indexed(interceptor  = IndexUserInterceptor.class)
@javax.persistence.Table(name = "user")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserEntity extends BaseEntity implements UserDetails, Principal {

    private static final long serialVersionUID = 3256446889040622647L;

    private Long id;
    private String username;
    private String password;
    private String salt;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private Address address;
    private byte[] profilePick;
    private Set<Role> roles;
    private PasswordType passwordType = PasswordType.PERMANENT;
    private boolean accountNonExpired=true;
    private boolean accountNonLocked=true;
    private boolean credentialsNonExpired=true;
    private boolean enabled=true;


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(unique = true, nullable = false, updatable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Field(name = "first_name" ,index = Index.YES, store = Store.NO, analyze = Analyze.YES)
    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Field(name = "last_name", index = Index.YES, store = Store.NO, analyze = Analyze.YES)
    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Embedded
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(name = "profile_pick", insertable = true, updatable = true,nullable = true)
    public byte[] getProfilePick() {
        return profilePick;
    }

    public void setProfilePick(byte[] profilePick) {
        this.profilePick = profilePick;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false)})
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        if(null==getRoles())
            setRoles(new HashSet<Role>());
        getRoles().add(role);
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "password_type")
    public PasswordType getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(PasswordType passwordType) {
        this.passwordType = passwordType;
    }

    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            AUTHORITIES.add(new SimpleGrantedAuthority(role.getName()));
        }
        return AUTHORITIES;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @javax.persistence.Column(name = "username", nullable = false, insertable = true, updatable = false, length = 255, precision = 0)
    @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Transient
    @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
    public String getName(){
        return getFirstName()+" " +getLastName();
    }

    @Transient
    public boolean isUserInRole(String roleName){
        boolean isValid = false;
        if(null!=getRoles()){
            for(Role role :getRoles()){
                if(roleName.equals(role.getName())){
                    isValid=true;
                    break;
                }
            }
        }
        return isValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity userEntity = (UserEntity) o;

        if (id != null ? !id.equals(userEntity.id) : userEntity.id != null) return false;
        if (username != null ? !username.equals(userEntity.username) : userEntity.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
