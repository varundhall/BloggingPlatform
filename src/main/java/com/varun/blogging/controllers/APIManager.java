package com.varun.blogging.controllers;

import java.lang.reflect.Method;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.*;

import com.varun.blogging.models.Blog;
import com.varun.blogging.models.EnlargedBlog;
import com.varun.blogging.models.Paragraph;

@Component
public class APIManager {
	
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init(){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public String addBlog(Blog blog){
		// add a new blog post - also populates corresponding paragraphs
		String newBlogId = Long.toString((long)(Math.abs(UUID.randomUUID().getLeastSignificantBits()/Math.pow(10,11))));
		blog.setId(newBlogId);
		
                Object[] params = new Object[] {newBlogId, blog.getTitle(), blog.getPlainText()};		
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
		String query = "insert into blog (id, title, plainText) VALUES (?,?,?)";
                int updateCount = jdbcTemplate.update(query, params, types);
		
                EnlargedBlog allBlogInfo = new EnlargedBlog(newBlogId, blog.getTitle(), blog.getPlainText());
		int counter = 0, updateCountPara = 0;
		
                for(Paragraph p:allBlogInfo.getParagraphs()){
			params = new Object[] {newBlogId, p.getParagraphId(),p.getParagraph()};		
			types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
			query = "insert into blogPara (blog_id, paragraphId, paragraph) VALUES (?,?,?)";
                        updateCountPara = jdbcTemplate.update(query, params, types);
			counter++;
		}
		
                if(updateCount == 1 && updateCountPara == allBlogInfo.getParagraphs().length){
			return "Ok";
		}
                else{
			return "Error";
                }
	}

	public Blog[] getAllBlogs() {
                // get consolidated information of all blog posts
		String query = "select * from blog";
                List<Map<String, Object>> list = jdbcTemplate.queryForList(query);
		Class blogClass = Blog.class;
		Method[] methods = blogClass.getMethods();
		Map<String, Method> map = new HashMap<String, Method>();
		
                String name = "";
		Blog[] blogList = new Blog[list.size()];
		for(Method m:methods){
			name = m.getName();
			if(name.contains("set"))//track setters
				map.put(name.substring(name.indexOf("set")+3).toLowerCase(), m);
		}
		
                int blogCounter=0;
		Blog blog = null;
		for(Map<String, Object> row: list){
			blog = new Blog();
			for(String value: row.keySet()){
				try {
					if(map.get(value.toLowerCase()) != null)
						map.get(value.toLowerCase()).invoke(blog, row.get(value));					
				} catch (Exception e) {	}
			}
			blogList[blogCounter] = blog;
			blogCounter++;
		}
		
                return blogList;
	}

	public EnlargedBlog getAllInfoOfBlog(String id) {
		//get all info of a blog post
                EnlargedBlog aEnlargedBlog = new EnlargedBlog();
		aEnlargedBlog.setId(id);
		
                String query = "select blog.title, blog.plainText from blog where blog.id = '";
                Map<String, Object> blog = jdbcTemplate.queryForMap(query + id + "'");
                query = "select blogPara.paragraphId, blogPara.paragraph from blog,  blogPara  where  blog.id = blogPara.blog_id  and blog.id = '";
		List<Map<String, Object>> paragraphs = jdbcTemplate.queryForList(query + id + "'");
                query = "select blogInfo.paragraphId, blogInfo.comment from blog, blogInfo, blogPara  where  blog.id = blogInfo.id and  blog.id = blogPara.blog_id and blogInfo.paragraphId = blogPara.paragraphId and blog.id = '";
		List<Map<String, Object>> comments = jdbcTemplate.queryForList(query + id + "'");
		
                aEnlargedBlog.setTitle((String)blog.get("title"));
		aEnlargedBlog.setPlainText((String)blog.get("plainText"));
		
                Paragraph para = null;
		Map<String,Paragraph> map = new HashMap<String, Paragraph>();
		if(paragraphs.size()>0){
			for(Map<String, Object> paragraph: paragraphs){
				para = new Paragraph();
				para.setParagraphId((String)paragraph.get("paragraphId"));
				para.setParagraph((String)paragraph.get("paragraph"));
				map.put(para.getParagraphId(), para);
			}
			for(Map<String, Object> comment: comments){
				para = map.get(comment.get("paragraphId"));
				List<String> comm = para.getComments();
				if(comm == null){
					comm = new ArrayList<String>();
					para.setComments(comm);
				}
				comm.add((String)comment.get("comment"));
			}
		}
                
		aEnlargedBlog.setParagraphs(map.values().toArray(new Paragraph[0]));
		return aEnlargedBlog;
	}

	public String addComment(String blogId, String paraId, String comment) {
                // add new comment to particular location
		String newCommentId = Long.toString((long)(Math.abs(UUID.randomUUID().getLeastSignificantBits()/Math.pow(10,11))));
		Object[] params = new Object[] {blogId, paraId, newCommentId, comment};		
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR};
		
                int updateCount=0;
		try{
			String query = "insert into blogInfo (id, paragraphId, comment_id, comment) VALUES (?,?,?,?)";
                        updateCount=jdbcTemplate.update(query, params, types);
		}catch(Exception e){    }
                
		if(updateCount == 1){
                    return "Ok";
                }
                else{
                    return "Error";
                }
	}
}
