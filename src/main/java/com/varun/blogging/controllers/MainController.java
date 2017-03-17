package com.varun.blogging.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.net.URLDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.varun.blogging.models.Blog;
import com.varun.blogging.models.Comment;
import com.varun.blogging.models.BlogList;
import com.varun.blogging.models.EnlargedBlog;
import com.varun.blogging.models.Response;

@Controller
@RequestMapping("/blog")
public class MainController {
	
	@Autowired
	private BlogManager blogService;
	private final int pageSize = 5;
	
        
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json")
	public @ResponseBody ResponseEntity<?> addBlog(@RequestBody String blogString) {
            
		Blog blog = null;
		try {
			blog = (new ObjectMapper()).readValue(URLDecoder.decode(blogString,"UTF-8"), Blog.class);
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response("Invalid JSON",""), HttpStatus.BAD_REQUEST);
		}		
		String status;
                status = blogService.addBlog(blog);
		
                return new ResponseEntity<Response>(new Response(blog),HttpStatus.OK);
	}
        
        
	@RequestMapping(value = "/view", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseEntity<?> allBlogs(@RequestParam(value = "pageNumber", defaultValue="1") int pageNumber) {
            
                //break all records into different pages
                int allBlogCount = blogService.getTotalBlogCount();
		int totalPages = (int)Math.ceil((double)allBlogCount/(double)pageSize) ;
		
                if(1 <= pageNumber  &&  pageNumber <= totalPages){
                    
                    Blog[] blogList = blogService.getAPageOfBlogs(pageSize, pageNumber-1);
                    return new ResponseEntity<Response>(new Response(new BlogList(pageNumber, totalPages ,blogList)),HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<Response>(new Response("Invalid page request",""),HttpStatus.BAD_REQUEST);
                }
        }
        
        
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseEntity<?> getDetailedBlog(@PathVariable String id){
		
                EnlargedBlog blog = blogService.getSingleBlog(id);
		return new ResponseEntity<Response>(new Response(blog), HttpStatus.OK);
	}

        
	@RequestMapping(value = "/{blogId}/para/{paraId}", method = RequestMethod.POST, produces="application/json")
	public @ResponseBody ResponseEntity<?> addComment(@PathVariable String blogId, @PathVariable String paraId, @RequestBody String com){
		
                Comment comment = null;
		try {
			comment = (new ObjectMapper()).readValue(URLDecoder.decode(com,"UTF-8"), Comment.class);
		} 
		catch (Exception e) { }		
		
                String state = blogService.addNewComment(blogId, paraId,comment.getComment());
		return new ResponseEntity<Response>(new Response(state,comment.getComment()), HttpStatus.OK);
	}
}
