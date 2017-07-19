package com.stpl.automation.stplstores.enumerations;

public enum Categories {
	
	APPAREL("Apparel"),Accessories("Accessories");

	private String categoryName;

	Categories(final String categoryName){
		this.categoryName = categoryName;
	}
	
	public String getCategoryName(){
		return categoryName;
	}
   
}
