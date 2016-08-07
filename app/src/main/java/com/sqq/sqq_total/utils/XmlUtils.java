package com.sqq.sqq_total.utils;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by sqq on 2016/8/4.
 */
public class XmlUtils {

    /**
     * 解析某个路径下的xml文件
     * @return
     */

	/*public static ArrayList<ViewItem> GetList(String filePath,Context con) {
        ArrayList<ViewItem> vitems = new ArrayList<>();
        String pathName = filePath;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File(filePath));
            NodeList list= document.getElementsByTagName("node");

            for (int i=0;i<list.getLength();i++){
                ViewItem viewItem = new ViewItem();
                Element element = (Element) list.item(i);
                viewItem.setName(element.getElementsByTagName("name")
                        .item(0).getAttributes().item(0).getNodeValue());
                viewItem.setVisible(element.getElementsByTagName("visible")
                        .item(0).getAttributes().item(0).getNodeValue().toLowerCase()
                        .equals("true"));
                String Location = element.getElementsByTagName("location")
                        .item(0).getAttributes().item(0).getNodeValue();
                String dou = ",";
                String x = Location.substring(0,Location.indexOf(dou));
                String y = Location.substring(Location.indexOf(dou)+1,Location.length());
                viewItem.setLocationX(Integer.parseInt(x));
                viewItem.setLocationY(Integer.parseInt(y));

                viewItem.setSize(Integer.parseInt(element.getElementsByTagName("size")
                        .item(0).getAttributes().item(0).getNodeValue()));
                viewItem.setColor(element.getElementsByTagName("color")
                        .item(0).getAttributes().item(0).getNodeValue());

                vitems.add(viewItem);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            Log.d("sqqq",e.toString());
        } catch (SAXException e) {
            e.printStackTrace();
            Log.d("sqqq", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("sqqq", e.toString());
        }


        return vitems;
	}*/
}
