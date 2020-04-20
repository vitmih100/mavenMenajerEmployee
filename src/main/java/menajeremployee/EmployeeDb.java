package menajeremployee;

import enums.Gender;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDb <T>{
  void insert(Employee emp) {
     String sql = "insert into postgres.employee(idnp,nume,prenume,sarariu,data_ang,data_nast,gen)"
                + "values(?,?,?,?,?,?,?)";
    Connection conn = ConnectionDb.initConnection();
    PreparedStatement statement=null;
    
    try{
      if(conn!=null){
        statement = conn.prepareStatement(sql);
        statement.setInt   (1, emp.getIdnp());
        statement.setString(2, emp.getName());
        statement.setString(3, emp.getsurName());
        statement.setFloat (4, (float)emp.getSalary());
        statement.setDate  (5, Date.valueOf(emp.getHireDay()));
        statement.setDate  (6, Date.valueOf(emp.getBirthDay()));
        statement.setInt   (7, emp.getGender().ordinal());
        int rows = statement.executeUpdate();
        System.out.println("Inserted" + rows + " rows"); 
      } else {
        System.out.println("Insert failed");
      } 
    } catch(SQLException ex){
      System.out.println("ERROR! Insert failed. " + ex.getMessage());
    }finally{
      try {
        if(statement!=null){
          statement.close(); 
        }
        if(conn!=null){
          conn.close();
        }
      }catch(SQLException ex){
        System.out.println(ex.getMessage());
      }
    }   
  }
  void insert(List<Employee> emps) {
     String sql = "insert into postgres.employee(idnp,nume,prenume,sarariu,data_ang,data_nast,gen)"
                + "values(?,?,?,?,?,?,?)";
    Connection conn = ConnectionDb.initConnection();
    PreparedStatement statement=null;
    
    try{
      if(conn!=null){
        for(Employee emp : emps){
          statement = conn.prepareStatement(sql);
          statement.setInt   (1, emp.getIdnp());
          statement.setString(2, emp.getName());
          statement.setString(3, emp.getsurName());
          statement.setFloat (4, (float)emp.getSalary());
          statement.setDate  (5, Date.valueOf(emp.getHireDay()));
          statement.setDate  (6, Date.valueOf(emp.getBirthDay()));
          statement.setInt   (7, emp.getGender().ordinal());
          int rows = statement.executeUpdate();
          System.out.println("Inserted " + rows + " rows"); 
        }
      } else {
        System.out.println("Multiple insert failed");
      } 
    } catch(SQLException ex){
      System.out.println("ERROR! Multiple insert failed. " + ex.getMessage());
    }finally{
      try {
        if(statement!=null){
          statement.close(); 
        }
        if(conn!=null){
          conn.close();
        }
      }catch(SQLException ex){
        System.out.println(ex.getMessage());
      }
    }       
      
  }
  
  List<Employee> get(T searchParametr,int numParametr) {

    List<Employee> staff = new ArrayList<>();
    String sql = "SELECT id, idnp, nume, prenume, sarariu, data_ang, data_nast, gen " +
                 "FROM postgres.employee ";
    Connection conn = ConnectionDb.initConnection();
    PreparedStatement statement=null;

    try{
      if(conn!=null){
        switch(numParametr){
          case 0: 
            break;
          case 1: 
            sql=sql+"where id=?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1,(Integer)searchParametr);
            break;
          case 2: 
            sql=sql+"where idnp=?";
            statement = conn.prepareStatement(sql);
            statement.setString(1,(String)searchParametr);
            break;
          case 3: 
            sql=sql+"where nume=?";
            statement = conn.prepareStatement(sql);
            statement.setString(1,(String)searchParametr);
            break;
          case 4: 
            sql=sql+"where prenume=?";
            statement = conn.prepareStatement(sql);
            statement.setString(1,(String)searchParametr);
            break;
          case 5: 
            sql=sql+"where sarariu=?";
            statement = conn.prepareStatement(sql);
            statement.setDouble(1,(Double)searchParametr);
            break;
          case 6: 
            sql=sql+"where data_ang=?";
            statement = conn.prepareStatement(sql);
            statement.setDate(1,Date.valueOf((LocalDate)searchParametr));
            break;
          case 7: 
            sql=sql+"where data_nast=?";
            statement = conn.prepareStatement(sql);
            statement.setDate(1,Date.valueOf((LocalDate)searchParametr));
            break;
          case 8: 
            sql=sql+"where gen=?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1,((Gender)searchParametr).ordinal());
            break;
        }
        ResultSet result = statement.executeQuery();
        while (result.next()) {
          int id = result.getInt("id");
          String name = result.getString("nume");
          String surname = result.getString("prenume");
          double salary = result.getDouble("sarariu");
          LocalDate hireDay = result.getDate("data_ang").toLocalDate();
          LocalDate birthDay = result.getDate("data_nast").toLocalDate(); 
          Gender gender = Gender.values()[result.getInt("gen")];
          staff.add(new Employee(id,name,surname,salary,hireDay,birthDay,gender));
        }
        
      } else {
        System.out.println("Select failed");
      } 
    } catch(SQLException ex){
      System.out.println("ERROR! Select failed. " + ex.getMessage());
    }finally{
      try {
        if(statement!=null){
          statement.close(); 
        }
        if(conn!=null){
          conn.close();
        }
      }catch(SQLException ex){
        System.out.println(ex.getMessage());
      }
    }
    return staff;
  }
  
  
  
   
}
