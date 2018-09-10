package com.v2.vaadin8s.ui.layouts;

import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import com.v2.vaadin8s.model.entity.Incident;
import com.v2.vaadin8s.repository.IncidentRepository;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;


@SuppressWarnings("serial")
public class IncidentFactoryLayout extends VerticalLayout implements View {

	@Inject
	private IncidentRepository incidentRepository;

	public IncidentFactoryLayout() {
	        setSizeFull();
	        setSizeFull();
			// this.setSplitPosition(250, Unit.PIXELS);

			Grid<Incident> secondComponent = createDataGrid();
			secondComponent.setWidth(100, Unit.PERCENTAGE);

			Component firstComponent = createIncidentForm(secondComponent);
			firstComponent.setWidth(100, Unit.PERCENTAGE);

			addComponent(firstComponent);
			addComponent(secondComponent);

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