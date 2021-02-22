package com.nani.jira.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nani.jira.JsonPrettyString;
import com.nani.jira.issue.Attachment;
import com.nani.jira.issue.Issue;
import com.nani.jira.issue.IssueFields;
import com.nani.jira.issue.IssueSearchResult;
import com.nani.jira.issue.IssueType;
import com.nani.jira.issue.Priority;
import com.nani.jira.services.IssueService;
import com.nani.jira.util.HttpConnectionUtil;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IssueTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

	static String PROJECT_KEY = null;
	static String ISSUE_KEY = null;
	static String REPORTER = null;
	static String ASSIGNEE = null;
	
    @BeforeClass
    public static void beforeClass() {
        HttpConnectionUtil.disableSslVerification();
        
        PROJECT_KEY = System.getProperty("jira.test.project", "TES");
        ISSUE_KEY = System.getProperty("jira.test.issue", "TES-3");
        ASSIGNEE = System.getProperty("jira.test.assignee", "nani");
        REPORTER = System.getProperty("jira.test.reporter", "gitlab");
    }

    @Test
    public void aCreateIssue() throws IOException, ConfigurationException {

        Issue issue = new Issue();
        
        IssueFields fields = new IssueFields();
        
        fields.setProjectKey("TES")
              .setSummary("something's wrong")
              .setIssueTypeName("PROJECT_LEAD")
              .setDescription("Full description for issue")
              .setAssigneeName("PROJECT_LEAD");
        
      
        
        issue.setFields(fields);
        
        logger.info(issue.toPrettyJsonString());
        
        IssueService issueService = new IssueService();
        
        Issue genIssue = issueService.createIssue(issue);       
        
        ISSUE_KEY = genIssue.getKey();
        
        //Print Generated issue
        logger.info(genIssue.toPrettyJsonString());
    }
    
    @Test
    public void bgetIssue() throws IOException, ConfigurationException {

        IssueService issueService = new IssueService();
        Issue issue =  issueService.getIssue(ISSUE_KEY);

        logger.debug(issue.toPrettyJsonString());

        // attachment info
        List<Attachment> attachs = issue.getFields().getAttachment();
        for ( Attachment a : attachs) {
            logger.debug("Attachment:" + a.toPrettyJsonString());           
        }
        
        IssueFields fields = issue.getFields();
        
        // Project key
        logger.debug("Project Key:" + fields.getProject().getKey());
                
        //issue type
        logger.debug("IssueType:" + fields.getIssuetype().toPrettyJsonString());
        
        // issue description
        logger.debug("Issue Description:" + fields.getDescription());       
    }
        
    @Test
    public void cUploadAttachments() throws IOException, ConfigurationException {
        Issue issue = new Issue();
        
        issue.setKey(ISSUE_KEY);
                
        issue.addAttachment(new File("attachment.png"));
        issue.addAttachment("test.pdf");
        
        IssueService issueService = new IssueService();
        issueService.postAttachment(issue);
    }
    
    @Test
    public void dgetAllIssueType() throws IOException, ConfigurationException {

        IssueService issueService = new IssueService();
        List<IssueType> issueTypes =  issueService.getAllIssueTypes();

        for(IssueType i : issueTypes) {
            logger.info(i.toPrettyJsonString());
        }
    }
    
    @Test
    public void egetAllPriorities() throws IOException, ConfigurationException {

        IssueService issueService = new IssueService();
        List<Priority> priority =  issueService.getAllPriorities();

        for(Priority p : priority) {
            logger.info(p.toPrettyJsonString());
        }
    }
    
    @Test
    public void fGetCustomeFields() throws IOException, ConfigurationException {
        IssueService issueService = new IssueService();
        Issue issue =  issueService.getIssue(ISSUE_KEY);

        Map<String, Object> fields = issue.getFields().getCustomfield();
        
        logger.info("JSON : " + JsonPrettyString.mapToPrettyJsonString(fields));
        for( String key : fields.keySet() ){
            logger.info("Field Name: " + key + ",value:" + fields.get(key));
        }
    }

    @Test
    public void getIssuesFromQuery() throws IOException, ConfigurationException {
        IssueService issueService = new IssueService();
        final String query = "status=open and assignee = currentUser()";
        
        final IssueSearchResult issues = issueService.getIssuesFromQuery(query);

        assertTrue("Should be at least 1 open issue", !issues.getIssues().isEmpty());

    }

}
