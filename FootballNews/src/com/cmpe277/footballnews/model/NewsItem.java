package com.cmpe277.footballnews.model;

import java.util.ArrayList;
import java.util.HashMap;

//import android.content.Context;

public class NewsItem {
	private String title;
	private String description;
	private String link;
	private String date;
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String name){
		this.title = name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getLink(){
		return link;
	}
	
	public void setLink(String link){
		this.link=link;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
