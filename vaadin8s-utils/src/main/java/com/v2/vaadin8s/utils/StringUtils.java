package com.v2.vaadin8s.utils;

public enum StringUtils {

	HARDWARE_TITLE("Hardware"), //
	HARDWARE_ID("ID"), //
	HARDWARE_NAME("Name"), //
	HARDWARE_LOCATION("Location"), //

	USER_TITLE("User"), //
	USER_ID("ID"), //
	USER_NAME("Name"), //
	USER_EMAIL("E-mail"), //

	INCIDENT_TITLE("Incident"), //
	INCIDENT_ID("ID"), //
	INCIDENT_DATE("Date"), //
	INCIDENT_DESCRIPTION("Description"), //

	
	MENU_OPERATIONS("OPERATIONS"),//
	MENU_ADMIN("ADMINISTRATION"),//
	MENU_LOGOUT("LOGOUT"),//
	MENU_USER("User"),//
	MENU_HARDWARE("Hardware"),//
	MENU_INCIDENT("Incident"),//
	MENU_LOGOUT_LABEL("Logout"), //
	;

	private final String string;

	private StringUtils(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}
}
