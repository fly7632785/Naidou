package com.itspeed.naidou.app.util;

import android.util.Xml;

import com.itspeed.naidou.model.bean.UpdateInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * 解析工具类
 * 
 * @author kymjs
 * 
 */
public class Parser {


    /**
     * 解析从服务器获取来的xml更新文件
     * @param inStream
     * @return
     * @throws Exception
     */
    public UpdateInfo parseXmlByDom(InputStream inStream) throws Exception
    {
        UpdateInfo info = new UpdateInfo();//实体
        // 实例化一个文档构建器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 通过文档构建器工厂获取一个文档构建器
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 通过文档通过文档构建器构建一个文档实例
        Document document = builder.parse(inStream);
        //获取XML文件根节点
        Element root = document.getDocumentElement();
        //获得所有子节点
        NodeList childNodes = root.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++)
        {
            //遍历子节点
            Node childNode = childNodes.item(j);
            if (childNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element childElement = (Element) childNode;
                //版本号
                if ("version".equals(childElement.getNodeName()))
                {
                    info.setVersion(childElement.getFirstChild().getNodeValue());
                }
                //软件名称
                else if (("versionName".equals(childElement.getNodeName())))
                {
                    info.setVersionName(childElement.getFirstChild().getNodeValue());
                }
                //下载地址
                else if (("url".equals(childElement.getNodeName())))
                {
                    info.setUrl(childElement.getFirstChild().getNodeValue());
                }
            }
        }
        return info;
    }


    /*
     * 用pull解析器解析服务器返回的xml文件 (xml封装了版本号)
     */
    public static UpdateInfo getUpdataInfo(InputStream is) throws Exception{
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "utf-8");//设置解析的数据源
        int type = parser.getEventType();
        UpdateInfo info = new UpdateInfo();//实体
        while(type != XmlPullParser.END_DOCUMENT ){
            switch (type) {
                case XmlPullParser.START_TAG:
                    if("version".equals(parser.getName())){
                        info.setVersion(parser.nextText()); //获取版本号
                    }else if ("url".equals(parser.getName())){
                        info.setUrl(parser.nextText()); //获取要升级的APK文件
                    }else if ("versionName".equals(parser.getName())){
                        info.setVersionName(parser.nextText()); //获取版本名字
                    }
                    break;
            }
            type = parser.next();
        }
        return info;
    }

}
