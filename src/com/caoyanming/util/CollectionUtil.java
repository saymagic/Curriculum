package com.caoyanming.util;

import java.util.List;

public class CollectionUtil {

	public CollectionUtil() {
	}

	public  static boolean isListEmpty(List l){
		if(l.size() == 0 || l == null)
			return true;
		else 
			return false;
		
	}

}
