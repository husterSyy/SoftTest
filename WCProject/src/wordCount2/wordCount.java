
package wordCount2;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

import javax.sound.sampled.Line;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument.BranchElement;
	/*
	 * *
	wc.exe -s            //递归处理目录下符合条件的文件
    wc.exe -a file.c     //返回更复杂的数据（代码行 / 空行 / 注释行）
    wc.exe -e stopList.txt  // 停用词表，统计文件单词总数时，不统计该表中的单词
    
	wc.exe -c file.c     //返回文件 file.c 的字符数
	wc.exe -w file.c     //返回文件 file.c 的单词总数
	wc.exe -l file.c     //返回文件 file.c 的总行数
	wc.exe -o outputFile.txt     //将结果输出到指定文件outputFile.txt
	*/
//-x 选择文件图形界面
class SelectFile extends JFrame implements ActionListener
{

   File file=null;
   JButton btn = null;

   JTextField textField = null;

   public SelectFile()
   {
       this.setTitle("选择文件窗口");
       FlowLayout layout = new FlowLayout();// 布局
       JLabel label = new JLabel("请选择文件：");// 标签
       textField = new JTextField(30);// 文本域
       btn = new JButton("浏览");// 钮1

       // 设置布局
       layout.setAlignment(FlowLayout.LEFT);// 左对齐
       this.setLayout(layout);
       this.setBounds(400, 200, 600, 70);
       this.setVisible(true);
       this.setResizable(false);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       btn.addActionListener(this);
       this.add(label);
       this.add(textField);
       this.add(btn);

   }


   @Override
   public void actionPerformed(ActionEvent e)
   {
       JFileChooser chooser = new JFileChooser();
       chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
       chooser.showDialog(new JLabel(), "选择");
       
        file = chooser.getSelectedFile();
       textField.setText(file.getAbsoluteFile().toString());
       
   }
}
public class wordCount {
	
	private static ArrayList<String> listname = new ArrayList<String>();
	public static void main(String[] args) throws Exception{
		boolean flag = true;
		File outputFile = null; // 输出文件
		File[] sourceFile = null; // 源读取文件
		File stopFile = null; // 停用词文件
		File matchFile = null;
		String matchFileName = "";
			boolean cflag = false;
			boolean wflag = false;
			boolean lflag = false;
			boolean sflag = false;
			boolean aflag = false;
			boolean eflag = false;
			boolean oflag = false;
			boolean tflag = false;
	        boolean xflag=false;
	    //    Scanner scanner=new Scanner(System.in);
	     //   String str=scanner.nextLine();
	      //  String[] s=str.split(" ");

			for(int i=0;i<args.length;i++){ 
				if(args[i].equals("-x")){
					
					outputG();
				}

				else if (args[i].equals("-c")) {
					cflag=true;
				
				} else if (args[i].equals("-w")) {
					wflag=true;
				
				} else if (args[i].equals("-l")) {
					lflag=true;
				

				}
				//递归处理目录下符合条件的文件
				else if(args[i].equals("-s")){
					sflag=true;
					
			    }
		
		            			
				//返回更复杂的数据（代码行 / 空行 / 注释行）
				else if(args[i].equals("-a")){
					aflag=true;
			
			
				}
				// 停用词表，统计文件单词总数时，不统计该表中的单词
				else if(args[i].equals("-e")){
					eflag=true;
					stopFile=new File(args[++i]);
					continue;
				
				}
				
				
				else if(args[i].equals("-o")){
					oflag=true;
					outputFile=new File(args[++i]);
					
					continue;
					
					
				}
				
				else if(!sflag){
					sourceFile=new File[1];
					sourceFile[0]=new File(args[i]);				
					}
				else {
					String filepath=args[i];
					//ArrayList<String> filePath = readAllFile(
						//	matchFileName, args[i]);
					 ArrayList<String> filePath=new ArrayList<String>();
					File file=new File(System.getProperty("user.dir"));
		            String suff=filepath.substring(filepath.lastIndexOf("*")+1);//取出文件后缀
		            filePath=getAllFilePaths(file,filePath);
		            int count=0;
		            for(i=0;i<filePath.size();i++){//统计每个符合条件的文件的内容
		                if(filePath.get(i).endsWith(suff)){
		                count++;
		 
		          
		                }
				}
		         //   System.out.println(count);
		            sourceFile=new File[count];
		            count=0;
		            for(i=0;i<filePath.size();i++){//统计每个符合条件的文件的内容
		                if(filePath.get(i).endsWith(suff)){
		                	String fName=filePath.get(i).trim();
		                sourceFile[count]=new File(fName.substring(fName.lastIndexOf("\\")+1));
		             //   System.out.println(sourceFile[count].getName());
		                count++;
		                
		                }
				}
		           
					
				}
			
			}
			
			    //输出实现
			   if(outputFile==null){
		        	 outputFile=new File("result.txt");
		      
		         } 
		        FileWriter fW=new FileWriter(outputFile,true);
			  // FileWriter fW=new FileWriter(outputFile);
		         BufferedWriter bw=new BufferedWriter(fW);
		         bw.write("------------");
		         bw.write("\n");
		         int j=0;String str1="";
		         String name="";
		         while(j<sourceFile.length){
		        	 name=sourceFile[j].getName();
		        	// System.out.println(name);
		        	if(eflag){
		        		 str1=name+", 单词数: "+countWordsS(sourceFile[j], stopFile);
		        		 bw.write(str1+"\r\n");
		        	}
		        	 if(cflag){
		        		 str1=name+", 字符数: "+countCharacters(sourceFile[j]);
		        		 bw.write(str1+"\r\n");
		        	 }
		        	  if(!eflag&&wflag){
		        		 str1=name+", 单词数: "+countWords(sourceFile[j]);
		        		 bw.write(str1+"\r\n");
		        	 }  
		        	  if(lflag){
		        		 str1=name+", 行数: "+countLines(sourceFile[j]);
		        		 bw.write(str1+"\r\n");
		        	 }
		        	 //注释行代码行空行
		        	  if(aflag){
		        		 str1=name+", 代码行/空行/注释行: ";
		        		int[] lines=countDLines(sourceFile[j]);
		        		str1+=""+lines[0]+"/"+lines[1]+"/"+lines[2];
		        		 bw.write(str1+"\r\n");
		        	 }
 
		       
		        	 j++;
			 
		         }
		         
				  bw.close();
	    	
	
			
	}
			
	
	 //图形界面选择文件
	private static void outputG() throws IOException {
		SelectFile sf=new SelectFile();
		
		String str1="";
		String name=sf.file.getName();
		File outputFile=new File("result.txt");
		 FileWriter fW=new FileWriter(outputFile);
         BufferedWriter bw=new BufferedWriter(fW);
   		 str1=name+", 字符数: "+countCharacters(sf.file);
   		 bw.write(str1+"\r\n");
   	   		 str1=name+", 单词数: "+countWords(sf.file);
   		 bw.write(str1+"\r\n");
   	
   		 str1=name+", 行数: "+countLines(sf.file);
   		 bw.write(str1+"\r\n");
   	
   		 str1=name+", 代码行/空行/注释行: ";
   		int[] lines=countDLines(sf.file);
   		str1+=""+lines[0]+"/"+lines[1]+"/"+lines[2];
   		 bw.write(str1+"\r\n");
	     bw.close();
		
	}
	
	public static ArrayList<String> getAllFilePaths(File filepath,ArrayList<String> filePaths){
        File[] array=filepath.listFiles();
        if(array==null)
            return filePaths;
        for(File f:array){
            if(f.isDirectory()){//递归读取子目录下内容
                filePaths.add(f.getPath());
                getAllFilePaths(f,filePaths);
            }else{
                filePaths.add(f.getPath());
            }
        }
        return filePaths;
    }

  
	public static ArrayList<String>  readAllFile(String filepath,String pattern) {
		
		//File file= new File(filepath);
		File file= new File(System.getProperty(filepath));
		if(!file.isDirectory()){
			if(!listname.contains(file.getName())){
				listname.add(file.getName());
			}
			
		}else if(file.isDirectory()){
			//System.out.println("文件");
			String[] filelist=file.list();
			for(int i = 0;i<filelist.length;i++){
				File readfile = new File(filelist[i]);
				if (!readfile.isDirectory()) {
					if(!listname.contains(file.getName())){
						listname.add(file.getName());
					}
                  
				} else if (readfile.isDirectory()) {
					readAllFile(filepath + "\\" + filelist[i],pattern);//递归
				}
			}
		}
		for(int i=0;i<listname.size();i++){
			if(!listname.get(i).endsWith(pattern.substring(pattern.length()-2,pattern.length()))){
				listname.remove(i);
			}
		
		}
		File[] files=file.listFiles(new FilenameFilter(){

			   @Override
			    //重写accept方法,测试指定文件是否应该包含在某一文件列表中
			    public boolean accept(File dir, String name) {
			        // 创建返回值
			        boolean flag = true;
			        // 定义筛选条件
			        //endWith(String str);判断是否是以指定格式结尾的
			        if (name.toLowerCase().endsWith(".c")) {
			 
			        } else if (name.toLowerCase().endsWith(".txt")) {
			 
			        } else if (name.toLowerCase().endsWith(".docx")) {
			 
			        } else {
			            flag = false;
			        }
			         
			        //当返回true时,表示传入的文件满足条件
			        return flag;
			    }});
//		for(File f :files){
//			if(f.isDirectory()){
//				String fp=f.getAbsolutePath();
//				fp.replace("\\", "\\\\");
//				readAllFile(fp);
//			}else{
//				System.out.println(f.getName());
//			}
//		}
		
		
		return listname;
	}

  
    // 停用词表，统计文件单词总数时，不统计该表中的单词
    private static int countWordsS(File file1,File file2) throws Exception {    

		BufferedReader br1 = new BufferedReader(new FileReader(file1));
		BufferedReader br2 = new BufferedReader(new FileReader(file2));
		ArrayList<String> lists1 = new ArrayList<String>(); // 存储过滤后单词的列表
		ArrayList<String> lists2 = new ArrayList<String>(); // 存储过滤后单词的列表
		String readLine = null;
		while ((readLine = br1.readLine()) != null) {
			String[] wordsArr1 = readLine.split("[^a-zA-Z]"); // 过滤出只含有字母的
			for (String word : wordsArr1) {
				if (word.length() != 0) { // 去除长度为0的行
					lists1.add(word);
					// count++;
				}
			}
		}

		while ((readLine = br2.readLine()) != null) {
			String[] wordsArr2 = readLine.split("[^a-zA-Z]"); // 过滤出只含有字母的
			for (String word : wordsArr2) {
				if (word.length() != 0) { // 去除长度为0的行
					lists2.add(word);
					// count++;
				}
			}
		}
		Map<String, Integer> wordsCount = new TreeMap<String, Integer>(); // 存储单词计数信息，key值为单词，value为单词数

		// 单词的词频统计
		for (String li : lists1) {
			if (wordsCount.get(li) != null) {
				wordsCount.put(li, wordsCount.get(li) + 1);
			} else {
				wordsCount.put(li, 1);
			}

		}
		int count = 0;
		for (int i = 0; i < lists2.size(); i++) {
			if (wordsCount.containsKey(lists2.get(i))) {
				wordsCount.remove(lists2.get(i));
			}
		}
		for (Integer value : wordsCount.values()) {
			count += value;
		}

		 
		br1.close();
		br2.close();
		return count;
	}
	//返回更复杂的数据（代码行 / 空行 / 注释行）
	private static int[] countDLines(File file) throws IOException {
		boolean flag=false;
		int count=0;
		int[] lines=new int[3];
		FileReader fr=new FileReader(file);
		BufferedReader br=new BufferedReader(fr); 
	
		String str=""; 
		
		while((str=br.readLine())!=null){
			if(str.equals("")){   
					lines[1]++;     //空行
				//	System.out.println(str+"---");
					continue;
				}
			 //包括代码，则只有不超过一个可显示的字符，例如“{”
			if(!flag&&(str.split(" ").length==1)&&(str.split(" ")[0].equals("{")||str.split(" ")[0].equals("}"))){
			//	System.out.println(str+"+++");
				lines[1]++;          //空行
				continue;
			}
			 if(!flag&&str.split(" ")[0].startsWith("//")){
				  lines[2]++;     //注释行
				  continue;
			   }
			   if(!flag&&str.split(" ")[0].startsWith("}//")){
				   lines[2]++;        //注释行
				   continue; 
			   }
			   if(str.split(" ")[0].startsWith("/*")&&str.split(" ")[str.split(" ").length-1].endsWith("*/")){
				   flag=true;
				 lines[2]++;
				 
				  continue;
				   
			   }
			   if(str.split(" ")[0].startsWith("/*")){
				   flag=true;
				   count=1;
				   continue;
			   }
			   if(str.split(" ")[str.split(" ").length-1].endsWith("*/")&&flag){
				   if(count>1){
				           count++;
				    
			       }
				   lines[2]+=count;   //注释行
				   flag=false;
				   continue;
			   }
	            if(flag){
	            	count++;
	            	continue;
	            }
	            if(!flag){
	            	lines[0]++;       //代码行
	            	continue;
	            }
		    
		}
		
		return lines;
		
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
		return  countW;     

	}
	
	private static int  countLines(File file) throws FileNotFoundException, IOException {

		FileReader fr=new FileReader(file);    
         FileInputStream br=new FileInputStream(file);
		//BufferedReader br=new BufferedReader(fr);
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
		return ++countL;
	
	}


	
}