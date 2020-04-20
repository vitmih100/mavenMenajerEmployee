package menajeremployee;

import enums.Gender;
import java.util.List;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class XmlIOService {
  public void writeToFile(List<Employee> emps) throws FileNotFoundException,IOException{
    Element root = new Element("employees");
    Document doc = new Document(root);
  
    for(Employee emp : emps){
      Element employee = new Element("employee");
      employee.setAttribute(new Attribute("id",String.valueOf(emp.getId())));
      Element name = new Element("name");
      name.setText(emp.getName());
      
      Element surName = new Element("surname");
      surName.setText(emp.getsurName());

      Element salary = new Element("salary");
      salary.setText(String.valueOf(emp.getSalary()));
      
      Element hireDay = new Element("hireday");
      hireDay.setText(String.valueOf(emp.getHireDay()));
     
      Element birthDay = new Element("birthday");
      birthDay.setText(String.valueOf(emp.getBirthDay()));

      Element gender = new Element("gender");
      gender.setText(String.valueOf(emp.getGender()));
      
      employee.addContent(name);
      employee.addContent(surName);
      employee.addContent(salary);
      employee.addContent(hireDay);
      employee.addContent(birthDay);
      employee.addContent(gender);
      root.addContent(employee);
    }
    XMLOutputter xmlOutput = new XMLOutputter();
    xmlOutput.setFormat(Format.getPrettyFormat());
    xmlOutput.output(doc, new FileOutputStream("src/main/java/menajeremployee/employee.xml"));
    //xmlOutput.output(doc, System.out); // Console
  }
  
  public List<Employee> readFromFile() throws JDOMException,IOException{
    List<Employee> staff = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    File inputFile = new File("src/main/java/menajeremployee/employee.xml");
    SAXBuilder saxBuilder = new SAXBuilder();
    Document document = saxBuilder.build(inputFile);
    System.out.println("Root element :" + document.getRootElement().getName());
    Element root = document.getRootElement();
    
    List<Element> employees = root.getChildren("employee");
    System.out.println("--------------------");
    
    for(int index=0; index < employees.size();index++){
      Element employee = employees.get(index);
     
      Attribute attribute = employee.getAttribute("id");
      System.out.println("id: " + attribute.getValue());

      Element name = employee.getChild("name");
      String nameValue = name.getText();
      System.out.println("name: " + nameValue);
 
      Element surName = employee.getChild("surname");
      String surNameValue = surName.getText();
      System.out.println("surname: " + surNameValue);
      
      Element salary = employee.getChild("salary");
      String salaryValue = salary.getText();
      System.out.println("salary: " + salaryValue);

      Element hireDay = employee.getChild("hireday");
      String hireDayValue = hireDay.getText();
      System.out.println("hireday: " + hireDayValue);

      Element birthDay = employee.getChild("birthday");
      String birthDayValue = birthDay.getText();
      System.out.println("surname: " + birthDayValue);

      Element gender = employee.getChild("gender");
      String genderValue = gender.getText();
      System.out.println("gender: " + genderValue);    
      System.out.println("");
      staff.add(new Employee(0,nameValue,surNameValue,Double.valueOf(salaryValue),
                LocalDate.parse(hireDayValue,formatter),LocalDate.parse(birthDayValue,formatter),Gender.valueOf(genderValue)));
    }
    return staff;
  }
    
}
