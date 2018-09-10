package com.v2.vaadin8s.ui.commons;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class Vaadin8sNavigator extends Navigator {

	public Vaadin8sNavigator(UI ui, SingleComponentContainer container) {
			super(ui, container);
		}

	private static Vaadin8sNavigator getNavigator() {
		UI ui = UI.getCurrent();
		Navigator navigator = ui.getNavigator();
		return (Vaadin8sNavigator) navigator;
	}

	public static void navigate(String path) {
		try {
			Vaadin8sNavigator.getNavigator().navigateTo(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void navigateTo(String viewName) {
		super.navigateTo(viewName);
	}
}