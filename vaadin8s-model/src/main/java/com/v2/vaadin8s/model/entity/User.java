package com.v2.vaadin8s.model.entity;

import org.bson.types.ObjectId;
import org.jnosql.artemis.Column;
import org.jnosql.artemis.Entity;
import org.jnosql.artemis.Id;

import com.v2.vaadin8s.utils.StringUtils;

@Entity
public class User {

	@Id
	private ObjectId userId;

	@Column
	private String userName;

	@Column
	private String userEmail;

	@Column
	private String userPassword;

	public User() {
	}

	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	//@formatter:off
	@Override
	public String toString() {
		return String.format("%s {\n %s: %s\n, %s: %s\n, %s: %s - %s\n }", 
				StringUtils.USER_TITLE.getString(),
				StringUtils.USER_ID.getString(), 
				this.userId, 
				StringUtils.USER_NAME.getString(), 
				this.userName,
				StringUtils.USER_EMAIL.getString(), 
				this.userEmail,
				this.userId.toString()
				);
	}
	//@formatter:on

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userPassword == null) ? 0 : userPassword.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userPassword == null) {
			if (other.userPassword != null)
				return false;
		} else if (!userPassword.equals(other.userPassword))
			return false;
		return true;
	}

}
