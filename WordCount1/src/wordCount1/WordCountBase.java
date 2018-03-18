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
	 * �ո����ַ�
	 * �ɿո�򶺺ŷָ�Ķ���Ϊ���ʣ��Ҳ������ʵ���Ч��У�飬
	 * ���磺thi#,that��Ϊ�ö��Ÿ�����2�����ʡ�
	wc.exe -c file.c     //�����ļ� file.c ���ַ���
	wc.exe -w file.c     //�����ļ� file.c �ĵ�������
	wc.exe -l file.c     //�����ļ� file.c ��������
	wc.exe -o outputFile.txt     //����������ָ���ļ�outputFile.txt
	*/

public class WordCountBase {
	
	public static void main(String[] args) throws IOException{
		while(true){
			System.out.println("wc.exe -c file.c     //�����ļ� file.c ���ַ���");
			System.out.println("wc.exe -w file.c     //�����ļ� file.c �ĵ�������");
			System.out.println("wc.exe -l file.c     //�����ļ� file.c ��������");
			System.out.println("wc.exe -o outputFile.txt     //����������ָ���ļ�outputFile.txt");
		 File file=null;
		 File tofile=null;
       Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        String str2=sc.nextLine();
        
        String[] s=str.split(" ");
        String[] s2=str2.split(" ");
			if (s.length < 3) {               //-c -w -l ����ʹ��
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
			else if(s.length>=3){           //-c -w -l ���ʹ��
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
		int countC=0;       //�����ʼ�ո�������ɿ��ü�����ĸ���ž���Ϊһ���ַ�
		int countW=0;      //���ո�س������⣬�ö��Ÿ������ַ���ɾ�Ϊ����
		int countL=0;
		int pre=0;
		while((b =br.read())!=-1){
			//ͳ���ַ���
			
			 if(b==' '&&pre=='\n') {       //�ı����һ�лس����в�����һ�пո����Ŷ���-1���ļ�ĩβ        
				 continue;
			 }
			 else if(b==' '&&pre==' '){        //�ж��ı��г��ֵ������ո�
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
		
			 pre=b;                      //��סǰһ�ַ�
		}
		
		br.close();
		return countC;
	//	System.out.println(countC);
	//	System.out.println(--countW);   //--countW�ı����һ���Ⱥ��пո�ͻس�����ʹ�õ��ʼ������Σ�������һ����
	//	System.out.println(countL);
	}
	private static int countWords(File file) throws FileNotFoundException, IOException {

		FileReader fr=new FileReader(file);    
		BufferedReader br=new BufferedReader(fr);
		int b=0; 
		int countC=0;       //�����ʼ�ո�������ɿ��ü�����ĸ���ž���Ϊһ���ַ�
		int countW=0;      //���ո�س������⣬�ö��Ÿ������ַ���ɾ�Ϊ����
		int countL=0;
		int pre=0;
		while((b =br.read())!=-1){
			//ͳ���ַ���
			
			 if(b==' '&&pre=='\n') {       //�ı����һ�лس����в�����һ�пո����Ŷ���-1���ļ�ĩβ        
				 continue;
			 }
			 else if(b==' '&&pre==' '){        //�ж��ı��г��ֵ������ո�
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
		
			 pre=b;                      //��סǰһ�ַ�
		}
		
		br.close();
		return  --countW;     //--countW�ı����һ���Ⱥ��пո�ͻس�����ʹ�õ��ʼ������Σ�������һ����

	}
	private static int  countLines(File file) throws FileNotFoundException, IOException {

		FileReader fr=new FileReader(file);    

		BufferedReader br=new BufferedReader(fr);
		int b=0; 
		int countC=0;       //�����ʼ�ո�������ɿ��ü�����ĸ���ž���Ϊһ���ַ�
		int countW=0;      //���ո�س������⣬�ö��Ÿ������ַ���ɾ�Ϊ����
		int countL=0;
		int pre=0;
		while((b =br.read())!=-1){
			//ͳ���ַ���
			
			 if(b==' '&&pre=='\n') {       //�ı����һ�лس����в�����һ�пո����Ŷ���-1���ļ�ĩβ        
				 continue;
			 }
			 else if(b==' '&&pre==' '){        //�ж��ı��г��ֵ������ո�
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
		
			 pre=b;                      //��סǰһ�ַ�
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
        		 str=name+",�ַ���:"+countCharacters(file);
        	 }
        	 else if(s[i].equals("-w")){
        		 str=name+",������:"+countWords(file);
        	 } 
        	 else if(s[i].equals("-l")){
        		 str=name+",����:"+countLines(file);
        	 }
        	 else if(s[i].equals("-o")){
        		 str=name+",�ַ���:"+countCharacters(file);
        		 str+="\n";
        		 str+=name+",������:"+countWords(file);
        		 str+="\n";
        		 str+=name+",����:"+countLines(file);
        	 }
        	 i++;
        	 bw.write(str+"\n");
        	 
	 
         }
         
		  bw.close();
		
	//	System.out.println(countC);
	//	System.out.println(--countW);   //--countW�ı����һ���Ⱥ��пո�ͻس�����ʹ�õ��ʼ������Σ�������һ����
		
		
	}
	

	
}
