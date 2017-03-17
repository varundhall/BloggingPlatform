package com.varun.blogging.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paragraph implements Serializable{
	
	private String paragraphId;
	private String paragraph;
	private List<String> commentList = new ArrayList<String>();
	public Paragraph(){}
	
	public Paragraph(String n, String p){
                // parameterised constructor
		paragraphId = n;
		paragraph = p;		
	}
        
	public String getParagraph() {
                // get paragraph
		return paragraph;
	}
        
	public void setParagraph(String paragraph) {
                // set paragraph
		this.paragraph = paragraph;
	}
        
	public List<String> getComments() {
                // get list of all comments linked to particular paragraph
		return commentList;
	}
        
	public void setComments(List<String> commentList) {
                // set list of all comments linked to particular paragraph
		this.commentList = commentList;
	}
        
	public String getParagraphId() {
                // get ID of paragraph
		return paragraphId;
	}
        
	public void setParagraphId(String paragraphId) {
                // set ID of paragraph
		this.paragraphId = paragraphId;
	}
        
        @Override
	public String toString(){
                // get entire content as string
		return paragraphId + " " + paragraph + " " + commentList;
	}
}
