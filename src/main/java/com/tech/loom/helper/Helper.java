package com.tech.loom.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Helper {
	
	public static  boolean deleteFile(String path) {
		boolean isDelete = false;
		
		try {
			
			File file = new File(path);
			if(file.exists() && file.isFile()) {
				isDelete = file.delete();

			}else {
				System.out.println("File doesn't exist"+path);
			}
			
			
		} catch (SecurityException e) {
	        System.err.println("Permission issue: Unable to delete file at " + path);
	        e.printStackTrace();
	    } catch (Exception e) {
	        System.err.println("Error occurred while deleting file at " + path);
	        e.printStackTrace();
	    }
		
		return isDelete;
	}
	
	public static boolean saveFile(InputStream is, String path) {
	    boolean isSave = false;

	    try (FileOutputStream fos = new FileOutputStream(path)) {
	        byte b[] = new byte[is.available()];
	        is.read(b);

	        fos.write(b);
	        fos.flush();

	        isSave = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return isSave;
	}

}
