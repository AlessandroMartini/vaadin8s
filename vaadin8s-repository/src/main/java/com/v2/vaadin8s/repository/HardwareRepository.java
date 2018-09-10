package com.v2.vaadin8s.repository;

import java.util.Collection;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.jnosql.artemis.document.DocumentTemplate;
import static org.jnosql.diana.api.document.query.DocumentQueryBuilder.select;
import com.v2.vaadin8s.model.entity.Hardware;

@ApplicationScoped
public class HardwareRepository {

	private DocumentTemplate template;

	public boolean addHardware(Hardware hardrware) {
		return template.insert(hardrware) != null;
	}

	public Collection<Hardware> getAllHardware() {
		return template.select(select().from("eHardware").build());
	}

	public int countAllHardware() {
		return getAllHardware().size();
	}

	public List<Hardware> fetchHardware(int offset, int limit) {
		return template.select(select().from("eHardware").start(offset).limit(limit).build());
	}

}
