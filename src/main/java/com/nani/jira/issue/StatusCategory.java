package com.nani.jira.issue;

import lombok.Data;

@Data
public class StatusCategory {
	private String self;
	private String id;
	private String key;
	private String colorName;
	private String name;	
}
