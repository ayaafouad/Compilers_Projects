package project_scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

public class Project_Scanner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] Reserved_Words = {"if", "then", "else","end","repeat","until","read","write"};
        
        String[] Special_Symbols = {"+","-","*","/","=","<","(",")",";",":="};  
       ///////////////////////*Read from file *///////////////////////////
        
       try {
            // reading the code from the file 
            FileReader fout = new FileReader("tiny_language.txt") ;
            int c;
            String sentence = " ";
            while ( -1 != (c=fout.read()))
            {
                //System.out.print((char)c);
                char character = (char)c;
                 
                // removing the comments
                if(character == '{')
                { 
                    while ( character != '}')
                    {
                    c=fout.read();
                    character = (char)c;
                    }
                    c=fout.read();
                    character = (char)c;
                 }
                sentence += character ; 
            }
           
           // System.out.print(sentence);
       
        //String[] Tokens = sentence.split("\\s+|;|\\r\\n");
       String[] Tokens = sentence.split("\n");
       String[][] Row_Tokens =new String[Tokens.length][100];
      for (int i=0; i<Tokens.length; i++)
       {
          Row_Tokens[i] = Tokens[i].split("\\s+|;");   
       }
       LinkedHashMap<String,String> Token_Type=new LinkedHashMap<String,String>();
       FileWriter f = new FileWriter("Token_Type.txt") ;
       
       for (int x=0; x<Tokens.length; x++) 
       {
        for (int a=0; a<Row_Tokens[x].length; a++) 
        {
           char buffer;
           String Buf = Row_Tokens[x][a];
                buffer = Buf.charAt(0);
             
           if( buffer > 47 && buffer <58)
           {
               Token_Type.put(Row_Tokens[x][a],"Number");
               f.write(Row_Tokens[x][a]+"-->"+"Number"+ System.getProperty( "line.separator" ));
              
           }
           
           else if( ((int)buffer > 64 &&(int)buffer <91)|| ((int)buffer > 96 && (int)buffer <123) )
           {
               boolean found= false;
               for (String word :Reserved_Words)
               { 
                   if (Row_Tokens[x][a].equalsIgnoreCase(word))
                   {
                     found=true;  
                    Token_Type.put(Row_Tokens[x][a],"Reserved_Words"); 
                    f.write(Row_Tokens[x][a]+"-->"+"Reserved_Words"+ System.getProperty( "line.separator" ));
                   }   
               }
               if(!found)
               {
                   Token_Type.put(Row_Tokens[x][a],"Identifier");
                   f.write(Row_Tokens[x][a]+"-->"+"Identifier"+ System.getProperty( "line.separator" ));

               }
           }
           else
           {
               boolean found= false;
               for (String symbol :Special_Symbols)
               {
                   if (Row_Tokens[x][a].equalsIgnoreCase(symbol))
                   {
                     found=true;  
                    Token_Type.put(Row_Tokens[x][a],"Special_Symbols");
                    f.write(Row_Tokens[x][a]+"-->"+"Special_Symbols"+ System.getProperty( "line.separator" ));
                   }   
               }
               if(!found)
               {
                   Token_Type.put(Row_Tokens[x][a],"Not defined");
                   f.write(Row_Tokens[x][a]+"-->"+"Not defined"+ System.getProperty( "line.separator" ));
                   
               }    
           }
        }
       }
       f.close();      
       
       } catch (FileNotFoundException ex) {
            Logger.getLogger(Project_Scanner.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
            Logger.getLogger(Project_Scanner.class.getName()).log(Level.SEVERE, null, ex);
       }
      
    }
    
}
