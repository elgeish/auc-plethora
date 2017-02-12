/*
 * License Notice
 *
 * This file is part of Plethora.
 *
 * Plethora is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License.
 *
 * Plethora is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Plethora; if not, write to the Free Software
 *
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * LGCFG.java
 * Created on December 01, 2006
 */

package plethora.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;

/**
 * Utility class for updating the lgcfg file in the user's project
 *
 * @author Mohamed El-Geish
 * @version 1.00
 */
public class LGCFG {
    
    private static final XPath XPATH = XPathFactory.newInstance().newXPath();
    
    private static LSParser parser;
    private static Document lgcfg;
    
    static {
        try {
            parser = ((DOMImplementationLS) DOMImplementationRegistry
                    .newInstance()
                    .getDOMImplementation("LS"))
                    .createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
            lgcfg = parser.parseURI(LGCFG.class.getResource("..\\plethora.lgcfg").toExternalForm());
        } catch(Exception ex) {}
    }
    
    private static void setValue(String key, String value) throws XPathExpressionException {
        NodeList nodeList = (NodeList) XPATH.evaluate("/java/object/void[@property='" + key + "']/string/text()", lgcfg, XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); ++i) {
            nodeList.item(i).setNodeValue(value);
        }
    }
    
    public static void main(String[] args) throws Exception {
        String line;
        String projectName;
        String mainClass = "";
        Document project = parser.parseURI(LGCFG.class.getResource("..\\..\\nbproject\\project.xml").toExternalForm());
        BufferedReader properties = new BufferedReader(
                new InputStreamReader(LGCFG.class.getResourceAsStream("..\\..\\nbproject\\project.properties")));

        while ((line = properties.readLine()) != null) {
            if(line.startsWith("main.class=")) {
                mainClass = line.substring(line.indexOf('=') + 1);
                break;
            }
        }
        
        if (mainClass.equals("")) {
            System.out.println("LGCFG ERROR: Please specify a main class.");
        }
        
        /* XXX: Xpath is not working, dont know why! */
        projectName = ((Element) ((Element) project
                .getDocumentElement()
                .getElementsByTagName("configuration")
                .item(0))
                .getElementsByTagName("data")
                .item(0))
                .getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
               
        setValue("exec", "java " + mainClass);
        setValue("command", "java " + mainClass);
        setValue("name", projectName);
        setValue("classpathJars", projectName.replace(' ', '_') + ".jar");
        
        ((DOMImplementationLS) lgcfg.getImplementation()).createLSSerializer().writeToURI(lgcfg, lgcfg.getDocumentURI());
    }
    
}