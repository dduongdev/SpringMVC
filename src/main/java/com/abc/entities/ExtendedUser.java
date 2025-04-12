package com.abc.entities;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;

public class ExtendedUser extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "birth_date	")
	private LocalDate birthDate;
	@Column(name = "province_id")
	private int provinceId;
	@Column(name = "avatar_file_name")
	private String avatarFileName;

	public ExtendedUser() {
		
	}
	
	public ExtendedUser(int id, String username, String passWord, String createdAt, String email, LocalDate birthDate,
			int provinceId, String avatarFileName) {
		super(id, username, passWord, createdAt);
		this.email = email;
		this.birthDate = birthDate;
		this.provinceId = provinceId;
		this.avatarFileName = avatarFileName;
	}

	public ExtendedUser(String username, String passWord, String email, LocalDate birthDate, int provinceId,
			String avatarFileName) {
		super(username, passWord);
		this.email = email;
		this.birthDate = birthDate;
		this.provinceId = provinceId;
		this.avatarFileName = avatarFileName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public String getAvatarFileName() {
		return avatarFileName;
	}

	public void setAvatarFileName(String avatarFileName) {
		this.avatarFileName = avatarFileName;
	}

	@Override
	public String toString() {
		return "ExtendedUser [email=" + email + ", birthDate=" + birthDate + ", provinceId=" + provinceId
				+ ", avatarFileName=" + avatarFileName + "]";
	}

}
