package com.varun.blogging.models;

import java.io.Serializable;

public class Blog implements Serializable{

	protected String id;
	protected String title;
	protected String plainText;
        
	public Blog(){
		// default constructor
	}
        
	public Blog(String title, String text){
                // parameterised constructor
		this.title=title;
		plainText=text;
	}
        
        public void setTitle(String title) {
                // set blog title
		this.title = title;
	}
        
	public String getTitle() {
                // return blog title
		return title;
	}
        
	public void setPlainText(String text) {
                // set text content of blog
		this.plainText = text;
	}
        
        public String getPlainText() {
                // get text content of blog
		return plainText;
	}
        
        public void setId(String id) {
                // set blog id
		this.id = id;
	}
        
        public String getId() {
                // get blog id
		return id;
	}
        
        @Override
	public String toString(){
                // get entire content as string
		return title+" "+plainText;
	}
}
