package com.v2.vaadin8s.ui.layouts;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import com.vaadin.data.provider.DataProvider;
import com.v2.vaadin8s.repository.UserRepository;
import com.v2.vaadin8s.model.entity.User;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;

@SuppressWarnings("serial")
public class UserFactoryLayout extends VerticalLayout implements View {

	@Inject
	private UserRepository userRepository;

	public UserFactoryLayout() {

		System.out.println("--Antes 2--");
		System.out.println(userRepository.getAllUsers());
		System.out.println("--Depois 2--");

		setSizeFull();

		Grid<User> secondComponent = createUserDataGrid();
		secondComponent.setWidth(100, Unit.PERCENTAGE);

		Component firstComponent = createUserForm(secondComponent);
		firstComponent.setWidth(100, Unit.PERCENTAGE);

		addComponent(firstComponent);
		addComponent(secondComponent);

	}

	private Grid<User> createUserDataGrid() {
		Grid<User> grid = new Grid<>();
		grid.setSizeFull();

		System.out.println("createDataGrid - antes");

		grid.setDataProvider(DataProvider.fromCallbacks(
				query -> userRepository.fetchUsers(query.getOffset(), query.getLimit()).stream(),
				query -> userRepository.countAllUsers()));

		System.out.println("createDataGrid - depois");

		grid.setSelectionMode(SelectionMode.NONE);

		grid.addColumn(User::getUserName).setCaption("Name");
		grid.addColumn(User::getUserEmail).setCaption("E-mail");
		grid.addColumn(User::getUserPassword).setCaption("Password");

		return grid;
	}

	private Component createUserForm(Grid<User> gridToUpdate) {
		FormLayout userForm = new FormLayout();
		userForm.setMargin(true);
		userForm.addStyleName("outlined");
		userForm.setSizeFull();

		final TextField name = new TextField("Name");
		name.setWidth(100.0f, Unit.PERCENTAGE);
		userForm.addComponent(name);

		final TextField email = new TextField("E-mail");
		email.setWidth(100.0f, Unit.PERCENTAGE);
		userForm.addComponent(email);

		final TextField passwd = new TextField("Password");
		passwd.setWidth(100.0f, Unit.PERCENTAGE);
		userForm.addComponent(passwd);

		userForm.addComponent(new Button("Add", __ -> {
			LoggerFactory.getLogger(getClass()).info("Adding new user!");
			User user = new User();
			user.setUserId(new ObjectId());
			user.setUserEmail(email.getValue());
			user.setUserPassword(passwd.getValue());
			user.setUserName(name.getValue());
			System.out.println(user);
			if (userRepository.addUser(user)) {
				Notification.show("User added!");
				gridToUpdate.getDataProvider().refreshAll();
			} else {
				Notification.show("Count not add user!", Notification.Type.WARNING_MESSAGE);
			}
		}));

		return userForm;
	}
}