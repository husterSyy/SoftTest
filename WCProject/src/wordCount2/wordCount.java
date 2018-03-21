
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
	wc.exe -s            //�ݹ鴦��Ŀ¼�·����������ļ�
    wc.exe -a file.c     //���ظ����ӵ����ݣ������� / ���� / ע���У�
    wc.exe -e stopList.txt  // ͣ�ôʱ�ͳ���ļ���������ʱ����ͳ�Ƹñ��еĵ���
    
	wc.exe -c file.c     //�����ļ� file.c ���ַ���
	wc.exe -w file.c     //�����ļ� file.c �ĵ�������
	wc.exe -l file.c     //�����ļ� file.c ��������
	wc.exe -o outputFile.txt     //����������ָ���ļ�outputFile.txt
	*/
//-x ѡ���ļ�ͼ�ν���
class SelectFile extends JFrame implements ActionListener
{

   File file=null;
   JButton btn = null;

   JTextField textField = null;

   public SelectFile()
   {
       this.setTitle("ѡ���ļ�����");
       FlowLayout layout = new FlowLayout();// ����
       JLabel label = new JLabel("��ѡ���ļ���");// ��ǩ
       textField = new JTextField(30);// �ı���
       btn = new JButton("���");// ť1

       // ���ò���
       layout.setAlignment(FlowLayout.LEFT);// �����
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
       chooser.showDialog(new JLabel(), "ѡ��");
       
        file = chooser.getSelectedFile();
       textField.setText(file.getAbsoluteFile().toString());
       
   }
}
public class wordCount {
	
	private static ArrayList<String> listname = new ArrayList<String>();
	public static void main(String[] args) throws Exception{
		boolean flag = true;
		File outputFile = null; // ����ļ�
		File[] sourceFile = null; // Դ��ȡ�ļ�
		File stopFile = null; // ͣ�ô��ļ�
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
				//�ݹ鴦��Ŀ¼�·����������ļ�
				else if(args[i].equals("-s")){
					sflag=true;
					
			    }
		
		            			
				//���ظ����ӵ����ݣ������� / ���� / ע���У�
				else if(args[i].equals("-a")){
					aflag=true;
			
			
				}
				// ͣ�ôʱ�ͳ���ļ���������ʱ����ͳ�Ƹñ��еĵ���
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
		            String suff=filepath.substring(filepath.lastIndexOf("*")+1);//ȡ���ļ���׺
		            filePath=getAllFilePaths(file,filePath);
		            int count=0;
		            for(i=0;i<filePath.size();i++){//ͳ��ÿ�������������ļ�������
		                if(filePath.get(i).endsWith(suff)){
		                count++;
		 
		          
		                }
				}
		         //   System.out.println(count);
		            sourceFile=new File[count];
		            count=0;
		            for(i=0;i<filePath.size();i++){//ͳ��ÿ�������������ļ�������
		                if(filePath.get(i).endsWith(suff)){
		                	String fName=filePath.get(i).trim();
		                sourceFile[count]=new File(fName.substring(fName.lastIndexOf("\\")+1));
		             //   System.out.println(sourceFile[count].getName());
		                count++;
		                
		                }
				}
		           
					
				}
			
			}
			
			    //���ʵ��
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
		        		 str1=name+", ������: "+countWordsS(sourceFile[j], stopFile);
		        		 bw.write(str1+"\r\n");
		        	}
		        	 if(cflag){
		        		 str1=name+", �ַ���: "+countCharacters(sourceFile[j]);
		        		 bw.write(str1+"\r\n");
		        	 }
		        	  if(!eflag&&wflag){
		        		 str1=name+", ������: "+countWords(sourceFile[j]);
		        		 bw.write(str1+"\r\n");
		        	 }  
		        	  if(lflag){
		        		 str1=name+", ����: "+countLines(sourceFile[j]);
		        		 bw.write(str1+"\r\n");
		        	 }
		        	 //ע���д����п���
		        	  if(aflag){
		        		 str1=name+", ������/����/ע����: ";
		        		int[] lines=countDLines(sourceFile[j]);
		        		str1+=""+lines[0]+"/"+lines[1]+"/"+lines[2];
		        		 bw.write(str1+"\r\n");
		        	 }
 
		       
		        	 j++;
			 
		         }
		         
				  bw.close();
	    	
	
			
	}
			
	
	 //ͼ�ν���ѡ���ļ�
	private static void outputG() throws IOException {
		SelectFile sf=new SelectFile();
		
		String str1="";
		String name=sf.file.getName();
		File outputFile=new File("result.txt");
		 FileWriter fW=new FileWriter(outputFile);
         BufferedWriter bw=new BufferedWriter(fW);
   		 str1=name+", �ַ���: "+countCharacters(sf.file);
   		 bw.write(str1+"\r\n");
   	   		 str1=name+", ������: "+countWords(sf.file);
   		 bw.write(str1+"\r\n");
   	
   		 str1=name+", ����: "+countLines(sf.file);
   		 bw.write(str1+"\r\n");
   	
   		 str1=name+", ������/����/ע����: ";
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
            if(f.isDirectory()){//�ݹ��ȡ��Ŀ¼������
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
			//System.out.println("�ļ�");
			String[] filelist=file.list();
			for(int i = 0;i<filelist.length;i++){
				File readfile = new File(filelist[i]);
				if (!readfile.isDirectory()) {
					if(!listname.contains(file.getName())){
						listname.add(file.getName());
					}
                  
				} else if (readfile.isDirectory()) {
					readAllFile(filepath + "\\" + filelist[i],pattern);//�ݹ�
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
			    //��дaccept����,����ָ���ļ��Ƿ�Ӧ�ð�����ĳһ�ļ��б���
			    public boolean accept(File dir, String name) {
			        // ��������ֵ
			        boolean flag = true;
			        // ����ɸѡ����
			        //endWith(String str);�ж��Ƿ�����ָ����ʽ��β��
			        if (name.toLowerCase().endsWith(".c")) {
			 
			        } else if (name.toLowerCase().endsWith(".txt")) {
			 
			        } else if (name.toLowerCase().endsWith(".docx")) {
			 
			        } else {
			            flag = false;
			        }
			         
			        //������trueʱ,��ʾ������ļ���������
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

  
    // ͣ�ôʱ�ͳ���ļ���������ʱ����ͳ�Ƹñ��еĵ���
    private static int countWordsS(File file1,File file2) throws Exception {    

		BufferedReader br1 = new BufferedReader(new FileReader(file1));
		BufferedReader br2 = new BufferedReader(new FileReader(file2));
		ArrayList<String> lists1 = new ArrayList<String>(); // �洢���˺󵥴ʵ��б�
		ArrayList<String> lists2 = new ArrayList<String>(); // �洢���˺󵥴ʵ��б�
		String readLine = null;
		while ((readLine = br1.readLine()) != null) {
			String[] wordsArr1 = readLine.split("[^a-zA-Z]"); // ���˳�ֻ������ĸ��
			for (String word : wordsArr1) {
				if (word.length() != 0) { // ȥ������Ϊ0����
					lists1.add(word);
					// count++;
				}
			}
		}

		while ((readLine = br2.readLine()) != null) {
			String[] wordsArr2 = readLine.split("[^a-zA-Z]"); // ���˳�ֻ������ĸ��
			for (String word : wordsArr2) {
				if (word.length() != 0) { // ȥ������Ϊ0����
					lists2.add(word);
					// count++;
				}
			}
		}
		Map<String, Integer> wordsCount = new TreeMap<String, Integer>(); // �洢���ʼ�����Ϣ��keyֵΪ���ʣ�valueΪ������

		// ���ʵĴ�Ƶͳ��
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
	//���ظ����ӵ����ݣ������� / ���� / ע���У�
	private static int[] countDLines(File file) throws IOException {
		boolean flag=false;
		int count=0;
		int[] lines=new int[3];
		FileReader fr=new FileReader(file);
		BufferedReader br=new BufferedReader(fr); 
	
		String str=""; 
		
		while((str=br.readLine())!=null){
			if(str.equals("")){   
					lines[1]++;     //����
				//	System.out.println(str+"---");
					continue;
				}
			 //�������룬��ֻ�в�����һ������ʾ���ַ������硰{��
			if(!flag&&(str.split(" ").length==1)&&(str.split(" ")[0].equals("{")||str.split(" ")[0].equals("}"))){
			//	System.out.println(str+"+++");
				lines[1]++;          //����
				continue;
			}
			 if(!flag&&str.split(" ")[0].startsWith("//")){
				  lines[2]++;     //ע����
				  continue;
			   }
			   if(!flag&&str.split(" ")[0].startsWith("}//")){
				   lines[2]++;        //ע����
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
				   lines[2]+=count;   //ע����
				   flag=false;
				   continue;
			   }
	            if(flag){
	            	count++;
	            	continue;
	            }
	            if(!flag){
	            	lines[0]++;       //������
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
		return  countW;     

	}
	
	private static int  countLines(File file) throws FileNotFoundException, IOException {

		FileReader fr=new FileReader(file);    
         FileInputStream br=new FileInputStream(file);
		//BufferedReader br=new BufferedReader(fr);
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
		return ++countL;
	
	}


	
}