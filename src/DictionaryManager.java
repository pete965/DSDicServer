import java.io.*;
import java.util.HashMap;

public class DictionaryManager {
    static private HashMap<String,String> dic = new HashMap<String,String>();
    private File filename;
    private String dicPath;

    public DictionaryManager(String dicPath){
        this.filename = new File(dicPath);
        this.dicPath = dicPath;
        initiateDic(dicPath);
    }

    public void initiateDic(String dicPath){
        if(!filename.exists()){
            try {
                this.filename.createNewFile();
                System.out.println("File not exists, create a new one");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("File already there, begin to load");
            InputStreamReader reader = null; // 建立一个输入流对象reader
            try {
                reader = new InputStreamReader(new FileInputStream(filename));
                BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
                String slot = "";
                slot = br.readLine();
                while (slot != null) {
                    String[] keyValue = slot.split(" ");
                    dic.put(keyValue[0],keyValue[1]);
                    slot = br.readLine(); // 一次读入一行数据
                }
                for (String keys : dic.keySet()) {
                    System.out.println(keys+":"+dic.get(keys));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // TODO: 2019/9/3 exception
            } catch (IOException e){
                e.printStackTrace();
                // TODO: 2019/9/3 exception
            }
        }
    }
    public String add(String key,String value){
        String output;
        if(dic.get(key) == null){
            synchronized (this){
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(this.filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                try {
                    dic.put(key,value);
                    for (String keys : dic.keySet()) {
                        bufferedWriter.write(keys+" "+dic.get(keys)+"\n");
                    }
                    output = "Success Add";
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    output = "Failed FileNotFound";
                } catch (IOException e){
                    e.printStackTrace();
                    output = "Failed IOException";
                }finally {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            output = "Failed KeyAlreadyExists";
        }
        return output+"\n";
    }
    public String query(String key){
        String output=null;
        if(dic.get(key) == null){
            output = "Failed KeyNotExists";
        }else{
            output =dic.get(key);
        }
        return output+"\n";
    }
    public String remove(String key){
        String output;
        if(dic.get(key) == null){
            output = "Failed KeyNotExists";
        }else{
            synchronized (this){
                dic.remove(key);
                filename.delete();
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.filename));
                    for (String keys : dic.keySet()) {
                        bufferedWriter.write(keys+" "+dic.get(keys)+"\n");
                    }
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    output = "Success Remove";
                } catch (IOException e) {
                    e.printStackTrace();
                    output = "Failed IOException";
                }
            }
        }
        return output+"\n";
    }
}
