package com.varun.blogging.controllers;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varun.blogging.models.Blog;
import com.varun.blogging.models.EnlargedBlog;

@Service
public class BlogManager {

	@Autowired
	private APIManager apiManager;
	
	public String addBlog(Blog blog){
                // for adding a new blog
		return apiManager.addBlog(blog);
	}

	public Blog[] getAPageOfBlogs(int pageSize, int pageNumber){
                // get custom list of blogs equivalent to content of single blogs page
		Blog[] list = apiManager.getAllBlogs();
		Blog aPageOfBlogs[] = null;
		
                if(list.length>=(pageNumber+1)*pageSize)
			aPageOfBlogs=Arrays.copyOfRange(list, pageNumber*pageSize, ((pageNumber+1)*pageSize));
		else
			aPageOfBlogs=Arrays.copyOfRange(list, pageNumber*pageSize, list.length);
		
                return aPageOfBlogs;
	}
        
	public int getTotalBlogCount(){
                // get all blogs count
		return apiManager.getAllBlogs().length;
	}

	public EnlargedBlog getSingleBlog(String id) {
                // get full information of a single blog post
		return apiManager.getAllInfoOfBlog(id);
	}
        
	public String addNewComment(String blogId, String paraId, String comment) {
		// append a new comment to corresponding position
		return apiManager.addComment( blogId, paraId, comment);
	}
}
