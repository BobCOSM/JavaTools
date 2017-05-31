package com.maxd.main;

import com.maxd.fileoperator.FileCopyer;

public class Main {
	public static void main(String[] args){
//		System.out.println("hello world ! ");
		testCopyFile();
	}
	static void testCopyFile(){
		String sourceFilePath = "";
		String targetDirPath = "";
		boolean resBool = false;
		//////////////
		//test copy not exists file;
		sourceFilePath = "E:/testSourceDir/test";
		targetDirPath = "E:/testTargetDir/";
//		resBool = FileCopyer.copyFile(sourceFilePath, targetDirPath);
//		System.out.println(" test copy not exists file : " + (resBool?"failed" : "succeed"));
		///////////////////////////
		//test copy exists file;
		sourceFilePath = "E:/testSourceDir/testS";
//		resBool = FileCopyer.copyFile(sourceFilePath, targetDirPath);
//		System.out.println(" test copy exists file : " + (!resBool?"failed" : "succeed"));
		
		////////////////////////////////
		//test copy dir
		String sourceDirPath = "E:/testSourceDir";
		resBool = FileCopyer.copyDirectiory(sourceDirPath, targetDirPath);
		System.out.println(" test copy exists Dir : " + (!resBool?"failed" : "succeed"));
	}
}
