package com.nani.jira.issue;

import lombok.Data;

import org.joda.time.DateTime;

import com.nani.jira.JsonPrettyString;

@Data

public class Attachment extends JsonPrettyString{	
	private String id;
	private String self;
	private String filename;
	private Reporter author;
	
	private DateTime created;
	
	private Integer size;
	private String mimeType;	
	private String content;
	private String thumbnail;
}
