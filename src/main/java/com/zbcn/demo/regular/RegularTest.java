package com.zbcn.demo.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式测试
 *
 * @author likun
 * @date 2018/10/10 16:01
 */
public class RegularTest {

    private static void testMataCharacter(String reg,String str){

        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        System.out.println("匹配开始：----");
        while (matcher.find()){
            System.out.println("匹配结果：");
            System.out.println(matcher.group());
        }

        System.out.println("匹配结束");

    }

    public static void main(String[] args){
        //testReg();
        //testCapture();
        //testCaptureByName();
        //testUnCapture();
        //testBackReference();
        //replace();
        //GreedyMatch();
        lazyMatch();
    }


    /**
     * 懒惰匹配：当正则表达式中包含能接受重复的限定符时，
     * 通常的行为是（在使整个表达式能得到匹配的前提下）匹配尽可能少的字符，这匹配方式叫做懒惰匹配。
     * 特性：从左到右，从字符串的最左边开始匹配，每次试图不读入字符匹配，匹配成功，则完成匹配，
     * 否则读入一个字符再匹配，依此循环（读入字符、匹配）直到匹配成功或者把字符串的字符匹配完为止。
     */
    private static void lazyMatch(){
        String reg = "(\\d{1,2}?)(\\d{3,4})";
        String test="61762828 176 2991 871 89999";
        Pattern p1 =Pattern.compile(reg);
        Matcher m1 = p1.matcher(test);
        while(m1.find()){
            System.out.println("匹配结果："+m1.group(0));
        }
    }
    /**
     * 贪婪和非贪婪
     * 贪婪匹配：当正则表达式中包含能接受重复的限定符时，通常的行为是（在使整个表达式能得到匹配的前提下）匹配尽可能多的字符，
     * 这匹配方式叫做贪婪匹配。
     * 特性：一次性读入整个字符串进行匹配，每当不匹配就舍弃最右边一个字符，
     * 继续匹配，依次匹配和舍弃（这种匹配-舍弃的方式也叫做回溯），直到匹配成功或者把整个字符串舍弃完为止，
     * 因此它是一种最大化的数据返回，能多不会少。
     */
    private static void GreedyMatch(){
        String reg="\\d{3,6}";
        reg = "(\\d{1,2})(\\d{3,4})";
        String test="61762828 176 2991 871 89999";
        Pattern p1 =Pattern.compile(reg);
        Matcher m1 = p1.matcher(test);
        while(m1.find()){
            System.out.println("匹配结果："+m1.group(0));
        }
    }

    /**
     * 反向引用：捕获会返回一个捕获组，这个分组是保存在内存中，不仅可以在正则表达式外部通过程序进行引用，
     * 也可以在正则表达式内部进行引用，这种引用方式就是反向引用。
     *
     * 数字编号组反向引用：\k
     * 或\number
     * 命名编号组反向引用：\k
     * 或者\'name'
     */
    private static void testBackReference(){
        String test = "aabbbbgbddesddfiid";
        String reg = "(\\w)\\1";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(test);
        while(matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    //替换的例子，假如想要把字符串中abc换成a
    private static void replace(){
        String test = "abcbbabcbcgbddesddfiid";
        String reg="(a)(b)c";
        System.out.println(test.replaceAll(reg, "$1"));
    }

    //非捕获组：
    //语法：(?:exp)
    //解释：和捕获组刚好相反，它用来标识那些不需要捕获的分组，说的通俗一点，就是你可以根据需要去保存你的分组。
    private static void testUnCapture(){
        String test ="020-85653333";
        String reg = "^(?:0\\d{2})-(\\d{8})";
        capture(reg,test);
    }

    //数字编号捕获组：
    //语法：(exp)
    //解释：从表达式左侧开始，每出现一个左括号和它对应的右括号之间的内容为一个分组，在分组中，第0组为整个表达式，第一组开始为分组。
    //比如固定电话的：020-85653333
    //他的正则表达式为：(0\d{2})-(\d{8})
    //按照左括号的顺序，这个表达式有如下分组：
    private static void testCapture(){
        String test ="020-85653333";
        String reg = "(0\\d{2})-(\\d{8})";
        capture(reg,test);
    }

    //命名编号捕获组：
    //语法：(?<name>exp)
    //解释：分组的命名由表达式中的name指定
    //比如区号也可以这样写:(?<quhao>\0\d{2})-(?<haoma>\d{8})
    //按照左括号的顺序，这个表达式有如下分组：
    private static void testCaptureByName(){
        String test = "020-85653333";
        String reg="(?<quhao>0\\d{2})-(?<haoma>\\d{8})";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(test);
        if(matcher.find()){
            System.out.println("区号分组分组为："+matcher.group("quhao"));
            System.out.println("号码分组分组为："+matcher.group("haoma"));
        }
    }

    /**
     * 分组查询
     * @param reg
     * @param test
     */
    private static void capture(String reg,String test){
        //生成pattern
        Pattern pattern = Pattern.compile(reg);
        //正则匹配
        Matcher matcher = pattern.matcher(test);
        if(matcher.find()){
            System.out.println("分组的个数有："+matcher.groupCount());
            for(int i = 0; i<= matcher.groupCount(); i++){
                System.out.println("第"+i+"个分组为："+matcher.group(i));
            }
        }
    }

    private static void testReg() {
        String test = "abcdqwr";
        //以abc开头
        String reg = "^abc.*";
        //8位数字
        reg = "^\\d{8}$";
        test = "12345678";
        //以1开头的电话号码
        reg = "^1\\d{10}$";
        test = "18500368339";
        //14-15位的数字（银行卡）
        reg = "^\\d{14,18}$";
        test = "185003683391234567";
        //已a开头，以0个或者多个b结尾
        reg = "^a\\w*b*$";
        test = "abddbfffb";
        //以ab开头
        reg = "^(ab)\\w*";
        //条件或：比如联通有130/131/132/155/156/185/186/145/176
        test = "18500368339";
        reg = "^1((3[0-2])|(5[5-6])|(8[5-6])|(45)|(76))\\d{8}";
        //零宽断言：
        test = "<span class=\"read-count\">阅读数：641</span>";
        //正向先行断言（正前瞻）：
        //语法：（?=pattern）
        //作用：匹配pattern表达式的前面内容，不返回本身。
        reg = "\\d+(?=</span>)";
        //正向后行断言（正后顾）:
        //语法：（?<=pattern）
        //作用：匹配pattern表达式的后面的内容，不返回本身。
        reg = "(?<=<span class=\"read-count\">阅读数：)\\d+";
        //负向先行断言（负前瞻）
        //语法：(?!pattern)
        //作用：匹配非pattern表达式的前面内容，不返回本身。
        test = " “我爱祖国，我是祖国的花朵”";
        reg = "祖国(?!的花朵)";

        //负向后行断言（负后顾）
        //语法：(?<!pattern)
        //作用：匹配非pattern表达式的后面内容，不返回本身。
        reg = "(?<!祖国)朵";
        testMataCharacter(reg,test);
    }
}
