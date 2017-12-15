package cn.iflyapi;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: qfwang
 * Date: 2017-12-15 下午1:44
 */
public class Test {
    public static void main(String args[]){
        Pattern pattern =Pattern.compile("<img src=\"(.+?)\"");
        Matcher matcher =pattern.matcher("dsadsadas<a src=\"qfwang.png\"/>dsadasdas<lionel>\"www.163.com\"<kenny><img src=\"wang.png\"/>");
        if(matcher.find()){
            System.out.println(matcher.group(1));
        }

        Pattern pattern1 =Pattern.compile("<img\"(.+?)/\"");
        Matcher matcher1 =pattern.matcher("<a href=\"index.html\">主页</a><img src='' />");
        if(matcher1.find()){
            System.out.println(matcher1.group(1));
        }


        String s = "dsadsadas<img src=''/>dsadasdas<lionel>\"www.163.com\"<kenny><img src='wang.png'/><>";
        Pattern p = Pattern.compile("(<img[^>]*>)");
        Matcher m = p.matcher(s);
        List<String> result=new ArrayList<String>();
        while(m.find()){
            result.add(m.group());
        }
        for(String s1:result){
            System.out.println(s1);
        }
    }
}
