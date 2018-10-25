package com.zbcn.demo.util.message;

import com.google.common.collect.Lists;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zbcn.demo.util.RegexUtils;
import com.zbcn.demo.util.file.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * User: zhangqh6
 * Date: 2016/1/21 13:28:28
 */
public class XmlUtils {
    private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    public static <T> T readValue(String content, Class<T> valueType) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(valueType);
        xstream.aliasSystemAttribute("BEAN_CLASS", "class");
        return (T) xstream.fromXML(content);
    }

    /**
     * 读取文件-类加载器中文件
     * @param filePath
     * @param valueType
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T readValueFromFile(String filePath, Class<T> valueType) throws IOException {
        URL fileResource = XmlUtils.class.getResource("/" + filePath);
        if(fileResource != null) {
            return readValueFromAbsoluteFilePath(fileResource.getPath(), valueType);
        }else {
            return readValueFromAbsoluteFilePath(XmlUtils.class.getResource("/").getPath() + "/" + filePath, valueType);
        }
    }

    /**
     * 读取文件-根路径 +相对路径
     * @param rootPath
     * @param filePath
     * @param valueType
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T readValueFromFile(String rootPath, String filePath, Class<T> valueType) throws IOException {
        if(new File(rootPath).exists()) {
            return readValueFromAbsoluteFilePath(rootPath + "/" + filePath,valueType);
        }else {
            return readValueFromFile(filePath, valueType);
        }
    }

    /**
     * 读取文件-绝对路径
     * @param filePath
     * @param valueType
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T readValueFromAbsoluteFilePath( String filePath, Class<T> valueType) throws IOException {
        logger.debug("入参：{}|{}",filePath,valueType);
        String xmlString = FileUtils.readFile(filePath);
        logger.debug("报文：{}", xmlString);
        T t = readValue(xmlString, valueType);
        logger.debug("对象：{}", t);
        return t;
    }

    /**
     * 读取文件列表-类加载器中资源目录
     * @param <T>
     * @param directory
     * @param format
     * @return
     * @throws IOException
     */
    public static <T> File[] readValuesFromDirectory(String directory, String format) throws IOException {
        URL fileResource = XmlUtils.class.getResource("/" + directory);
        if(fileResource != null) {
            return readValueFromAbsoluteDirectoryPath(fileResource.getPath(), format);
        }else {
            return readValueFromAbsoluteDirectoryPath(XmlUtils.class.getResource("/").getPath() + "/" + directory, format);
        }
    }

    /**
     * 读取文件列表-类加载器中资源目录
     * @param directory
     * @param valueType
     * @param format
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> readValuesFromDirectory(String directory, Class<T> valueType, String format) throws IOException {
        URL fileResource = XmlUtils.class.getResource("/" + directory);
        if(fileResource != null) {
            return readValueFromAbsoluteDirectoryPath(fileResource.getPath(), valueType, format);
        }else {
            return readValueFromAbsoluteDirectoryPath(XmlUtils.class.getResource("/").getPath() + "/" + directory, valueType, format);
        }
    }


    public static <T> List<T> readValuesFromDirectory(String rootPath, String directory, Class<T> valueType, String format) throws IOException {
        if(new File(rootPath).exists()) {
            return readValueFromAbsoluteDirectoryPath(rootPath + "/" + directory, valueType, format);
        }else {
            return readValuesFromDirectory(directory, valueType, format);
        }
    }

    public static File[] readValuesFromDirectory(String rootPath, String directory, String format) throws IOException {
        if(new File(rootPath).exists()) {
            return readValueFromAbsoluteDirectoryPath(rootPath + "/" + directory, format);
        }else {
            return readValuesFromDirectory(directory, format);
        }
    }

    public static <T> List<T> readValuesFromDirectory(String directory, Class<T> valueType) throws IOException {
        return readValuesFromDirectory(directory, valueType, null);
    }

    public static File[] readValueFromAbsoluteDirectoryPath(String directory, String format) throws IOException {
        logger.debug("入参：{}|{}", directory, format);
        return  FileUtils.getFileList(new File(directory));
    }

    public static <T> List<T> readValueFromAbsoluteDirectoryPath(String directory, Class<T> valueType, String format) throws IOException {
        List<T> result = new ArrayList<T>();
        logger.debug("入参：{}|{}",directory,valueType);
        File[] fileList = FileUtils.getFileList(new File(directory));
        if(fileList == null) {
            logger.warn("file not exists !");
            return result;
        }
        for (File file : fileList) {
            if(StringUtils.isBlank(format) || file.getName().endsWith(format)) {
                String xmlString = FileUtils.readFile(file.getAbsolutePath());
                try {
                    xmlString = invokeEmbedPart(xmlString);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                logger.debug("报文：{}", xmlString);
                T t = readValue(xmlString, valueType);
                result.add(t);
                logger.debug("对象：{}", t);
            }
        }
        return result;
    }
    private static String invokeEmbedPart(String xmlContent) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] xmlEmbeds = RegexUtils.find(xmlContent, "<\b*XML_EMBED [^<>]*\b*>\b*<\b*/\b*XML_EMBED\b*>");
        for (String xmlEmbed : new HashSet<String>(Lists.newArrayList(xmlEmbeds))) {
            String[] classParts = RegexUtils.find(xmlEmbed, " class\b*=\b*\"[^\"]+\"");
            String[] methodParts = RegexUtils.find(xmlEmbed, " method\b*=\b*\"[^\"]+\"");
            String className = null, methodName = null;
            if(classParts != null && classParts.length > 0) {
                className = classParts[0].substring(0, classParts[0].length() - 1).replaceAll("class\b*=\b*\"","").trim();
            }
            if(methodParts != null && methodParts.length > 0) {
                methodName = methodParts[0].substring(0, methodParts[0].length() - 1).replaceAll("method\b*=\b*\"","").trim();
            }

            if(StringUtils.isNotBlank(className) && StringUtils.isNotBlank(methodName)) {
                String replaceString = String.valueOf(
                        Class.forName(className).getMethod(methodName, new Class[0]).invoke(null, null));
                xmlContent = xmlContent.replace(xmlEmbed, replaceString);
            }
        }
        return xmlContent;
    }


    public static  <T> String writeValueAsString(T t) throws IOException {
        XStream xstream = new XStream(new DomDriver("utf8"));
//        XStream xstream = new XStream(new DomDriver("UTF-8", new NoNameCoder()));

        xstream.processAnnotations(t.getClass());// 识别obj类中的注解
        /*
         // 以压缩的方式输出XML
         StringWriter sw = new StringWriter();
         xstream.marshal(obj, new CompactWriter(sw));
         return sw.toString();
         */
        // 以格式化的方式输出XML
        return xstream.toXML(t);
    }

    public static void main(String[] args) throws IOException {
        String rootClassPath = Thread.currentThread().getContextClassLoader ().getResource("").getPath();
        String xmlString = readFile(rootClassPath + "test.xml");
        System.out.println(xmlString);
//        Menu menu = readValue(jsonString, Menu.class);
//        Map map = readValue(xmlString, Map.class);
//        System.out.println(map);




    }

    private static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
        String retStr = "";
        String str = br.readLine();
        while(str != null) {
            retStr += str;
            str = br.readLine();
        }
        return retStr;
    }
}
