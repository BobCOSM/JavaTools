package com.maxd.fileoperator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileBuffer {
	private static final int BUF_SIZE = 1024;
	private ArrayList<char[]> mBuf = null;
	private int mBufSize = BUF_SIZE;
	private int topBufOffset = 0;
	public FileBuffer(){
		mBuf = new ArrayList<>();
		mBuf.add(new char[mBufSize]);
	}
	
	public FileBuffer(int bufSize){
		this();
		mBufSize = bufSize;
	}
	
	private boolean copyChars(char[] target,int tOffset,char[] source,int sOffset,int size){
		boolean resBool = false;
		int copySize = (source.length - sOffset + 1) >= size ? 
				size : source.length - sOffset + 1;
		if((target.length - tOffset) < copySize  ){
			return resBool;
		}
		for(int i = 0;i < copySize ;i++){
			target[tOffset + i] = source[sOffset + i];
		}
		resBool = true;
		return resBool;
	}
	
	private boolean pushData(char[] cbuf,int size){
		boolean resBool = false;
		int residueSize = mBufSize - topBufOffset;
		int copySize = -1;
		copySize = (size > cbuf.length) ? cbuf.length : size;
		char[] topBuf = mBuf.get(mBuf.size() - 1);
		if(residueSize >= copySize){
			copyChars(topBuf,topBufOffset,cbuf,0,copySize);
			topBufOffset += copySize;
		} else {
			if(residueSize > 0){
				copyChars(topBuf,topBufOffset,cbuf,0,residueSize);	
			}
			char[] e = new char[mBufSize];
			mBuf.add(e);
			topBufOffset = 0;
			resBool = pushData(cbuf,copySize - residueSize);
//			copyChars(topBuf,0,cbuf,residueSize,copySize - residueSize);
		}
		return resBool;
	}
	
//	
//	public int getData(char[] cbuf,int offset,int size){
//		int realSize = 0;
//		
//		return realSize;
//	}
	
	public boolean loadFile(File sourceFile,int offset,int size){
		boolean resBool = false;
		char[] cbuf = new char[mBufSize];
		if(sourceFile == null || !sourceFile.exists()){
			return resBool;
		}
		FileReader fr = null;
		try {
			fr = new FileReader(sourceFile);
			fr.skip(offset);
			int needReadSize = mBufSize;
			if(size < mBufSize){
				needReadSize = size;
			}
			int realReadSize = -1;
			int sizeSum = 0;
			resBool = true;
			while((realReadSize = fr.read(cbuf,0,needReadSize)) != -1){
				sizeSum += realReadSize;
				if((sizeSum + needReadSize) > size){
					needReadSize = size - sizeSum;
				}
				if(!pushData(cbuf,realReadSize)){
					resBool = false;
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resBool = false;
		}finally{
			try {
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resBool = false;
			}
		}
		return resBool;
	}
}
