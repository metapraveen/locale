package com.tra;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBHelper {
	
	public static String marshal(Object objectName) throws JAXBException {

		  JAXBContext jaxbContext = JAXBContext.newInstance(objectName.getClass());
		  Marshaller marshaller = jaxbContext.createMarshaller();
		  marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		  StringWriter writer = new StringWriter(); 
		  marshaller.marshal(objectName, writer);
		  return writer.toString();
		 }
	
	public static Object unmarshal(Class<?> className,String xml) throws JAXBException {
		  JAXBContext jaxbContext = JAXBContext.newInstance(className);
		  Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		  StringReader reader = new StringReader(xml);
		  return unmarshaller.unmarshal(reader);

		 }

}
