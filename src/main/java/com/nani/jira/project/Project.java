package com.nani.jira.project;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;

import lombok.Data;

import com.nani.jira.JsonPrettyString;
import com.nani.jira.issue.Component;
import com.nani.jira.issue.IssueType;
import com.nani.jira.issue.Lead;
import com.nani.jira.issue.Version;



@Data
@JsonIgnoreProperties({"assigneeType", "roles"
})
public class Project extends JsonPrettyString{
	private String expand;
	private String self;
	
	private String id;
	private String key;
	
	private String description;
	private String name;
	private String url;
	
	private DateTime startDate;
	
	private Lead lead;
	
	private AvatarUrl avatarUrls; 
	private ProjectCategory projectCategory;
	
	private List<Component> components;
	private List<IssueType> issueTypes;
	private List<Version> versions;
	
	private String projectTypeKey;
}
