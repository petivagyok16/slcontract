package com.api.slcontract.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "users")
@Data
public class User implements UserDetails {

	@MongoId
	private String id;

	@NotBlank(message = "Email cannot be empty!")
	@Email(message = "Email is not valid!")
	private String username;

	@NotBlank(message = "Password must be provided!")
	@Size(min = 4, message = "Password must be at least 4 characters long!")
	private String password;

	private List<Role> roles;

	public User(
					@JsonProperty("username") String username,
					@JsonProperty("password") String password) {
		this.username = username;
		this.password = password;
		this.roles = Collections.singletonList(Role.ROLE_CLIENT);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>(this.roles);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}
}
