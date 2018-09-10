package com.v2.vaadin8s.repository;

import java.util.Collection;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.jnosql.artemis.document.DocumentTemplate;
import static org.jnosql.diana.api.document.query.DocumentQueryBuilder.select;
import com.v2.vaadin8s.model.entity.Incident;

@ApplicationScoped
public class IncidentRepository {

	private DocumentTemplate template;

	public boolean addIncident(Incident incident) {
		return template.insert(incident) != null;
	}

	public Collection<Incident> getAllIncidents() {
		return template.select(select().from("eIncident").build());
	}

	public int countAllIncidents() {
		return getAllIncidents().size();
	}

	public List<Incident> fetchIncidents(int offset, int limit) {
		return template.select(select().from("eIncident").start(offset).limit(limit).build());
	}

}
