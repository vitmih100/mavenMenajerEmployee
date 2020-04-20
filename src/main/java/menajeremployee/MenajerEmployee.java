package menajeremployee;
//import java.time.*;
//import java.time.*.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import enums.Gender;
import enums.EMenu;
import enums.EFRONT_END;
import enums.EBACK_END;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.jdom2.JDOMException;
import org.json.simple.parser.ParseException;


public class MenajerEmployee{

  //private List<Employee> staff = new ArrayList<>();

  public static void clrscr() {
    //System.out.print("\033[H\033[2J");
    //System.out.flush();
    //for(int i = 0; i < 80*300; i++) // Default Height of cmd is 300 and Default width is 80
    //    System.out.print("\b"); // Prints a backspace

    try{
      final String os = System.getProperty("os.name");
      //System.out.println("OS: " + os);

      if (os.contains("Windows")){
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        //Runtime.getRuntime().exec("cls");
      }
      else{
        System.out.print("\033[H\033[2J");
        System.out.flush();
        //Runtime.getRuntime().exec("clear");
      }
    }
    catch (final Exception e){
    //catch (IOException | InterruptedException ex) {
      //  Handle any exceptions.
    }

  }

  public static LocalDate getDateFromString(String stringDate,DateTimeFormatter format){
    LocalDate date = LocalDate.parse(stringDate,format);
    return date;
  }

  public static EMenu iEmployee(List<Employee> staff){

    EMenu Menu;
    EmployeeDb edb = new EmployeeDb();
    int i=0;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    Scanner in = new Scanner(System.in);
    Scanner inq = new Scanner(System.in);
    do{
      /////////clrscr();
      System.out.println("Introduce-ti numele muncitorului: ");
      String name = in.nextLine();
      System.out.println("Introduce-ti prenumele muncitorului: ");
      String surname = in.nextLine();
      //in.reset();
      Double salary = 0.0;
      while (salary<=0.0){
        System.out.println("Introduce-ti salariul muncitorului: ");
        try{
          salary = in.nextDouble();
        }
        catch(InputMismatchException e){
          System.out.println("Nu ati introdus salariul corect. Mai incerca-ti odata.");
        }
        if(salary<=0.0){
          System.out.println("Nu ati introdus salariul corect. Salariul trebuie sa fie un numar pozitiv. Mai incerca-ti odata.");
        }
      }
      in.nextLine();
      LocalDate hireDay = null;
      while (hireDay==null){
        System.out.println("Introduce-ti data angajarii muncitorului in formatul dd.mm.yyyy: ");
        String hireDayStr = in.nextLine();
        //System.out.println("hireDayStr="+hireDayStr);
        try{
          //System.out.println("hireDayStr= " + hireDayStr);
          hireDay = getDateFromString(hireDayStr, formatter);
          //System.out.println("hireDay="+hireDay);
        }
        catch(DateTimeParseException e){
          System.out.println("Nu ati introdus data corect. Mai incerca-ti odata.");
          //System.out.println("Exeption: " + e);
        }
      }
      LocalDate birthDay = null;
      while (birthDay==null){
        System.out.println("Introduce-ti data nasterii muncitorului in formatul dd.mm.yyyy: ");
        String birthDayStr = in.nextLine();
        try{
          //System.out.println("hireDayStr= " + hireDayStr);
          birthDay = getDateFromString(birthDayStr, formatter);
          //System.out.println("hireDay="+hireDay);
        }
        catch(DateTimeParseException e){
          System.out.println("Nu ati introdus data corect. Mai incerca-ti odata.");
          //System.out.println("Exeption: " + e);
        }
      }
      Gender gender=null;
      //String genderStr;
      while (gender==null){
        System.out.println("Introduce-ti genul muncitorului in formatul M/F ori MALE/FEMALE: ");
        String genderStr = in.nextLine();
        try{
          //gender = Gender.valueOf(genderStr);
          gender = Gender.valueOfIgnoreCase(genderStr);
        }
        catch(IllegalArgumentException e){
          try{
            gender = Gender.valueOf(genderStr);
          }
          catch(IllegalArgumentException ex){
            System.out.println("Nu ati introdus genul corect. Mai incerca-ti odata.");
            //System.out.println("Exeption: " + e);
          }
        }
        //if(gender==null){
        //  try{
        //    gender = Gender.valueOf(genderStr);
        //  }
        //  catch(IllegalArgumentException e){
        //    System.out.println("Nu ati introdus genul corect. Mai incerca-ti odata.");
        //  }
        //}
      }
      //staff[i] = new Employee(0,name,salary,hireDay,birthDay,gender);
      staff.add(new Employee(0,name,surname,salary,hireDay,birthDay,gender));
      
      //////edb.insert(new Employee(0,name,surname,salary,hireDay,birthDay,gender));
      
      i++;
      /////////clrscr();
      System.out.println("Pentru iesirea in meniul principal apasati butonul q. ");
    }while((int)inq.next().charAt(0)!=113);
    edb.insert(staff);
    return Menu=EMenu.Main_menu;
  }

  public static EMenu eEmployee(List<Employee> staff){

    boolean exist_nume=false;
    EMenu Menu;
    Scanner in = new Scanner(System.in);

    while(true){
      ///////clrscr();
      System.out.println("Introduceti numele muncitorului care doriti sa-l redactati: "); 
      String name = in.nextLine();

      //exist_nume=false;
      exist_nume=true;
      //Algoritm de cautare

      if(exist_nume == false){
        System.out.println("Muncitor cu asa nume nu exista.");
        System.out.println("q - exit edit student....");
        if((int)in.next().charAt(0)==113){
          break;
        }
        else{
          continue;
        }
      }
      else{
        break;
      }

    }

    if(exist_nume == true){
      return Menu=EMenu.Edit_menu_employee;
    }
    else {
      return Menu=EMenu.Main_menu;
    }
  }
  
  public static EMenu shEmployee(int numParametr,int saveToFile){
    List<Employee> staff = new ArrayList<>();
    EMenu Menu=EMenu.Show_menu_employee;
    EmployeeDb edb = new EmployeeDb();
    int i=0;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    Scanner in = new Scanner(System.in);
    Scanner inq = new Scanner(System.in);   
    do{
        
        switch(numParametr){
          case 0: 
            break;
          case 1: 
            break;
          case 2: 
            break;
          case 3: 
            System.out.println("Introduce-ti numele muncitorului: ");
            String name = in.nextLine();
            staff = edb.get(name,3);
            break;
          case 4: 
            System.out.println("Introduce-ti prenumele muncitorului: ");
            String surname = in.nextLine();
            staff = edb.get(surname,4);
            break;
          case 5: 
            Double salary = 0.0;
            while (salary<=0.0){
              System.out.println("Introduce-ti salariul muncitorului: ");
              try{
                salary = in.nextDouble();
              }
              catch(InputMismatchException e){
                System.out.println("Nu ati introdus salariul corect. Mai incerca-ti odata.");
              }
              if(salary<=0.0){
                System.out.println("Nu ati introdus salariul corect. Salariul trebuie sa fie un numar pozitiv. Mai incerca-ti odata.");
              }
            }
            in.nextLine();
            staff = edb.get(salary,5);
            break;
          case 6: 
            LocalDate hireDay = null;
            while (hireDay==null){
              System.out.println("Introduce-ti data angajarii muncitorului in formatul dd.mm.yyyy: ");
              String hireDayStr = in.nextLine();
              //System.out.println("hireDayStr="+hireDayStr);
              try{
                //System.out.println("hireDayStr= " + hireDayStr);
                hireDay = getDateFromString(hireDayStr, formatter);
                //System.out.println("hireDay="+hireDay);
              }
              catch(DateTimeParseException e){
                System.out.println("Nu ati introdus data corect. Mai incerca-ti odata.");
                //System.out.println("Exeption: " + e);
              }
            }
            staff = edb.get(hireDay,6);
            break;
          case 7: 
            LocalDate birthDay = null;
            while (birthDay==null){
              System.out.println("Introduce-ti data nasterii muncitorului in formatul dd.mm.yyyy: ");
              String birthDayStr = in.nextLine();
              try{
                //System.out.println("hireDayStr= " + hireDayStr);
                birthDay = getDateFromString(birthDayStr, formatter);
                //System.out.println("hireDay="+hireDay);
              }
              catch(DateTimeParseException e){
                System.out.println("Nu ati introdus data corect. Mai incerca-ti odata.");
                //System.out.println("Exeption: " + e);
              }
            }
            staff = edb.get(birthDay,7);
            break;
          case 8: 
            Gender gender=null;
            //String genderStr;
            while (gender==null){
              System.out.println("Introduce-ti genul muncitorului in formatul M/F ori MALE/FEMALE: ");
              String genderStr = in.nextLine();
              try{
                //gender = Gender.valueOf(genderStr);
                gender = Gender.valueOfIgnoreCase(genderStr);
              }
              catch(IllegalArgumentException e){
                try{
                  gender = Gender.valueOf(genderStr);
                }
                catch(IllegalArgumentException ex){
                  System.out.println("Nu ati introdus genul corect. Mai incerca-ti odata.");
                  //System.out.println("Exeption: " + e);
                }
              }
            }
            staff = edb.get(gender,8);
            break;
        }
        
          if(saveToFile==0){  
            for (Employee emp : staff) {
              System.out.println("Id-ul " + emp.getId());  
              System.out.println("Numele " + emp.getName()); 
              System.out.println("Prenumele " + emp.getsurName()); 
              System.out.println("Salariul " + emp.getSalary());
              System.out.println("Data angajarii " + emp.getHireDay());
              System.out.println("Data nasterii " + emp.getBirthDay());
              System.out.println("Genul " + emp.getGender()); 
              Menu=EMenu.Show_menu_employee;
            }
          } else if(saveToFile==1){
              XmlIOService xmlFile = new XmlIOService();
              try {
                xmlFile.writeToFile(staff);
              }
              catch(IOException ex){
                System.out.println("Datele nu au fost salvate in fisier ");
              }
              Menu=EMenu.Save_file_XML;
          } else if(saveToFile==2){
              ConvertingToJson jsonFile = new ConvertingToJson();
              try{
                jsonFile.writeToJsonFile(staff);
              }
              catch(IOException ex){
                System.out.println("Datele nu au fost salvate in fisier ");
              }
              Menu=EMenu.Save_file_JSON;
          }
  
        System.out.println("Pentru iesirea in meniul principal apasati butonul q. ");
    }while((int)inq.next().charAt(0)!=113);
    return Menu;
  }
  
  public static EMenu readEmployeeFile(int numParametr){
    List<Employee> staff = new ArrayList<>();
    EMenu Menu=EMenu.Main_menu;
    Scanner in = new Scanner(System.in);
    
    if(numParametr == 1){
        XmlIOService xmlRead = new XmlIOService();
        try{
          staff=xmlRead.readFromFile();
        }
        catch(JDOMException|IOException ex){
          System.out.println("Datele nu au fost citite din fisier ");
        }
        
    
    } else if(numParametr == 2){
        ConvertingToJson jsonRead = new ConvertingToJson();
        try{
          staff=jsonRead.convertFromJson();
        }
        catch (IOException e) {
          System.out.println("Could not read JSON. Reason: " + e.getMessage());
        } 
        catch (ParseException e) {
          System.out.println("Could not parse JSON. Reason: " + e.getMessage());
        }
    
    }    
    System.out.println("Pentru pentru salvarea datelor in baza de date apasati butonul y. ");
    String saveDb = in.nextLine();
    if(saveDb.charAt(0)=='y' || saveDb.charAt(0)=='Y'){
      EmployeeDb edb = new EmployeeDb();
      edb.insert(staff);
    }   
    
    return Menu;
  }
  
  public static void main(String[] args){

    List<Employee> staff = new ArrayList<>();
    EMenu Menu;
    ///////clrscr();
    int numParametr=0;        
    
    meniulPrincipal meniulPrin = new meniulPrincipal();
    while (true){

      switch(meniulPrin.getFRONT_END()){
        case MENIUL: meniulPrin.meniul(); break;
      }

      switch(meniulPrin.getBACK_END()){
        case InsertEmployee: 
               Menu=iEmployee(staff);
               meniulPrin.setMenu(Menu);
               break;
           /*try {
             TimeUnit.SECONDS.sleep(1);
           } catch (InterruptedException e) {
             e.printStackTrace();
           }; break; */
        case EditEmployee: 
               Menu=eEmployee(staff);
               meniulPrin.setMenu(Menu);
               break;
        case ShowEmployee: 
               Menu=EMenu.Show_menu_employee;
               meniulPrin.setMenu(Menu);
               break;
 
        case ShowNume: 
               numParametr=3;
               Menu=shEmployee(numParametr,0);
               meniulPrin.setMenu(Menu);
               break; 
        case ShowPrenume: 
               numParametr=4;
               Menu=shEmployee(numParametr,0);
               meniulPrin.setMenu(Menu);
               break;  
        case ShowSalariu: 
               numParametr=5;
               Menu=shEmployee(numParametr,0);
               meniulPrin.setMenu(Menu);
               break;
        case ShowDataAngaj: 
               numParametr=6;
               Menu=shEmployee(numParametr,0);
               meniulPrin.setMenu(Menu);
               break;
        case ShowDataNast: 
               numParametr=7;
               Menu=shEmployee(numParametr,0);
               meniulPrin.setMenu(Menu);
               break;
        case ShowGenul: 
               numParametr=8;
               Menu=shEmployee(numParametr,0);
               meniulPrin.setMenu(Menu);
               break;

        case SaveFileXML: 
               Menu=EMenu.Save_file_XML;
               meniulPrin.setMenu(Menu);
               break;

        case SaveFileXMLNume: 
               numParametr=3;
               Menu=shEmployee(numParametr,1);
               meniulPrin.setMenu(Menu);
               break;              

        case SaveFileXMLPrenume: 
               numParametr=4;
               Menu=shEmployee(numParametr,1);
               meniulPrin.setMenu(Menu);
               break;                  

        case SaveFileXMLSalariu: 
               numParametr=5;
               Menu=shEmployee(numParametr,1);
               meniulPrin.setMenu(Menu);
               break;   
        case SaveFileXMLDataAngaj: 
               numParametr=6;
               Menu=shEmployee(numParametr,1);
               meniulPrin.setMenu(Menu);
               break;   
 
        case SaveFileXMLDataNast: 
               numParametr=7;
               Menu=shEmployee(numParametr,1);
               meniulPrin.setMenu(Menu);
               break;   
        case SaveFileXMLGenul: 
               numParametr=8;
               Menu=shEmployee(numParametr,1);
               meniulPrin.setMenu(Menu);
               break;   
               
        case SaveFileJSON: 
               Menu=EMenu.Save_file_JSON;
               meniulPrin.setMenu(Menu);
               break;               

        case SaveFileJSONNume: 
               numParametr=3;
               Menu=shEmployee(numParametr,2);
               meniulPrin.setMenu(Menu);
               break;              

        case SaveFileJSONPrenume: 
               numParametr=4;
               Menu=shEmployee(numParametr,2);
               meniulPrin.setMenu(Menu);
               break;                  

        case SaveFileJSONSalariu: 
               numParametr=5;
               Menu=shEmployee(numParametr,2);
               meniulPrin.setMenu(Menu);
               break;   
        case SaveFileJSONDataAngaj: 
               numParametr=6;
               Menu=shEmployee(numParametr,2);
               meniulPrin.setMenu(Menu);
               break;   
 
        case SaveFileJSONDataNast: 
               numParametr=7;
               Menu=shEmployee(numParametr,2);
               meniulPrin.setMenu(Menu);
               break;   
        case SaveFileJSONGenul: 
               numParametr=8;
               Menu=shEmployee(numParametr,2);
               meniulPrin.setMenu(Menu);
               break;   

        case ReadFileXML: 
               numParametr=1;
               Menu=readEmployeeFile(numParametr);
               meniulPrin.setMenu(Menu);
               break;   

        case ReadFileJSON:
               numParametr=2;
               Menu=readEmployeeFile(numParametr);
               meniulPrin.setMenu(Menu);
               break; 

        //case ShowEmployee: sEmployee(staff); break;*/
        case End_execution: System.exit(0); break;

      }
        //*BACK_END=Not_exist;^M
  }

  }

}