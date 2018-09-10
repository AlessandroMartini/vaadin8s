package com.v2.vaadin8s.model.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.jnosql.artemis.Column;
import org.jnosql.artemis.Entity;
import org.jnosql.artemis.Id;

import com.v2.vaadin8s.utils.StringUtils;

@Entity
public class Incident {

	@Id
	private ObjectId incidentId;

	@Column
	private Hardware incidentHardware;

	@Column
	private LocalDateTime incidentDate;

	@Column
	private String incidentDescription;

	public Incident() {
	}

	public ObjectId getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(ObjectId incidentId) {
		this.incidentId = incidentId;
	}

	public Hardware getIncidentHardware() {
		return incidentHardware;
	}

	public void setIncidentHardware(Hardware incidentHardware) {
		this.incidentHardware = incidentHardware;
	}

	public LocalDateTime getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(LocalDateTime incidentDate) {
		this.incidentDate = incidentDate;
	}

	public String getIncidentDescription() {
		return incidentDescription;
	}

	public void setIncidentDescription(String incidentDescription) {
		this.incidentDescription = incidentDescription;
	}

	//@formatter:off
	@Override
	public String toString() {
		return String.format("%s {\n %s: %s\n, %s: %s\n, %s: %s\n }", 
				StringUtils.INCIDENT_TITLE.getString(),
				StringUtils.INCIDENT_ID.getString(), 
				this.incidentId, 
				StringUtils.INCIDENT_DATE.getString(),
				this.incidentDate.toString(), 
				StringUtils.INCIDENT_DESCRIPTION.getString(), 
				this.incidentDescription);
	}
	//@formatter:on

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((incidentDate == null) ? 0 : incidentDate.hashCode());
		result = prime * result + ((incidentDescription == null) ? 0 : incidentDescription.hashCode());
		result = prime * result + ((incidentHardware == null) ? 0 : incidentHardware.hashCode());
		result = prime * result + ((incidentId == null) ? 0 : incidentId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Incident other = (Incident) obj;
		if (incidentDate == null) {
			if (other.incidentDate != null)
				return false;
		} else if (!incidentDate.equals(other.incidentDate))
			return false;
		if (incidentDescription == null) {
			if (other.incidentDescription != null)
				return false;
		} else if (!incidentDescription.equals(other.incidentDescription))
			return false;
		if (incidentHardware == null) {
			if (other.incidentHardware != null)
				return false;
		} else if (!incidentHardware.equals(other.incidentHardware))
			return false;
		if (incidentId == null) {
			if (other.incidentId != null)
				return false;
		} else if (!incidentId.equals(other.incidentId))
			return false;
		return true;
	}

}
