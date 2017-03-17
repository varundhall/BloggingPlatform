package com.varun.blogging.models;

public class BlogList {

	private int currentPage;
	private int totalPages;
	private Blog[] blogs;
	
	public BlogList(){
		// default constructor
	}
        
	public BlogList(int currentPage, int totalPages, Blog[] blogs){
                // parameterised constructor
		this.currentPage = currentPage;
		this.totalPages = totalPages;
		this.blogs = blogs;
	}
        
        public void setCurrentPage(int currentPage) {
                // set current page number
		this.currentPage = currentPage;
	}
        
	public int getCurrentPage() {
                // get current page number
		return currentPage;
	}
        
	public void setTotalPages(int totalPages) {
                // set total number of pages
		this.totalPages = totalPages;
	}
        
	public int getTotalPages() {
                // get total number of pages
		return totalPages;
	}
	
        public void setBlogs(Blog[] blogs) {
                // set blogs
		this.blogs = blogs;
	}
        
	public Blog[] getBlogs() {
                // get all blogs in a list
		return blogs;
	}	
}
