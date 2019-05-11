package com.api.slcontract.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {
	private String username;
	private String password;

	public AuthenticationRequest(
					@JsonProperty String username,
					@JsonProperty String password) {
		this.username = username;
		this.password = password;
	}
}
