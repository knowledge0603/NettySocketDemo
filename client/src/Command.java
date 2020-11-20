import java.io.*;

public class Command {
    public static void exeCmd(String commandStr) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
                System.out.println(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        /*
        String commandStr = "frpc -c  frpc.ini";
        //String commandStr = "ipconfig";*/
        // Command.exeCmd("G:\\work\\20200914\\command\\out\\production\\command\\frpc.exe , -c ,frpc.ini");
        //  String cmd =  PathUtil.getCurrentPath() + "frpc.exe -c  frpc.ini";
        //processBuilder();

    }
    public static void writeFile(int port,String host) {
        //String fileName="C:\\kuka.txt";
        //idea调用以下写法
        //String fileName=PathUtil.getCurrentPath()+"frpc"+"_"+port+".ini";
        //c# 调用 java jar 命令 调用以下写法
        //String fileName= "frpc"+"_"+port+".ini";
        String fileName= "frpc.ini";

        try
        {
            System.out.println("writeFile 1001");
            //使用这个构造函数时，如果存在kuka.txt文件，
            //则先把这个文件给删除掉，然后创建新的kuka.txt
            FileWriter writer=new FileWriter(fileName);

            writer.write( "[common]\n");
            writer.write("server_addr = "+host+"\n");
            writer.write("server_port = "+port+"\n");

            writer.write("[web]\n");
            writer.write("type = http\n");
            writer.write("local_port = 9090\n");
            writer.write("local_ip = 127.0.0.1\n");
            writer.write("custom_domains = 127.0.0.1\n");
            writer.close();
            System.out.println("writeFile 1002");
        }
        catch (IOException e)
        {
            System.out.println("writeFile 1003");
            e.printStackTrace();
        }
    }
    public static void processBuilder( int port,String host) throws IOException {
        System.out.println("java client  processBuilder:1001");
        writeFile(port,host);
        //创建ProcessBuilder对象
        /*ProcessBuilder processBuilder =new ProcessBuilder();
        //设置执行的第三方程序(命令),第一个参数是命令,之后的是参数
        //        processBuilder.command("ping","127.0.0.1");
        System.out.println("frp 客户端  服务被开启  port is:"+port);

        System.out.println("java client  processBuilder:1002");
        //idea环境调用以下写法
        //processBuilder.command(PathUtil.getCurrentPath()+"frpc.exe","-c",PathUtil.getCurrentPath()+"frpc"+"_"+port+".ini");


        System.out.println("客户端 frp.ini 路径："+ClassUtils.getCurrentProgramPath()+"/frp"+"_"+port+".ini");
        //vs exe 环境调用 java jar 命令启动以下写法
        processBuilder.command(ClassUtils.getCurrentProgramPath()+"/frpc.exe ", "-c", ClassUtils.getCurrentProgramPath()+"/frpc"+"_"+port+".ini");

        System.out.println("java client  processBuilder:1003");
        //        processBuilder.command("java","-jar","f:/xc-service-manage-course.jar");
        //将标准输入流和错误输入流合并，通过标准输入流读取信息就可以拿到第三方程序输出的错误信息、正常信息
        processBuilder.redirectErrorStream(true);
        System.out.println("java client  processBuilder:1004");

        //启动一个进程
        Process process = processBuilder.start();
        System.out.println("java client  processBuilder:1004001");
        //由于前边将错误和正常信息合并在输入流，只读取输入流
        InputStream inputStream = process.getInputStream();
        System.out.println("java client  processBuilder:1004002");
        //将字节流转成字符流
        InputStreamReader reader = new InputStreamReader(inputStream,"gbk");
        System.out.println("java client  processBuilder:1004003");
        //字符缓冲区
        char[] chars = new char[1024];
        int len = -1;
        System.out.println("java client  processBuilder:1005");
        while((len = reader.read(chars))!=-1){
            String string = new String(chars,0,len);
            System.out.println(string);
            if(string.contains("success")){
                System.out.println("frp 客户端 启动 成功！");
                System.out.println("java client  processBuilder:1006");
                inputStream.close();
                reader.close();
                System.out.println("java client  processBuilder:1007");
                return;
            }
        }*/
    }
}
