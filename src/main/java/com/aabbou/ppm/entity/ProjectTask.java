package com.aabbou.ppm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProjectTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "project_sequence", updatable = false)
	private String projectSequence;

	@NotBlank(message = "Please include a project summary")
	@Column(name = "summary")
	private String summary;

	@Column(name = "acceptance_criteria")
	private String acceptanceCriteria;

	@Column(name = "status")
	private String status;

	@Column(name = "priority")
	private Integer priority;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "due_date")
	private Date dueDate;

	// ManyToOne with Backlog
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "backlog_id", updatable = false, nullable = false)
	@JsonIgnore
	private BackLog backlog;

	@Column(name = "project_identifier", updatable = false)
	private String projectIdentifier;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created_At", updatable = false)
	private Date create_At;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "updated_At")
	private Date update_At;

	public ProjectTask() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectSequence() {
		return projectSequence;
	}

	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public Date getCreate_At() {
		return create_At;
	}

	public void setCreate_At(Date create_At) {
		this.create_At = create_At;
	}

	public Date getUpdate_At() {
		return update_At;
	}

	public void setUpdate_At(Date update_At) {
		this.update_At = update_At;
	}

	@PrePersist
	protected void onCreate() {
		this.create_At = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.update_At = new Date();
	}

	public BackLog getBacklog() {
		return backlog;
	}

	public void setBacklog(BackLog backlog) {
		this.backlog = backlog;
	}

	@Override
	public String toString() {
		return "ProjectTask{" + "id=" + id + ", projectSequence='"
				+ projectSequence + '\'' + ", summary='" + summary + '\''
				+ ", acceptanceCriteria='" + acceptanceCriteria + '\''
				+ ", status='" + status + '\'' + ", priority=" + priority
				+ ", dueDate=" + dueDate + ", projectIdentifer='"
				+ projectIdentifier + '\'' + ", create_At=" + create_At
				+ ", update_At=" + update_At + '}';
	}

	public enum TaskStatus {
		TO_DO(0, "To do"), IN_PROGRESS(1, "In Progress"), DONE(2, "Done");

		TaskStatus(int i, String string) {
		}

		private int index;
		private String label;

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

	}

	public enum TaskPriority {
		LOW(0, "Low"), MEDIUM(1, "Medium"), HIGH(2, "High");

		TaskPriority(int i, String string) {
		}

		private int index;
		private String label;

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

	}
}
