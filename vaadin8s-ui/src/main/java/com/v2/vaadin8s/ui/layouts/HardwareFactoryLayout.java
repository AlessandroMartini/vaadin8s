package com.v2.vaadin8s.ui.layouts;

import javax.inject.Inject;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import com.v2.vaadin8s.model.entity.Hardware;
import com.v2.vaadin8s.repository.HardwareRepository;
import com.vaadin.data.provider.DataProvider;
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
public class HardwareFactoryLayout extends VerticalLayout implements View {

	@Inject
	private HardwareRepository hardwareRepository;

	public HardwareFactoryLayout() {
	        setSizeFull();
	        setSizeFull();
			// this.setSplitPosition(250, Unit.PIXELS);

			Grid<Hardware> secondComponent = createDataGrid();
			secondComponent.setWidth(100, Unit.PERCENTAGE);

			Component firstComponent = createHardwareForm(secondComponent);
			firstComponent.setWidth(100, Unit.PERCENTAGE);

			addComponent(firstComponent);
			addComponent(secondComponent);

	}

	private Grid<Hardware> createDataGrid() {
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
		FormLayout hardwareForm = new FormLayout();
		hardwareForm.setMargin(true);
		hardwareForm.addStyleName("outlined");
		hardwareForm.setSizeFull();

		final TextField name = new TextField("Name");
		name.setWidth(100.0f, Unit.PERCENTAGE);
		hardwareForm.addComponent(name);

		final TextField location = new TextField("Location");
		location.setWidth(100.0f, Unit.PERCENTAGE);
		hardwareForm.addComponent(location);

		hardwareForm.addComponent(new Button("Add", __ -> {
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

		return hardwareForm;
	}
}