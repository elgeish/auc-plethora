/*
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * Lg3dDataDocument.java
 * Created on September 29, 2006
 */

package org.netbeans.modules.plethora.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.netbeans.modules.plethora.editor.Lg3dDesigner;
import org.netbeans.modules.plethora.lg3d.Lg3dDataObject;
import org.netbeans.modules.plethora.peers.Container3DPeer;
import org.netbeans.modules.plethora.peers.Frame3DPeer;
import org.netbeans.modules.plethora.peers.ShapePeer;
import org.openide.filesystems.FileLock;
import org.openide.nodes.AbstractNode;
import org.openide.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Wrapper for the LG3D file. It manipulates the file and creates a DOM source
 * to be used in the XSL transformation by the code generator
 *
 * @see Lg3dDataObject
 *
 * @author Mohamed El-Geish, Sarah Nadi, and Ismail El-Helw
 * @version 1.00
 */
public class Lg3dDataDocument {
    
    public static final String ENCODING_NAME = "UTF-8";
    public static final String DTD_PUBLIC_ID = "-//NetBeans//DTD LG3D Frame 1.0//EN";
    public static final String DTD_SYSTEM_ID = "nbfs://nbhost/SystemFileSystem/xml/entities/NetBeans/DTD_LG3D_Frame_1_0";
    
    public static final ArrayList<String> CONTAINER_LIST = new ArrayList<String>();
    private static final ArrayList<String> VISUAL_ELEMENT_LIST = new ArrayList<String>();
    private static final ArrayList<String> NON_VISUAL_ELEMENT_LIST = new ArrayList<String>();
    
    private static final String COMPONENT_3D_CLASS_NAME = "org.jdesktop.lg3d.wg.Component3D";
    private static final String CONTAINER_3D_CLASS_NAME = "org.jdesktop.lg3d.wg.Container3D";
    
    private static final String SLASH = "/";
    private static final String FRAME_TAG_NAME = "Frame";
    private static final String PROPERTIES_TAG_NAME = "Properties";
    private static final String NON_VISUAL_ELEMENTS_TAG_NAME = "NonVisualElements";
    private static final String LAYOUT_TAG_NAME = "Layout";
    private static final String COMPONENTS_TAG_NAME = "Components";
    private static final String COMPONENT_TAG_NAME = "Component";
    private static final String CONTAINER_TAG_NAME = "Container";
    private static final String SIMPLE_PROPERTY_TAG_NAME = "SimpleProperty";
    private static final String COMPLEX_PROPERTY_TAG_NAME = "ComplexProperty";
    private static final String EVENT_HANDLERS_TAG_NAME = "EventHandlers";
    private static final String EVENT_HANDLER_TAG_NAME = "EventHandler";
    private static final String ELEMENT_TAG_NAME = "Element";
    private static final String PARAMETERS_TAG_NAME = "Parameters";
    private static final String SIMPLE_PARAMETER_TAG_NAME = "SimpleParameter";
    private static final String COMPLEX_PARAMETER_TAG_NAME = "ComplexParameter";
    private static final String COMPONENT_REF_TAG_NAME = "ComponentRef";
    private static final String CONTAINER_REF_TAG_NAME = "ContainerRef";
    
    private static final String ID_ATTR_NAME = "id";
    private static final String TYPE_ATTR_NAME = "type";
    private static final String REF_ATTR_NAME = "ref";
    private static final String NAME_ATTR_NAME = "name";
    private static final String VALUE_ATTR_NAME = "value";
    private static final String EVENT_ATTR_NAME = "event";
    private static final String HANDLER_ATTR_NAME = "handler";
        
    static {
        VISUAL_ELEMENT_LIST.add("org.jdesktop.lg3d.utils.shape.Box");
        VISUAL_ELEMENT_LIST.add("org.jdesktop.lg3d.utils.shape.ColorCube");
        VISUAL_ELEMENT_LIST.add("org.jdesktop.lg3d.utils.shape.Cone");
        VISUAL_ELEMENT_LIST.add("org.jdesktop.lg3d.utils.shape.Cylinder");
        VISUAL_ELEMENT_LIST.add("org.jdesktop.lg3d.utils.shape.Sphere");
        VISUAL_ELEMENT_LIST.add("org.jdesktop.lg3d.utils.shape.Text2D");
        VISUAL_ELEMENT_LIST.add("org.jdesktop.lg3d.wg.ModelLoader");
        VISUAL_ELEMENT_LIST.add("edu.aucegypt.plethora.widgets.MenuItem");
        VISUAL_ELEMENT_LIST.add("edu.aucegypt.plethora.widgets.Button");
        
        CONTAINER_LIST.add(CONTAINER_3D_CLASS_NAME);
        CONTAINER_LIST.add("edu.aucegypt.plethora.widgets.Menu");
        CONTAINER_LIST.add("edu.aucegypt.plethora.widgets.ComponentHolder");
        
        NON_VISUAL_ELEMENT_LIST.add("org.jdesktop.lg3d.sg.AmbientLight");
    }
    
    private final XPath XPATH = XPathFactory.newInstance().newXPath();
    
    private Lg3dDataObject lg3dDataObject;
    private Document document;
    private DOMSource source;
    private FileLock fileLock;
    private Hashtable<String, Integer> generatedIds;
    private Node framePropertiesNode;
    private Node nonVisualElementsNode;
    private Node componentsNode;
    private Node layoutNode;
    
    public Lg3dDataDocument(Lg3dDataObject lg3dDataObject) {
        this.lg3dDataObject = lg3dDataObject;
        generatedIds = new Hashtable<String, Integer>();
        
        try {
            document = XMLUtil.parse(
                    new InputSource(lg3dDataObject.getLg3dFileObject().getURL().toExternalForm()),
                    true, false, null, null);
            source = new DOMSource(document);
            
            framePropertiesNode = selectSingleNode(SLASH + FRAME_TAG_NAME + SLASH + PROPERTIES_TAG_NAME);
            nonVisualElementsNode = selectSingleNode(SLASH + FRAME_TAG_NAME + SLASH + NON_VISUAL_ELEMENTS_TAG_NAME);
            componentsNode = selectSingleNode(SLASH + FRAME_TAG_NAME + SLASH + COMPONENTS_TAG_NAME);
            layoutNode = selectSingleNode(SLASH + FRAME_TAG_NAME + SLASH + LAYOUT_TAG_NAME);
            
        } catch (Exception ex) {
            org.openide.ErrorManager.getDefault().notify(ex);
        }
    }
    
    public Lg3dDataObject getLg3dDataObject() {
        return lg3dDataObject;
    }
    
    public Document getDocument() {
        return document;
    }
    
    public DOMSource getSource() {
        return source;
    }
    
    public void saveDocument() throws IOException {
        if (fileLock == null || !fileLock.isValid()) {
            fileLock = lg3dDataObject.getLg3dFileEntry().takeLock();
        }
        
        try {
            
            XMLUtil.write(document,
                    lg3dDataObject.getLg3dFileObject().getOutputStream(fileLock),
                    ENCODING_NAME);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        fileLock.releaseLock();
    }
    
    private Node selectSingleNode(String expression) throws XPathExpressionException {
        return (Node) XPATH.evaluate(expression, document, XPathConstants.NODE);
    }
    
    private NodeList selectNodes(String expression) throws XPathExpressionException {
        return (NodeList) XPATH.evaluate(expression, document, XPathConstants.NODESET);
    }
    
    public boolean isUnique(String id) throws XPathExpressionException {
        return selectSingleNode("//*[@" + ID_ATTR_NAME + "='" + id + "']") == null;
    }
    
    private String generateId(String type) {
        String className = type.substring(type.lastIndexOf('.') + 1).toLowerCase();
        Integer id = generatedIds.get(className);
        
        id = new Integer((id == null) ? 1 : id.intValue() + 1);
        generatedIds.put(className, id);
        
        return className + id;
    }
    
    public IdPair addElement(String type) throws XPathExpressionException {
        String id = generateId(type);
        IdPair idPair = new IdPair();
        
        while (!isUnique(id)) {
            id = generateId(type);
        }
        
        if (CONTAINER_LIST.contains(type)) {
            
            Element container = document.createElement(CONTAINER_TAG_NAME);
            Element containerRef = document.createElement(CONTAINER_REF_TAG_NAME);
            
            container.setAttribute(ID_ATTR_NAME, id);
            container.setAttribute(TYPE_ATTR_NAME, type);
            
            container.appendChild(document.createElement(PROPERTIES_TAG_NAME));
            container.appendChild(document.createElement(EVENT_HANDLERS_TAG_NAME));
            
            containerRef.setAttribute(REF_ATTR_NAME, id);
            
            componentsNode.appendChild(container);
            layoutNode.appendChild(containerRef);
            
        } else if (VISUAL_ELEMENT_LIST.contains(type)) {
            
            String componentId = generateId(COMPONENT_3D_CLASS_NAME);
            Element component = document.createElement(COMPONENT_TAG_NAME);
            Element componentRef = document.createElement(COMPONENT_REF_TAG_NAME);
            Element element = document.createElement(ELEMENT_TAG_NAME);
            Element parameter;
            
            while (!isUnique(componentId)) {
                componentId = generateId(COMPONENT_3D_CLASS_NAME);
            }
            
            component.setAttribute(ID_ATTR_NAME, componentId);
            component.setAttribute(TYPE_ATTR_NAME, COMPONENT_3D_CLASS_NAME);
            
            component.appendChild(document.createElement(PROPERTIES_TAG_NAME));
            component.appendChild(document.createElement(EVENT_HANDLERS_TAG_NAME));
            
            element.setAttribute(ID_ATTR_NAME, id);
            element.setAttribute(TYPE_ATTR_NAME, type);
            element.appendChild(document.createElement(PARAMETERS_TAG_NAME));
            element.appendChild(document.createElement(PROPERTIES_TAG_NAME));
            
            componentRef.setAttribute(REF_ATTR_NAME, componentId);
            
            component.appendChild(element);
            componentsNode.appendChild(component);
            layoutNode.appendChild(componentRef);
            
            idPair.setComponentId(componentId);
            
        } else {
            
            Element element = document.createElement(ELEMENT_TAG_NAME);
            Element parameter;
            
            element.setAttribute(ID_ATTR_NAME, id);
            element.setAttribute(TYPE_ATTR_NAME, type);
            element.appendChild(document.createElement(PARAMETERS_TAG_NAME));
            element.appendChild(document.createElement(PROPERTIES_TAG_NAME));
            
            nonVisualElementsNode.appendChild(element);
        }
        
        idPair.setShapeId(id);
        return idPair;
    }
    
    public void removeElement(String id) throws XPathExpressionException {
        Element element = (Element) selectSingleNode("//*[@" + ID_ATTR_NAME + "='" + id + "']");
        Element parent = (Element) element.getParentNode();
        
        if (element.getTagName().equals(CONTAINER_TAG_NAME)) {
            Element containerRef = (Element) selectSingleNode(
                    SLASH + FRAME_TAG_NAME + SLASH + LAYOUT_TAG_NAME +
                    SLASH + SLASH + CONTAINER_REF_TAG_NAME + "[@" + REF_ATTR_NAME + "='" + id + "']");
            
            NodeList nodes = containerRef.getChildNodes(); // Any references inside this ContainerRef
            ArrayList<String> ids = new ArrayList<String>();
            
            for (int i = 0 ; i < nodes.getLength() ; i++) {
                if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    ids.add(((Element) nodes.item(i)).getAttribute(REF_ATTR_NAME));
                }
            }
            
            for (String childId : ids) {
                removeElement(childId);
            }
            
            containerRef.getParentNode().removeChild(containerRef); // Remove this ContainerRef from layout
        } else if (element.getTagName().equals(COMPONENT_TAG_NAME)) {
            Element componentRef = (Element) selectSingleNode(
                    SLASH + FRAME_TAG_NAME + SLASH + LAYOUT_TAG_NAME +
                    SLASH + SLASH + COMPONENT_REF_TAG_NAME + "[@" + REF_ATTR_NAME + "='" + id + "']");
            
            if (componentRef != null) {
                componentRef.getParentNode().removeChild(componentRef);
            }
        } else if (element.getTagName().equals(ELEMENT_TAG_NAME) && parent.getTagName().equals(NON_VISUAL_ELEMENTS_TAG_NAME)) {
            NodeList nodes = selectNodes("//*[@" + VALUE_ATTR_NAME + "='" + id + "']");
            
            if (nodes != null) {
                for (int i = 0 ; i < nodes.getLength() ; i++) {
                    nodes.item(i).getParentNode().removeChild(nodes.item(i));
                }
            }
        } else if (element.getTagName().equals(ELEMENT_TAG_NAME)) { // if it's an element in Component/Container
            removeElement(parent.getAttribute(ID_ATTR_NAME));
        }
        
        /* After all processing of removing referencs, the actual Component/Container/Element will be removed */
        parent.removeChild(element);
    }
    
    public boolean renameElement(String oldId, String newId) throws XPathExpressionException {
        if (!isUnique(newId)) {
            return false;
        }
        
        Element element = (Element) selectSingleNode("//*[@" + ID_ATTR_NAME + "='" + oldId + "']");
        
        element.setAttribute(ID_ATTR_NAME, newId);
        element = (Element) selectSingleNode(SLASH + FRAME_TAG_NAME + SLASH + LAYOUT_TAG_NAME + "//*[@" + REF_ATTR_NAME + "='" + oldId + "']");
        
        if (element != null) {
            element.setAttribute(REF_ATTR_NAME, newId);
        }
        
        NodeList nodes = selectNodes(SLASH + FRAME_TAG_NAME + SLASH + COMPONENTS_TAG_NAME + "//*[@" + VALUE_ATTR_NAME + "='" + oldId + "']");
        
        if (nodes != null) {
            for (int i = 0 ; i < nodes.getLength() ; ++i) {
                if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    ((Element) nodes.item(i)).setAttribute(VALUE_ATTR_NAME, newId);
                }
            }
        }
        
        return true;
    }
    
    public void setParameters(String elementId, Parameter... parameters) throws XPathExpressionException {
        Element params = (Element) selectSingleNode("//Element[@" + ID_ATTR_NAME + "='" + elementId + "']/" + PARAMETERS_TAG_NAME);
        Element element = (Element) params.getParentNode();
        
        element.removeChild(params);
        element.appendChild(createParameters(parameters));
    }
    
    private Element createParameters(Parameter... params) {
        Element parameters = document.createElement(PARAMETERS_TAG_NAME);
        Element parameter;
        
        if (params != null) {
            for (Parameter param : params) {
                if (param instanceof SimpleParameter) {
                    parameter = document.createElement(SIMPLE_PARAMETER_TAG_NAME);
                    parameter.setAttribute(VALUE_ATTR_NAME, ((SimpleParameter) param).getValue());
                } else {
                    parameter = document.createElement(COMPLEX_PARAMETER_TAG_NAME);
                    parameter.appendChild(createParameters(((ComplexParameter) param).getParameters().toArray(new Parameter[0])));
                }
                
                parameter.setAttribute(TYPE_ATTR_NAME, param.getType());
                parameters.appendChild(parameter);
            }
        }
        
        return parameters;
    }
    
    public void setProperty(String elementId, String propertyName, String propertyType, String propertyValue) throws XPathExpressionException {
        Element property = (Element) selectSingleNode("//*[@" + ID_ATTR_NAME + "='" + elementId + "']/" + PROPERTIES_TAG_NAME + SLASH + SIMPLE_PROPERTY_TAG_NAME + "[@" + NAME_ATTR_NAME + "='" + propertyName +"']");
        
        if (property == null) {
            Node propertiesElement = selectSingleNode("//*[@" + ID_ATTR_NAME + "='" + elementId + "']/" + PROPERTIES_TAG_NAME);
            
            property = document.createElement(SIMPLE_PROPERTY_TAG_NAME);
            property.setAttribute(NAME_ATTR_NAME, propertyName);
            property.setAttribute(TYPE_ATTR_NAME, propertyType);
            propertiesElement.appendChild(property);
        }
        
        property.setAttribute(VALUE_ATTR_NAME, propertyValue);
    }
    
    public void setProperty(String elementId, String propertyName, String propertyType, Parameter... parameters) throws XPathExpressionException {
        Element property = (Element) selectSingleNode("//*[@" + ID_ATTR_NAME + "='" + elementId + "']" +
                SLASH + PROPERTIES_TAG_NAME + SLASH + COMPLEX_PROPERTY_TAG_NAME +
                "[@" + NAME_ATTR_NAME + "='" + propertyName +"']");
        Element properties = (Element) selectSingleNode("//*[@" + ID_ATTR_NAME + "='" + elementId + "']/" + PROPERTIES_TAG_NAME);
        Element params = document.createElement(PARAMETERS_TAG_NAME);
        Element parameter;
        
        if (property != null) {
            properties.removeChild(property);
        }
        
        property = document.createElement(COMPLEX_PROPERTY_TAG_NAME);
        
        property.setAttribute(NAME_ATTR_NAME, propertyName);
        property.setAttribute(TYPE_ATTR_NAME, propertyType);
        
        property.appendChild(createParameters(parameters));
        properties.appendChild(property);
    }
    
    public void removeProperty(String elementId, String propertyName) throws XPathExpressionException {
        Element property = (Element) selectSingleNode("//*[@" + ID_ATTR_NAME + "='" + elementId + "']/" + PROPERTIES_TAG_NAME + SLASH + "*[@" + NAME_ATTR_NAME + "='" + propertyName +"']");
        
        if (property != null) {
            property.getParentNode().removeChild(property);
        }
    }
    
    public void moveComponentToContainer(String containerId, String componentId) throws XPathExpressionException {
        Element componentToBeMoved = (Element) selectSingleNode(SLASH + FRAME_TAG_NAME + SLASH + LAYOUT_TAG_NAME + SLASH + SLASH + "*[@" + REF_ATTR_NAME + "='" + componentId + "']");
        
        if (componentToBeMoved != null) {
            componentToBeMoved.getParentNode().removeChild(componentToBeMoved);
        }
        
        if (containerId == null) {
            layoutNode.appendChild(componentToBeMoved);
        } else {
            Element container = (Element) selectSingleNode(SLASH + FRAME_TAG_NAME + SLASH + LAYOUT_TAG_NAME + SLASH + SLASH + "*[@" + REF_ATTR_NAME + "='" + containerId + "']");
            
            container.appendChild(componentToBeMoved);
        }
    }
    
    public void setEventHandler(String elementId, String name, String event, String handler) throws XPathExpressionException{
        Element elementEventHandlers = (Element) selectSingleNode(SLASH + FRAME_TAG_NAME + SLASH + COMPONENTS_TAG_NAME + SLASH + "*[@" + ID_ATTR_NAME + "='" + elementId + "']/" + EVENT_HANDLERS_TAG_NAME);
        Element eventHandler = (Element) selectSingleNode(SLASH + FRAME_TAG_NAME + SLASH + COMPONENTS_TAG_NAME + SLASH + "*[@" + ID_ATTR_NAME + "='" + elementId + "']/" + EVENT_HANDLERS_TAG_NAME + SLASH + EVENT_HANDLER_TAG_NAME + "[@" + NAME_ATTR_NAME + "='" + name + "']");
        
        if (handler != null && !handler.equals("")) {
            if (eventHandler == null) {
                eventHandler = document.createElement(EVENT_HANDLER_TAG_NAME);
                elementEventHandlers.appendChild(eventHandler);
            }
            
            eventHandler.setAttribute(NAME_ATTR_NAME, name);
            eventHandler.setAttribute(EVENT_ATTR_NAME, event);
            eventHandler.setAttribute(HANDLER_ATTR_NAME, handler);
        } else if (eventHandler != null) { // Remove handler
            elementEventHandlers.removeChild(eventHandler);
        }
    }
    
    /* Shape Loading Section */
    
    public Frame3DPeer getFrame3DPeer() {
        Vector<ComponentDescription> componentDescriptions = new Vector<ComponentDescription>();
        Vector<ContainerDescription> containerDescriptions = new Vector<ContainerDescription>();
        Vector<Property> frameProperties = new Vector<Property>();
        Hashtable<String, AbstractNode> elements = new Hashtable<String, AbstractNode>();
        
        for (Node cNode = componentsNode.getFirstChild(); cNode != null; cNode = cNode.getNextSibling()) {
            if (cNode.getNodeName().equals(COMPONENT_TAG_NAME)) {
                ComponentDescription componentDescription = new ComponentDescription(
                        ((Element) cNode).getAttribute(ID_ATTR_NAME),
                        ((Element) cNode).getAttribute(TYPE_ATTR_NAME));
                
                String nodeName = null;
                
                for (Node node = cNode.getFirstChild(); node != null ; node = node.getNextSibling()) {
                    nodeName = node.getNodeName();
                    
                    if (nodeName.equals(PROPERTIES_TAG_NAME)) {
                        componentDescription.setProperties(getPropertiesFromNode(node));
                    } else if (nodeName.equals(EVENT_HANDLERS_TAG_NAME)) {
                        componentDescription.setUserEvents(getUserEventsFromNode(node));
                    } else if (nodeName.equals(ELEMENT_TAG_NAME)) {
                        componentDescription.setShapeDescription(getShapeDescriptionFromNode(node));
                    }
                }
                componentDescriptions.addElement(componentDescription);
                elements.put(componentDescription.getId(), ShapeLoader.createShapePeer(componentDescription));
            } else if (cNode.getNodeName().equals(CONTAINER_TAG_NAME)) {
                ContainerDescription containerDescription = new ContainerDescription(
                        ((Element) cNode).getAttribute(ID_ATTR_NAME),
                        ((Element) cNode).getAttribute(TYPE_ATTR_NAME));
                
                for (Node node = cNode.getFirstChild(); node != null ; node = node.getNextSibling()) {
                    if (node.getNodeName().equals(PROPERTIES_TAG_NAME)) {
                        containerDescription.setProperties(getPropertiesFromNode(node));
                    } else if (node.getNodeName().equals(EVENT_HANDLERS_TAG_NAME)) {
                        containerDescription.setUserEvents(getUserEventsFromNode(node));
                    }
                }
                containerDescriptions.addElement(containerDescription);
                elements.put(containerDescription.getId(), ShapeLoader.createContainer3DPeer(containerDescription));
            }
        }
        
        frameProperties = getPropertiesFromNode(framePropertiesNode);
        
        return getFrame3DPeer(elements);
    }
    
    private Frame3DPeer getFrame3DPeer(Hashtable<String, AbstractNode> elements) {
        Frame3DPeer framePeer = new Frame3DPeer();
        AbstractNode peer = null;
        
        Lg3dDesigner.clear();
        
        for (Node node = layoutNode.getFirstChild(); node != null; node = node.getNextSibling()) {
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                peer = elements.get(((Element) node).getAttribute(REF_ATTR_NAME));
                
                if (node.getNodeName().equals(CONTAINER_REF_TAG_NAME)) {
                    doContainer3DPeerLayout(peer, node, elements);
                }
                
                framePeer.addChild((ShapePeer) peer);
            }
        }
        
        framePeer.setName(getLg3dDataObject().getName() + " [Frame3D]");
        
        return framePeer;
    }
    
    private void doContainer3DPeerLayout(AbstractNode containerPeer, Node container, Hashtable<String, AbstractNode> elements) {
        AbstractNode peer = null;
        
        for (Node node = container.getFirstChild(); node != null; node = node.getNextSibling()) {
            if(node.getNodeType() == Node.ELEMENT_NODE) {
                peer = elements.get(((Element) node).getAttribute(REF_ATTR_NAME));
                
                if (node.getNodeName().equals(CONTAINER_REF_TAG_NAME)) {
                    doContainer3DPeerLayout(peer, node, elements);
                }
                
                ((ShapePeer) containerPeer).addChild((ShapePeer) peer);
            }
        }
    }
    
    private Vector<Property> getPropertiesFromNode(Node propertiesNode) {
        Vector<Property> props = new Vector<Property>();
        Element element = null;
        String nodeName = null;
        
        for (Node node = propertiesNode.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                nodeName = node.getNodeName();
                element = (Element) node;
                
                if (nodeName.equals(SIMPLE_PROPERTY_TAG_NAME)) {
                    
                    props.addElement(new SimpleProperty(
                            element.getAttribute(NAME_ATTR_NAME),
                            element.getAttribute(TYPE_ATTR_NAME),
                            element.getAttribute(VALUE_ATTR_NAME)));
                    
                } else if (nodeName.equals(COMPLEX_PROPERTY_TAG_NAME)) {
                    
                    props.addElement(new ComplexProperty(
                            element.getAttribute(NAME_ATTR_NAME),
                            element.getAttribute(TYPE_ATTR_NAME),
                            getParametersFromNode(element.getElementsByTagName(PARAMETERS_TAG_NAME).item(0))));
                    
                }
            }
        }
        
        return props;
    }
    
    private Vector<Parameter> getParametersFromNode(Node parametersNode) {
        Vector<Parameter> params = new Vector<Parameter>(3);
        
        for (Node node = parametersNode.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeName().equals(SIMPLE_PARAMETER_TAG_NAME)) {
                params.addElement(new SimpleParameter(
                        ((Element) node).getAttribute(TYPE_ATTR_NAME),
                        ((Element) node).getAttribute(VALUE_ATTR_NAME)));
            } else if (node.getNodeName().equals(COMPLEX_PARAMETER_TAG_NAME)) {
                params.addElement(new ComplexParameter(
                        ((Element) node).getAttribute(TYPE_ATTR_NAME),
                        getParametersFromNode(((Element) node).getElementsByTagName(PARAMETERS_TAG_NAME).item(0))));
            }
        }
        
        return params;
    }
    
    private Vector<UserEvent> getUserEventsFromNode(Node userEventsNode) {
        Vector<UserEvent> events = new Vector<UserEvent>(3);
        
        for (Node node = userEventsNode.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeName().equals(EVENT_HANDLER_TAG_NAME)) {
                events.addElement(new UserEvent(
                        ((Element) node).getAttribute(NAME_ATTR_NAME),
                        ((Element) node).getAttribute(EVENT_ATTR_NAME),
                        ((Element) node).getAttribute(HANDLER_ATTR_NAME)));
            }
        }
        
        return events;
    }
    
    private ShapeDescription getShapeDescriptionFromNode(Node shapeNode) {
        Element shape = (Element) shapeNode;
        ShapeDescription shapeDescription = new ShapeDescription(
                shape.getAttribute(ID_ATTR_NAME),
                shape.getAttribute(TYPE_ATTR_NAME));
        
        for (Node node = shape.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeName().equals(PARAMETERS_TAG_NAME)) {
                shapeDescription.setParameters(getParametersFromNode(node));
            } else if (node.getNodeName().equals(PROPERTIES_TAG_NAME)) {
                shapeDescription.setProperties(getPropertiesFromNode(node));
            }
        }
        
        return shapeDescription;
    }
    
}