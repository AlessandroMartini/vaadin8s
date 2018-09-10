package com.v2.vaadin8s.ui.commons;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

import com.v2.vaadin8s.model.entity.Hardware;
import com.v2.vaadin8s.model.entity.Incident;
import com.v2.vaadin8s.model.entity.User;
import com.v2.vaadin8s.repository.HardwareRepository;
import com.v2.vaadin8s.repository.IncidentRepository;
import com.v2.vaadin8s.repository.UserRepository;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.cdi.CDIUI;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;

@SuppressWarnings("serial")
@Title("Vaadin 8 Single service")
@PreserveOnRefresh
@CDIUI("ui")
@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
@Theme("valo")
public class Vaadin8sUI extends UI {

	private Panel changeTab = new Panel();

	private Vaadin8sMenuFactory menuFactory = new Vaadin8sMenuFactory();

	private Vaadin8sLogoFactory logoFactory = new Vaadin8sLogoFactory();

	@Inject
	private UserRepository userRepository;

	@Inject
	private HardwareRepository hardwareRepository;
	
	@Inject
	private IncidentRepository incidentRepository;

	@Override
	protected void init(VaadinRequest request) {

		changeTab.setHeight("100%");

		VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		root.setMargin(true);

		Panel content = new Panel();
		content.setWidth("85%");
		content.setHeight("100%");

		Panel logoContent = new Panel();
		logoContent.setWidth("85%");
		logoContent.setHeightUndefined();

		HorizontalLayout uiLayout = new HorizontalLayout();
		uiLayout.setSizeFull();
		uiLayout.setMargin(true);

		Component logo = logoFactory.createComponent();
		Component menu = menuFactory.createComponent();

		menuFactory.mainMenu.addItemClickListener(event -> valueChange(event.getItem()));

		uiLayout.addComponent(menu);
		uiLayout.addComponent(changeTab);

		uiLayout.setComponentAlignment(changeTab, Alignment.TOP_CENTER);
		uiLayout.setComponentAlignment(menu, Alignment.TOP_CENTER);

		uiLayout.setExpandRatio(menu, 1);
		uiLayout.setExpandRatio(changeTab, 3);

		logoContent.setContent(logo);
		content.setContent(uiLayout);

		root.addComponent(logoContent);
		root.addComponent(content);
		root.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
		root.setComponentAlignment(logoContent, Alignment.TOP_CENTER);
		root.setExpandRatio(content, 1);

		System.out.println("--Antes--");
		System.out.println(userRepository.getAllUsers());
		System.out.println("--Depois--");

		initializeNavigator();

		//changeTab.setContent(UserFactoryLayout());

		setContent(root);
	}

	private void initializeNavigator() {
//		Vaadin8sNavigator navigator = new Vaadin8sNavigator(this, changeTab);
//		navigator.addView("user", UserFactoryLayout.class);
//		navigator.addView("hardware", HardwareFactoryLayout.class);
//		navigator.addView("incident", IncidentFactoryLayout.class);
//		navigator.addView("logout", UserFactoryLayout.class);
//		navigator.navigateTo("user");
	}

	public void valueChange(String selectedItemPath) {

		if (selectedItemPath == null)
			return;

		String path = selectedItemPath.toLowerCase().replaceAll("\\s+", "");
		System.out.println("UI");
		System.out.println(path);
		
		if (path.equals("user")) {
			changeTab.setContent(UserFactoryLayout());
		}
		
		if (path.equals("hardware")) {
			changeTab.setContent(HardwareFactoryLayout());
		}
		
		if (path.equals("incident")) {
			changeTab.setContent(IncidentFactoryLayout());
		}
	}

	private VerticalLayout UserFactoryLayout() {

		VerticalLayout layout = new VerticalLayout();

		layout.setSizeFull();

		Grid<User> secondComponent = createUserDataGrid();
		secondComponent.setWidth(100, Unit.PERCENTAGE);

		Component firstComponent = createUserForm(secondComponent);
		firstComponent.setWidth(100, Unit.PERCENTAGE);

		layout.addComponent(firstComponent);
		layout.addComponent(secondComponent);

		return layout;
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
		FormLayout UserForm = new FormLayout();
		UserForm.setMargin(true);
		UserForm.addStyleName("outlined");
		UserForm.setSizeFull();

		final TextField name = new TextField("Name");
		name.setWidth(100.0f, Unit.PERCENTAGE);
		UserForm.addComponent(name);

		final TextField email = new TextField("E-mail");
		email.setWidth(100.0f, Unit.PERCENTAGE);
		UserForm.addComponent(email);

		final TextField passwd = new TextField("Password");
		passwd.setWidth(100.0f, Unit.PERCENTAGE);
		UserForm.addComponent(passwd);

		UserForm.addComponent(new Button("Add", __ -> {
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

		return UserForm;
	}

	public VerticalLayout HardwareFactoryLayout() {
		
		System.out.println("HARDWARE");

		VerticalLayout layout = new VerticalLayout();

		layout.setSizeFull();

		Grid<Hardware> secondComponent = createHardwareDataGrid();
		secondComponent.setWidth(100, Unit.PERCENTAGE);

		Component firstComponent = createHardwareForm(secondComponent);
		firstComponent.setWidth(100, Unit.PERCENTAGE);

		layout.addComponent(firstComponent);
		layout.addComponent(secondComponent);

		return layout;

	}

	private Grid<Hardware> createHardwareDataGrid() {
		Grid<Hardware> grid = new Grid<>();
		grid.setSizeFull();

		grid.setDataProvider(DataProvider.fromCallbacks(
				query -> hardwareRepository.fetchHardware(query.getOffset(), query.getLimit()).stream(),
				query -> hardwareRepository.countAllHardware()));

		grid.setSelectionMode(SelectionMode.NONE);

		grid.addColumn(Hardware::getHardwareName).setCaption("Name");
		grid.addColumn(Hardware::getHardwareLocation).setCaption("Location");

		return grid;
	}

	private Component createHardwareForm(Grid<Hardware> gridToUpdate) {
		FormLayout HardwareForm = new FormLayout();
		HardwareForm.setMargin(true);
		HardwareForm.addStyleName("outlined");
		HardwareForm.setSizeFull();

		final TextField name = new TextField("Name");
		name.setWidth(100.0f, Unit.PERCENTAGE);
		HardwareForm.addComponent(name);

		final TextField location = new TextField("Location");
		location.setWidth(100.0f, Unit.PERCENTAGE);
		HardwareForm.addComponent(location);

		HardwareForm.addComponent(new Button("Add", __ -> {
			LoggerFactory.getLogger(getClass()).info("Adding new hardware!");
			Hardware hardware = new Hardware();
			hardware.setHardwareId(new ObjectId());
			hardware.setHardwareLocation(location.getValue());
			hardware.setHardwareName(name.getValue());
			System.out.println(hardware);
			if (hardwareRepository.addHardware(hardware)) {
				Notification.show("Hardware added!");
				gridToUpdate.getDataProvider().refreshAll();
			} else {
				Notification.show("Count not add hardware!", Notification.Type.WARNING_MESSAGE);
			}
		}));

		return HardwareForm;
	}

	public VerticalLayout IncidentFactoryLayout() {
		
		VerticalLayout layout = new VerticalLayout();
		
		layout.setSizeFull();

			Grid<Incident> secondComponent = createDataGrid();
			secondComponent.setWidth(100, Unit.PERCENTAGE);

			Component firstComponent = createIncidentForm(secondComponent);
			firstComponent.setWidth(100, Unit.PERCENTAGE);

			layout.addComponent(firstComponent);
			layout.addComponent(secondComponent);
			
			return layout;

	}

	private Grid<Incident> createDataGrid() {
		Grid<Incident> grid = new Grid<>();
		grid.setSizeFull();

		grid.setDataProvider(DataProvider.fromCallbacks(
				query -> incidentRepository.fetchIncidents(query.getOffset(), query.getLimit()).stream(),
				query -> incidentRepository.countAllIncidents()));

		grid.setSelectionMode(SelectionMode.NONE);

		grid.addColumn(Incident::getIncidentDate).setCaption("Date");
		grid.addColumn(Incident::getIncidentDescription).setCaption("Description");

		return grid;
	}

	private Component createIncidentForm(Grid<Incident> gridToUpdate) {
		FormLayout indicentForm = new FormLayout();
		indicentForm.setMargin(true);
		indicentForm.addStyleName("outlined");
		indicentForm.setSizeFull();

		final DateTimeField dtIncident = new DateTimeField("Date");
		dtIncident.setWidth(100.0f, Unit.PERCENTAGE);
		indicentForm.addComponent(dtIncident);

		final TextField description = new TextField("Description");
		description.setWidth(100.0f, Unit.PERCENTAGE);
		indicentForm.addComponent(description);

		indicentForm.addComponent(new Button("Add", __ -> {
			LoggerFactory.getLogger(getClass()).info("Adding new incident!");
			Incident incident = new Incident();
			incident.setIncidentId(new ObjectId());
			incident.setIncidentDescription(description.getValue());
			incident.setIncidentDate(dtIncident.getValue());
			System.out.println(incident);
			if (incidentRepository.addIncident(incident)) {
				Notification.show("Hardware added!");
				gridToUpdate.getDataProvider().refreshAll();
			} else {
				Notification.show("Count not add hardware!", Notification.Type.WARNING_MESSAGE);
			}
		}));

		return indicentForm;
	}

}
