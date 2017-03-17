package com.varun.blogging.models;

public class Response {
    
	private String status = "OK";
	private Object data;
	
	public Response(){
                // default constructor
        }
        
	public Response(Object dat){
                // parameterised constructor
		data=dat;
	}
        
	public Response(String st, Object da){
                // parameterised constructor
		status=st;
		data=da;
	}
        
        public void setStatus(String status) {
                // set status of response
		this.status = status;
	}
        
	public String getStatus() {
                // get status of response
		return status;
	}
        
	public void setData(Object data) {
                // set data of response
		this.data = data;
	}
        
	public Object getData() {
                // get data of response
		return data;
	}
}
