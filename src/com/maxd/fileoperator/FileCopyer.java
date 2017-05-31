package com.maxd.fileoperator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileCopyer {
	
	private static int FILEBUF_SIZE = 1024;
	
	public static boolean copyFile(String sourceFilePath,String targetDirPath){
		boolean resBool = false;
		File sourceFile = new File(sourceFilePath);
		File targetDir = new File(targetDirPath);
		File targetFile = new File(targetDir.getAbsolutePath() + File.separator + sourceFile.getName());
		resBool = copyFile(sourceFile,targetFile);
		return resBool;
	}
	
	public static boolean copyFile(File sourceFile,File targetFile){
		boolean resBool = false;
		if(sourceFile == null ||!sourceFile.exists()
				|| !sourceFile.isFile() || targetFile == null){
			return resBool;
		}
		if(!targetFile.exists()){
			File targetDir = targetFile.getParentFile();
			if(!targetDir.exists()){
				targetDir.mkdirs();
			}
			try {
				targetFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return resBool;
			}
		}
		FileReader fr = null;
		FileWriter fw = null;
		char[] cbuf = new char[FILEBUF_SIZE];
		try {
			fr = new FileReader(sourceFile);
			fw = new FileWriter(targetFile);
			int size = -1;
			while((size = fr.read(cbuf)) != -1){
				fw.write(cbuf, 0, size);
			}
			resBool = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return resBool;
		} finally{
			try {
				fr.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return resBool;
			}
		}
		return resBool;
	}
	
	public static boolean copyDirectiory(String sourceDir, String targetDir) {
		boolean resBool = false;
		(new File(targetDir)).mkdirs();
		File f = new File(sourceDir);
		if (!f.exists()) {
			return resBool;
		}
		if (f.isFile()) {
			resBool = copyFile(f, new File(targetDir));
			return resBool;
		}

		File[] file = f.listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {

				File sourceFile = file[i];
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				resBool = copyFile(sourceFile, targetFile);
			} else if (file[i].isDirectory()) {
				String dir1 = f.getAbsolutePath() + File.separator + file[i].getName();
				String dir2 = targetDir + "/" + file[i].getName();
				resBool = copyDirectiory(dir1, dir2);
			}
		}
		return resBool;
	}
}
