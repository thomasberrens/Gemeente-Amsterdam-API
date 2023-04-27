package org.gemeenteamsterdam.api.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Role {
	@Transient
	public static final Role USER = new Role(null, "ROLE_USER");

	@Transient
	public static final Role MANAGER = new Role(null, "ROLE_MANAGER");

	@Transient
	public static final Role ADMIN = new Role(null, "ROLE_ADMIN");

	protected Role() {
		this.id = null;
		this.name = null;
	}

	protected Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Column(unique = true)
	private String name;
}
