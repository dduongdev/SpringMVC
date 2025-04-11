package com.abc.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

public class UserEditRequest {
	private String email;
	private LocalDate birthDate;
	private int provinceId;
	private MultipartFile avatarFile;

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

	public MultipartFile getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(MultipartFile avatarFile) {
		this.avatarFile = avatarFile;
	}

	@Override
	public String toString() {
		return "UserEditRequest [email=" + email + ", birthDate=" + birthDate + ", provinceId=" + provinceId
				+ ", avatarFile=" + avatarFile + "]";
	}

	

}
