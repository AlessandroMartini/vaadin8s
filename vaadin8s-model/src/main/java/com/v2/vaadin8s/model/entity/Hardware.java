package com.v2.vaadin8s.model.entity;

import org.bson.types.ObjectId;
import org.jnosql.artemis.Column;
import org.jnosql.artemis.Embeddable;
import org.jnosql.artemis.Entity;
import org.jnosql.artemis.Id;

import com.v2.vaadin8s.utils.StringUtils;

@Entity
@Embeddable
public class Hardware {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Type(type = "objectid")
	private ObjectId hardwareId;

	@Column
	private String hardwareName;

	@Column
	private String hardwareLocation;

	public Hardware() {
	}

	public ObjectId getHardwareId() {
		return hardwareId;
	}

	public void setHardwareId(ObjectId hardwareId) {
		this.hardwareId = hardwareId;
	}

	public String getHardwareName() {
		return hardwareName;
	}

	public void setHardwareName(String hardwareName) {
		this.hardwareName = hardwareName;
	}

	public String getHardwareLocation() {
		return hardwareLocation;
	}

	public void setHardwareLocation(String hardwareLocation) {
		this.hardwareLocation = hardwareLocation;
	}

	//@formatter:off
	@Override
	public String toString() {
		return String.format("%s {\n %s: %s\n, %s: %s\n, %s: %s\n }", 
				StringUtils.HARDWARE_TITLE.getString(),
				StringUtils.HARDWARE_ID.getString(), 
				this.hardwareId, 
				StringUtils.HARDWARE_NAME.getString(),
				this.hardwareName, 
				StringUtils.HARDWARE_LOCATION.getString(), 
				this.hardwareLocation);
	}
	//@formatter:on

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hardwareId == null) ? 0 : hardwareId.hashCode());
		result = prime * result + ((hardwareLocation == null) ? 0 : hardwareLocation.hashCode());
		result = prime * result + ((hardwareName == null) ? 0 : hardwareName.hashCode());
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
		Hardware other = (Hardware) obj;
		if (hardwareId == null) {
			if (other.hardwareId != null)
				return false;
		} else if (!hardwareId.equals(other.hardwareId))
			return false;
		if (hardwareLocation == null) {
			if (other.hardwareLocation != null)
				return false;
		} else if (!hardwareLocation.equals(other.hardwareLocation))
			return false;
		if (hardwareName == null) {
			if (other.hardwareName != null)
				return false;
		} else if (!hardwareName.equals(other.hardwareName))
			return false;
		return true;
	}

}
