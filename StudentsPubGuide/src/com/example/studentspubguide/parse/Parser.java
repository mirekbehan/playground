package com.example.studentspubguide.parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {
	private List<Placemark> myPlacemarks;
	private Document dom;
	
	public Parser() {
		myPlacemarks = new ArrayList<Placemark>();
		
		parseXmlFile();
		parseDocument();
	}
	
	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			this.dom = db.parse("/mnt/sdcard/mapa.kml");


		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void parseDocument(){
		//get the root element
		Element docEle = dom.getDocumentElement();

		//get a nodelist of  elements
		NodeList nl = docEle.getElementsByTagName("Placemark");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {

				//get the employee element
				Element el = (Element)nl.item(i);

				//get the Employee object
				//Employee e = getEmployee(el);
				Placemark  pl = getPlacemark(el);

				//add it to list
				System.out.println(pl.getCoordinates());
				myPlacemarks.add(pl);
			}
		}
	}
	private Placemark getPlacemark(Element empEl) {

		//for each <employee> element get text or int values of
		//name ,id, age and name
		
		String name = getTextValue(empEl,"name");
		String desc = getTextValue(empEl, "description");
		String coordinates = getTextValue(empEl, "coordinates");
		
		
		
		//String type = empEl.getAttribute("type");

		//Create a new Employee with the value read from the xml nodes
		Placemark pl = new Placemark(coordinates, name, desc);

		return pl;
	}


	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content
	 * i.e for <employee><name>John</name></employee> xml snippet if
	 * the Element points to employee node and tagName is 'name' I will return John
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}


	/**
	 * Calls getTextValue and returns a int value
	 */
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}
	
	
}
