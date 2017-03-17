package com.varun.blogging.models;

public class EnlargedBlog extends Blog {
	
	private Paragraph[] paragraphList;
	
	public EnlargedBlog(){
		super();
	}
	
	public EnlargedBlog(String id, String title, String plainText){
                // get full information of a blog post
		super(title, plainText);
		setId(id);
		mapParagraphs();
	}

	private void mapParagraphs() {
                // split the text content of blog into paragraphs
                // assuming para-break char as \n\n\n
		String paras[] = plainText.split("\n\n\n");
		int paraCount = 0;
		this.paragraphList = new Paragraph[paras.length];
		for(String para:paras){			
			this.paragraphList[paraCount] = new Paragraph( "" + paraCount, para);
			paraCount++;
		}
	}
        
	public Paragraph[] getParagraphs() {
                // get list of paragraphs
		return paragraphList;
	}

	public void setParagraphs(Paragraph[] paragraphs) {
                // set paragraphs list
		this.paragraphList = paragraphs;
	}
}
