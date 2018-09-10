package com.v2.vaadin8s.ui.commons;

import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class Vaadin8sMenuFactory {

	public Tree<String> mainMenu;

	private class Vaadin8sMenu extends VerticalLayout {

		private TreeData<String> mainMenuData;
		private TreeDataProvider<String> dataProvider;
		
		public Vaadin8sMenu init() {
			mainMenu = new Tree<>();
			mainMenuData = new TreeData<>();
			
			mainMenu.addItemClickListener(event ->valueChange(event.getItem()));
			
			return this;
		}

		public Vaadin8sMenu layout() {

			setWidth("100%");
			setHeightUndefined();

			mainMenuData.addItem(null, "OPERATIONS");
			mainMenuData.addItem(null, "ADMINISTRATION");
			mainMenuData.addItem(null, "LOGOUT");

			mainMenuData.addItem("OPERATIONS", "Hardware");
			mainMenuData.addItem("OPERATIONS", "Incident");

			mainMenuData.addItem("ADMINISTRATION", "User");
			
			mainMenuData.addItem("LOGOUT", "Logout");
			
			dataProvider = new TreeDataProvider<>(mainMenuData);
			
			mainMenu.setDataProvider(dataProvider);
			mainMenu.expand("OPERATIONS");
			mainMenu.expand("ADMINISTRATION");
			mainMenu.expand("LOGOUT");
			
			addComponent(mainMenu);

			return this;
		}

		public void valueChange(String selectedItemPath) {

			if (selectedItemPath == null)
				return;

			String path = selectedItemPath.toLowerCase().replaceAll("\\s+", "");
			System.out.println("MENU");
			System.out.println(path);
			
			
			
			
		}
	}

	public Component createComponent() {
		return new Vaadin8sMenu().init().layout();
	}

}
