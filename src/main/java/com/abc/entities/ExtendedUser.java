package com.abc.entities;

import java.time.LocalDate;

public class ExtendedUser extends User {

	private String email;
	private LocalDate birthDate;
	private int provinceId;
	private String avatarFileName;

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

}
