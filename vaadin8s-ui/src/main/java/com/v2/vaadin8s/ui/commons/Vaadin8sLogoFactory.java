package com.v2.vaadin8s.ui.commons;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;


public class Vaadin8sLogoFactory {

	@SuppressWarnings("serial")
	private class LogoLayout extends VerticalLayout {

		private Embedded logo;

		public LogoLayout init() {
			logo = new Embedded();
			logo.setSource(new ThemeResource("../../images/logo.png"));
			logo.setWidth("398px");
			logo.setHeight("241px");
			return this;
		}

		public LogoLayout layout() {
			addComponent(logo);
			setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
			return this;
		}
	}

	public Component createComponent() {
		return new LogoLayout().init().layout();
	}
}