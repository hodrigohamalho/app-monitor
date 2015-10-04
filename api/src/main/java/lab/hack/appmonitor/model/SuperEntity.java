package lab.hack.appmonitor.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SuperEntity implements Serializable{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="created_at")
//	private Date createdAt;
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="updated_at")
//	private Date updatedAt;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
//	public Date getCreatedAt() {
//		return createdAt;
//	}
//	
//	public void setCreatedAt(Date createdAt) {
//		this.createdAt = createdAt;
//	}
//	
//	public Date getUpdatedAt() {
//		return updatedAt;
//	}
//	
//	public void setUpdatedAt(Date updatedAt) {
//		this.updatedAt = updatedAt;
//	}
//
//	@PrePersist
//	protected void onCreate() {
//		updatedAt = createdAt = new Date();
//	}
//
//	@PreUpdate
//	protected void onUpdate() {
//		updatedAt = new Date();
//	}
	
}
