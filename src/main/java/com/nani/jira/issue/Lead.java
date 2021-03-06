package com.nani.jira.issue;

import com.nani.jira.project.AvatarUrl;

import lombok.Data;

@Data
public class Lead {
	private String self;
	private String name;
	private AvatarUrl avatarUrls;
	private String displayName;
	private Boolean active;
	private String key;
}
