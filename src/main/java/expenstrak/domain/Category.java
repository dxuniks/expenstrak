package expenstrak.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.repository.CrudRepository;

@Entity ( name = "CATEGORIES" )
public class Category {
	
	protected Category(){};
	
	@Id
	@GeneratedValue 
	private long id;
	
	@Column(name="NAME", nullable = false)
	private String name;
	
	@Column(name="DESCRIPTION", length = 512)
	private String description;
	
	@Column(name="LAST_UPDATED",
			updatable=false,
			columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdate;

	public Category(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", description=" + description + ", lastUpdate=" + lastUpdate
				+ "]";
	}
	
}
