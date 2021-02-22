package com.nani.jira.issue;

import java.util.List;

import com.nani.jira.JsonPrettyString;

import lombok.Data;

@Data
public class IssueSearchResult extends JsonPrettyString{
    
  private String expand;
  private Integer startAt;
  private Integer maxResults;
  private Integer total;
  private List<Issue> issues;
}
