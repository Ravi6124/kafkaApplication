package com.example.kafkaApplication.services.read;

import com.example.kafkaApplication.entity.Employee;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import javax.xml.parsers.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class XMLRead {
        private List<Employee> arrayList = new ArrayList<Employee>();
        private DocumentBuilderFactory dbFactory;
        private Document document;
        private DocumentBuilder dBuilder;
        private Element root;

        public XMLRead() {
            this.dbFactory = DocumentBuilderFactory.newInstance();
            try {
                dBuilder = dbFactory.newDocumentBuilder();
                this.document = dBuilder.newDocument();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.root = document.createElement("Employees");
            document.appendChild(root);
        }

        public List<Employee> read() {
            String filePath = "/Users/ravikumarjha/Desktop/kafkaApplication/src/main/resources/employee.xml" ;           File xmlFile = new File(filePath);

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder;
            try {
                documentBuilder = documentBuilderFactory.newDocumentBuilder();//ParserConfigurationException
                Document document = documentBuilder.parse(xmlFile);
                document.getDocumentElement().normalize();

                NodeList nodeList = document.getElementsByTagName("employee");
                System.out.println(nodeList.item(0));
                for (int j = 0; j < nodeList.getLength(); j++) {
                    arrayList.add(getEmployee(nodeList.item(j)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        private static Employee getEmployee(Node node) {

            Employee emp = new Employee();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                emp.setFirstName(getTagValue("firstName", element));
                emp.setLastName(getTagValue("lastName", element));
                try {
                    Date date = (new SimpleDateFormat("MM/dd/yy")).parse(getTagValue("dateOfBirth", element));
                    emp.setDateOfBirth(date);
                    emp.setExperience(Double.parseDouble(getTagValue("experience", element)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return emp;
        }

        private static String getTagValue(String tag, Element element) {
            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = (Node) nodeList.item(0);
            return node.getNodeValue();
        }
}
