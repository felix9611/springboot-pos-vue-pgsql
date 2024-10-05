package com.fixedasset.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class PassDto implements Serializable {

	@NotBlank(message = "New Password cannot blank")
	private String password;

	@NotBlank(message = "Old Password cannot blank")
	private String currentPass;
}
