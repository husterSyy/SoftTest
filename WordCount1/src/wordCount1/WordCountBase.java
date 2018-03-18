package wordCount1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
	/*
	 * *
	 * 空格算字符
	 * 由空格或逗号分割开的都视为单词，且不做单词的有效性校验，
	 * 例如：thi#,that视为用逗号隔开的2个单词。
	wc.exe -c file.c     //返回文件 file.c 的字符数
	wc.exe -w file.c     //返回文件 file.c 的单词总数
	wc.exe -l file.c     //返回文件 file.c 的总行数
	wc.exe -o outputFile.txt     //将结果输出到指定文件outputFile.txt
	*/

public class WordCountBase {
	
	public static void main(String[] args) throws IOException{
		while(true){
			System.out.println("wc.exe -c file.c     //返回文件 file.c 的字符数");
			System.out.println("wc.exe -w file.c     //返回文件 file.c 的单词总数");
			System.out.println("wc.exe -l file.c     //返回文件 file.c 的总行数");
			System.out.println("wc.exe -o outputFile.txt     //将结果输出到指定文件outputFile.txt");
		 File file=null;
		 File tofile=null;
       Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        String str2=sc.nextLine();
        
        String[] s=str.split(" ");
        String[] s2=str2.split(" ");
			if (s.length < 3) {               //-c -w -l 单独使用
				if (s[0].equals("-c")) {
					file = new File(s[1]);
					countCharacters(file);

				} else if (s[0].equals("-w")) {
					file = new File(s[1]);
					countWords(file);

				} else if (s[0].equals("-l")) {
					file = new File(s[1]);
					countLines(file);

				}
				 if(s2[0].equals("-o")){
					//file=new File(s[1]);
					tofile=new File(s2[1]);
					output(file, tofile,s);
				}
			}
			else if(s.length>=3){           //-c -w -l 组合使用
				int i=0;
				while(i<=s.length-2){
					if (s[i].equals("-c")) {
						file = new File(s[s.length-1]);
						countCharacters(file);

					} else if (s[i].equals("-w")) {
						file = new File(s[s.length-1]);
						countWords(file);

					} else if (s[i].equals("-l")) {
						file = new File(s[s.length-1]);
						countLines(file);

					}
					i++;
				}
				 if(s2[0].equals("-o")){
						//file=new File(s[1]);
						tofile=new File(s2[1]);
						output(file, tofile,s);
					}
				
			}
			
			//output(file, tofile,s);
		}	
	 }
	private static int countCharacters(File file) throws FileNotFoundException, IOException {

		FileReader fr=new FileReader(file);    
		//InputStreamReader isr=new InputStreamReader(in);
		BufferedReader br=new BufferedReader(fr);
		int b=0; 
		int countC=0;       //两单词间空格和其他可看得见的字母符号均视为一个字符
		int countW=0;      //除空格回车换行外，用逗号隔开的字符组成均为单词
		int countL=0;
		int pre=0;
		while((b =br.read())!=-1){
			//统计字符数
			
			 if(b==' '&&pre=='\n') {       //文本最后一行回车换行并另起一行空格，最后才读到-1，文件末尾        
				 continue;
			 }
			 else if(b==' '&&pre==' '){        //判断文本中出现的连续空格
				 continue;
			 }
			 else if(b==','&&pre==' '){       
				 countW++;
				 continue;
			 }
			 else if(b==' '||b==','){
			
				 countC++;
				 countW++;
			}
			else if(b=='\n'){
			
				countL++;
				countW++;
			}
			else if(b!='\r'){   
	
				countC++;
			}
		
			 pre=b;                      //记住前一字符
		}
		
		br.close();
		return countC;
	//	System.out.println(countC);
	//	System.out.println(--countW);   //--countW文本最后一行先后有空格和回车换行使得单词计算两次，故作减一处理。
	//	System.out.println(countL);
	}
	private static int countWords(File file) throws FileNotFoundException, IOException {

		FileReader fr=new FileReader(file);    
		BufferedReader br=new BufferedReader(fr);
		int b=0; 
		int countC=0;       //两单词间空格和其他可看得见的字母符号均视为一个字符
		int countW=0;      //除空格回车换行外，用逗号隔开的字符组成均为单词
		int countL=0;
		int pre=0;
		while((b =br.read())!=-1){
			//统计字符数
			
			 if(b==' '&&pre=='\n') {       //文本最后一行回车换行并另起一行空格，最后才读到-1，文件末尾        
				 continue;
			 }
			 else if(b==' '&&pre==' '){        //判断文本中出现的连续空格
				 continue;
			 }
			 else if(b==','&&pre==' '){       
				 countW++;
				 continue;
			 }
			 else if(b==' '||b==','){
			
				 countC++;
				 countW++;
			}
			else if(b=='\n'){
			
				countL++;
				countW++;
			}
			else if(b!='\r'){   
				countC++;
			}
		
			 pre=b;                      //记住前一字符
		}
		
		br.close();
		return  --countW;     //--countW文本最后一行先后有空格和回车换行使得单词计算两次，故作减一处理。

	}
	private static int  countLines(File file) throws FileNotFoundException, IOException {

		FileReader fr=new FileReader(file);    

		BufferedReader br=new BufferedReader(fr);
		int b=0; 
		int countC=0;       //两单词间空格和其他可看得见的字母符号均视为一个字符
		int countW=0;      //除空格回车换行外，用逗号隔开的字符组成均为单词
		int countL=0;
		int pre=0;
		while((b =br.read())!=-1){
			//统计字符数
			
			 if(b==' '&&pre=='\n') {       //文本最后一行回车换行并另起一行空格，最后才读到-1，文件末尾        
				 continue;
			 }
			 else if(b==' '&&pre==' '){        //判断文本中出现的连续空格
				 continue;
			 }
			 else if(b==','&&pre==' '){       
				 countW++;
				 continue;
			 }
			 else if(b==' '||b==','){
	
				 countC++;
				 countW++;
			}
			else if(b=='\n'){
				countL++;
				countW++;
			}
			else if(b!='\r'){   
				
				countC++;
			}
		
			 pre=b;                      //记住前一字符
		}
		
		br.close();
		return --countL;
	
	}
	private static void output(File file,File tofile,String[] s) throws IOException {
         if(tofile==null){
        	 tofile=new File("result.txt");
      
         } 
         FileWriter fW=new FileWriter(tofile,true);
         BufferedWriter bw=new BufferedWriter(fW);
        // bw.flush();
         bw.write("--------------");
         bw.write("\n");
         int i=0;String str="";
         String name=file.getName();
         while(i<=s.length-2){
        	 if(s[i].equals("-c")){
        		 str=name+",字符数:"+countCharacters(file);
        	 }
        	 else if(s[i].equals("-w")){
        		 str=name+",单词数:"+countWords(file);
        	 } 
        	 else if(s[i].equals("-l")){
        		 str=name+",行数:"+countLines(file);
        	 }
        	 else if(s[i].equals("-o")){
        		 str=name+",字符数:"+countCharacters(file);
        		 str+="\n";
        		 str+=name+",单词数:"+countWords(file);
        		 str+="\n";
        		 str+=name+",行数:"+countLines(file);
        	 }
        	 i++;
        	 bw.write(str+"\n");
        	 
	 
         }
         
		  bw.close();
		
	//	System.out.println(countC);
	//	System.out.println(--countW);   //--countW文本最后一行先后有空格和回车换行使得单词计算两次，故作减一处理。
		
		
	}
	

	
}
