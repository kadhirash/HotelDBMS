/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;


import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class DBProject {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of DBProject
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public DBProject (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end DBProject

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
   if(outputHeader){
      for(int i = 1; i <= numCol; i++){
    System.out.print(rsmd.getColumnName(i) + "\t");
      }
      System.out.println();
      outputHeader = false;
   }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            DBProject.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if
      
      Greeting();
      DBProject esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the DBProject object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new DBProject (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
        System.out.println("MAIN MENU");
        System.out.println("---------");
        System.out.println("1. Add new customer");
        System.out.println("2. Add new room");
        System.out.println("3. Add new maintenance company");
        System.out.println("4. Add new repair");
        System.out.println("5. Add new Booking"); 
        System.out.println("6. Assign house cleaning staff to a room");
        System.out.println("7. Raise a repair request");
        System.out.println("8. Get number of available rooms");
        System.out.println("9. Get number of booked rooms");
        System.out.println("10. Get hotel bookings for a week");
        System.out.println("11. Get top k rooms with highest price for a date range");
        System.out.println("12. Get top k highest booking price for a customer");
        System.out.println("13. Get customer total cost occurred for a give date range"); 
        System.out.println("14. List the repairs made by maintenance company");
        System.out.println("15. Get top k maintenance companies based on repair count");
        System.out.println("16. Get number of repairs occurred per year for a given hotel room");
        System.out.println("17. < EXIT");

            switch (readChoice()){
           case 1: addCustomer(esql); break;
           case 2: addRoom(esql); break;
           case 3: addMaintenanceCompany(esql); break;
           case 4: addRepair(esql); break;
           case 5: bookRoom(esql); break;
           case 6: assignHouseCleaningToRoom(esql); break;
           case 7: repairRequest(esql); break;
           case 8: numberOfAvailableRooms(esql); break;
           case 9: numberOfBookedRooms(esql); break;
           case 10: listHotelRoomBookingsForAWeek(esql); break;
           case 11: topKHighestRoomPriceForADateRange(esql); break;
           case 12: topKHighestPriceBookingsForACustomer(esql); break;
           case 13: totalCostForCustomer(esql); break;
           case 14: listRepairsMade(esql); break;
           case 15: topKMaintenanceCompany(esql); break;
           case 16: numberOfRepairsForEachRoomPerYear(esql); break;
           case 17: keepon = false; break;
           default : System.out.println("Unrecognized choice!"); break;
            }//end switch
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main
   
   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface                       \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   
  public static void addCustomer(DBProject esql){
    // Given customer details add the customer in the DB 


      /* customerID Numeric
             fName CHAR(30) NOT NULL,
             lName CHAR(30) NOT NULL,
             Address TEXT,
             phNo Numeric,
             DOB Date,
             gender GenderType,*/

      int customerID; // Assuming customerId will only be integers based on the given CSV file
      String fname,lname, addr, dob, gender;
      double phNo; // to allow for numbers up to 999-999-9999
      
      //customerID
      do{
          System.out.print("Enter Customer ID: ");
          try{
              customerID = Integer.parseInt(in.readLine());
              break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);

      // fname
      do{
          System.out.print("Enter Customer first name: ");
          try{ 
            fname = in.readLine();
            if ( (fname.length() <= 0 || fname.length() > 30) ) {
              throw new RuntimeException("Invalid input. Customer's first  name can't be empty, negative, or excheed 30 characters.");
            }
            break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);

      // lname
      do{
          System.out.print("Enter Customer last name: ");
          try{ 
            lname = in.readLine();
            if ((lname.length() <= 0 || lname.length() > 30) ) {
              throw new RuntimeException("Invalid input. Customer's last name can't be empty, negative, or excheed 30 characters.");
            }
            break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);

      //address
      do{
          System.out.print("Enter Customer's Address: ");
          try{
            addr = in.readLine();
            break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);

       //phNo
      do{
          System.out.print("Enter Customer Phone Number: ");
          try{
              phNo = Integer.parseInt(in.readLine());
              break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);

      // dob
      do{
        System.out.print("Enter Customers DOB (MM/DD/YYYY): ");
        try{
          dob = in.readLine();
          if(dob.length() < 0 || dob.length() > 10){
            throw new RuntimeException("Invalid DOB. DOB can't be that value, please enter up to 10 values including '/'' ");
          }
         /* month = dob.substring(0,2);
          if(month.indexOf())*/
          break;
        }
        catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);

      //gender
      do{
        System.out.print("Enter Customer's gender: ");
        try{
          gender = in.readLine();
          if (!(gender.equals("Male") || gender.equals("Female") || gender.equals("Other"))){
              throw new RuntimeException("Invalid input. Customer's gender can only be Male, Female, Other.");
          }
          break;
      }
      catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
        }
      }while(true);

      try{
        String query = "INSERT INTO Customer(customerID, fname, lName, Address, phNo, DOB, gender) VALUES (" + customerID + ", \'"  + fname + "\', \'"  + lname + "\', \'" + addr + "\', \'" + phNo + "\', \'" + dob + "\', \'" + gender + "\' );";
        esql.executeUpdate(query);
      }
      catch(Exception e){
        System.err.print("Query failed: " + e.getMessage());
      }

   }//end addCustomer*/

   public static void addRoom(DBProject esql){
    // Given room details add the room in the DB
    int HotelID;
    int roomNum;
    String roomtype;

    // HotelID:
    do{   
       System.out.print("Enter Hotel ID: ");
       try{
           HotelID = Integer.parseInt(in.readLine());
           break;
       }
       catch(Exception e){
          System.out.print("Input is invalid. " + e.getMessage());
          continue;
       }
    }while(true);

    do{
       System.out.print("Enter Room Number: ");
       try{
          roomNum = Integer.parseInt(in.readLine());
          break;
       }
       catch(Exception e){
          System.out.print("Input is invalid. " + e.getMessage());
          continue;
       }
    }while(true);

    do{
       System.out.print("Enter Room Type: ");
       try{
          roomtype = in.readLine();
          if(roomtype.length() <= 0 || roomtype.length() > 10){
             throw new RuntimeException("Invalid input. Room type can't be empty, negative, or exceed 10 characters.");
          }
          break;
       }
       catch(Exception e){
          System.out.print("Input is invalid. " + e.getMessage());
          continue;
       }
    }while(true);
    try{
       String query = "INSERT INTO Room (hotelID, roomNo, roomType) VALUES (" + HotelID + ", \'" + roomNum +"\', \'" + roomtype + "\')";
       esql.executeQuery(query);
    }
    catch(Exception e){
       System.err.println(e.getMessage());
    }
   }//end addRoom

   public static void addMaintenanceCompany(DBProject esql){
      // Given maintenance Company details add the maintenance company in the DB
      
       /* cmpID Numeric NOT NULL,
        name CHAR(30) NOT NULL,
        address TEXT,
        isCertified Boolean NOT NULL*/

        int cmpID;
        String name;
        String addr;
       Boolean isCertified;

        //cmpID
         do{
          System.out.print("Enter Maintenance Company ID: ");
          try{
              cmpID = Integer.parseInt(in.readLine());
              break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);
        //name
         do{
          System.out.print("Enter Maintenance Company name: ");
          try{ 
            name = in.readLine();
            if ( name.length() <= 0 || name.length() > 30) {
              throw new RuntimeException("Invalid input. Maintenance Company name can't be empty, negative, or exceed 30 characters.");
            }
            break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);
      //address
      do{
          System.out.print("Enter Maintenance Company Address: ");
          try{
            addr = in.readLine();
            break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);
      //isCertified

      do{
        System.out.print("Enter Maintenance Company certification value: ");
        try{
          String cert;
          cert = in.readLine();
          if(cert.equals("TRUE")){
            isCertified = true;
          }else if (cert.equals("FALSE")){
            isCertified = false;
          }else{
            throw new RuntimeException("Invalid input.");
          }
          break;
        }
        catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);
       try{
        String query = "INSERT INTO MaintenanceCompany(cmpID, name, address, isCertified) VALUES (" + cmpID + ", \'" + name + "\', \'" + addr + "\',\'" + isCertified + "\' );";
        //String query = "INSERT INTO MaintenanceCompany(cmpID, name, address, isCertified) VALUES (" + cmpID + "," + name + "," + addr + "," + isCertified +);";
         esql.executeUpdate(query);
      }
      catch(Exception e){
        System.err.print("Query failed: " + e.getMessage());
      }

   }//end addMaintenanceCompany

   public static void addRepair(DBProject esql){
    // Given repair details add repair in the DB
    /*
      rID Numeric NOT NULL,
      hotelID Numeric NOT NULL DEFAULT 0,
      roomNo Numeric NOT NULL DEFAULT 0,
      mCompany Numeric NOT NULL DEFAULT 0,
      repairDate Date NOT NULL,
      description TEXT,
      repairType CHAR(10),
    */
      int rID;
      int HotelID;
      int roomNo;
      int mCompany;
      Date repairDate;
      String description;
      String repairType;

      do{
         System.out.print("Enter Repair ID: ");
         try{
             rID = Integer.parseInt(in.readLine());
             break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      do{
         System.out.print("Enter Hotel ID: ");
         try{
             HotelID = Integer.parseInt(in.readLine());
             break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);
      
      do{
         System.out.print("Enter Room Number: ");
         try{
            roomNo = Integer.parseInt(in.readLine());
            break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      do{
         System.out.print("Enter Maintenance Company: ");
         try{
            mCompany = Integer.parseInt(in.readLine());
            break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      do{
        System.out.print("Enter Repair Date: ");
        try{
           SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
           repairDate = dateFormat.parse(in.readLine());
           break;
        }
        catch(Exception e){
           System.out.print("Input is invalid. " + e.getMessage());
           continue;
        }
      }while(true);

      do{
          System.out.print("Enter repair description: ");
          try{
            description = in.readLine();
            break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);

      do{
         System.out.print("Enter Repair Type: ");
         try{
            repairType = in.readLine();
            if(!(repairType.equals("Small") || repairType.equals("Medium") || repairType.equals("Large"))) {
               throw new RuntimeException("Not a valid repair type.");
            }
            break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      try{
         String query = "INSERT INTO Repair(rID, hotelID, roomNo, mCompany, repairDate, description, repairType) VALUES (" + rID + ", \'" + HotelID + "\',\'" + roomNo  + "\',\'" + mCompany + "\',\'" + repairDate + "\',\'" + description + "\',\'" + repairType + "\' );";
         esql.executeQuery(query);
      }
      catch(Exception e){
         System.err.println(e.getMessage());
      }
   }//end addRepair

   public static void bookRoom(DBProject esql){
    // Given hotelID, roomNo and customer Name create a booking in the DB 
      /*
      bID Numeric NOT NULL,
      customer Numeric NOT NULL DEFAULT 0,
      hotelID Numeric NOT NULL DEFAULT 0,
      roomNo Numeric NOT NULL DEFAULT 0,
      bookingDate Date NOT NULL,
      noOfPeople Numeric,
      price Numeric(6,2) NOT NULL,
      */
      int bID, customer, hotelID, roomNo, noOfPeople;
      double price;
      String bookingDate;

       //bID
         do{
          System.out.print("Enter Booking ID: ");
          try{
              bID = Integer.parseInt(in.readLine());
              break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);
      
       //customer
         do{
          System.out.print("Enter Booking customer: ");
          try{
              customer = Integer.parseInt(in.readLine());
              break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);


       //hotelID
         do{
          System.out.print("Enter Booking hotel ID: ");
          try{
              hotelID = Integer.parseInt(in.readLine());
              break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);


      //roomNo
         do{
          System.out.print("Enter Booking roomNo: ");
          try{
              roomNo = Integer.parseInt(in.readLine());
              break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);

      //bookingDate
      do{
          System.out.print("Enter Booking date (MM/DD/YYYY: ");
          try{
            //SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/YYYY");
            bookingDate = in.readLine();

            if(bookingDate.length() < 0 || bookingDate.length() > 10){
              throw new RuntimeException("Invalid booking date. Booking date can't be that value, please enter up to 10 values including '/'' ");
            }
            break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);


       //noOfPeople
         do{
          System.out.print("Enter Booking number of people: ");
          try{
              noOfPeople = Integer.parseInt(in.readLine());
              break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);


       //price
         do{
          System.out.print("Enter Booking price: ");
          try{
              price= Integer.parseInt(in.readLine());
              if(price < 0 || price > 999999.99){
                  throw new RuntimeException("Invalid price. Price can't be negative or greater than 999999.99");
              }
              break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);

      try{
        String query = "INSERT INTO Booking(bID, hotelID, roomNo, bookingDate, noOfPeople, price) VALUES (" + bID+ ", \'" + hotelID + "\', \'" + roomNo + "\',\'" + bookingDate + "\',\'"  + noOfPeople + "\', \'" + price + "\');";
        //String query = "INSERT INTO MaintenanceCompany(cmpID, name, address, isCertified) VALUES (" + cmpID + "," + name + "," + addr + "," + isCertified +);";
         esql.executeUpdate(query);
      }
      catch(Exception e){
        System.err.print("Query failed: " + e.getMessage());
      }

   }//end bookRoom

   public static void assignHouseCleaningToRoom(DBProject esql){
    // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
      int asgID;
      int SSN;
      int HotelID;
      int roomNum;

      do{
         System.out.print("Enter Assignment ID: ");
         try{
             asgID = Integer.parseInt(in.readLine());
             break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      do{
         System.out.print("Enter Staff SSN: ");
         try{
             SSN = Integer.parseInt(in.readLine());
             break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      do{
         System.out.print("Enter Hotel ID: ");
         try{
             HotelID = Integer.parseInt(in.readLine());
             break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      do{
         System.out.print("Enter Room Number: ");
         try{
            roomNum = Integer.parseInt(in.readLine());
            break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      try{
         String query = "INSERT INTO Assigned(asgID, staffID, hotelID, roomNo) VALUES (" + asgID + ",\'" + SSN + "\',\'" + HotelID + "\',\'" + roomNum + "\' );";
         esql.executeQuery(query);
      }
      catch(Exception e){
         System.err.println(e.getMessage());
      }
   }//end assignHouseCleaningToRoom
   
   public static void repairRequest(DBProject esql){ 
    // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
      
      //int hotelID, SSN, roomNo,rID;
      //String repairDate;
      
      int reqID,managerID, repairID;
      String requestDate, description;

      //reqID
       do{
          System.out.print("Enter Request ID: ");
          try{
              reqID = Integer.parseInt(in.readLine());
              break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);


      //managerID
       do{
          System.out.print("Enter Manager ID: ");
          try{
              managerID= Integer.parseInt(in.readLine());
              break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);


      //repairID
       do{
          System.out.print("Enter Request repair ID: ");
          try{
              repairID = Integer.parseInt(in.readLine());
              break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);

      //requestDate
       do{
          System.out.print("Enter Request Date(MM/DD/YYYY): ");
          try{
              requestDate = in.readLine();
              if(requestDate.length() < 0 || requestDate.length() > 10){
              throw new RuntimeException("Invalid request date. Request date can't be that value, please enter up to 10 values including '/'' ");
            }
            break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);

      
      //description
      do{
          System.out.print("Enter Request description: ");
          try{
            description = in.readLine();
            break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);

      try{
        String query = "INSERT INTO Request(reqID, managerID, repairID, requestDate, description) VALUES (" + reqID+ ", \'" + managerID + "\', \'" + repairID + "\',\'" + requestDate + "\',\'"  + description + "\');";
         esql.executeUpdate(query);
      }
      catch(Exception e){
        System.err.print("Query failed: " + e.getMessage());
      }

   }//end repairRequest
   
   public static void numberOfAvailableRooms(DBProject esql){
    // Given a hotelID, get the count of rooms available 
      int hotelID;

      do{
         System.out.print("Enter Hotel ID: ");
         try{
             hotelID = Integer.parseInt(in.readLine());
             break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);
      try{
         String query = "SELECT COUNT(*) FROM Room r WHERE r.hotelID='" + hotelID + "' AND r.roomNo IN (SELECT b.roomNo FROM Booking b)";
         esql.executeQuery(query);
      }
      catch(Exception e){
         System.err.println(e.getMessage());
      }
   }//end numberOfAvailableRooms

   
  public static void numberOfBookedRooms(DBProject esql){ // THIS ONE
    // Given a hotelID, get the count of rooms booked
      int hotelID;
      String date; 
      //hotelID
         do{
          System.out.print("Enter hotel ID: ");
          try{
              hotelID = Integer.parseInt(in.readLine());
              break;
          }
          catch(Exception e){
              System.out.print("Input is invalid. " + e.getMessage());
              continue;
          }
      }while(true);

      try{
        //String query = "SELECT FROM *;";
        //String query = "INSERT INTO MaintenanceCompany(cmpID, name, address, isCertified) VALUES (" + cmpID + "," + name + "," + addr + "," + isCertified +);";
         //esql.executeUpdate(query);
      }
      catch(Exception e){
        System.err.print("Query failed: " + e.getMessage());
      }
      

   }//end numberOfBookedRooms
   
   public static void listHotelRoomBookingsForAWeek(DBProject esql){
    // Given a hotelID, date - list all the rooms available for a week(including the input date) 
      int hotelID;
      Date date;
      String newDate;
      do{
         System.out.print("Enter Hotel ID: ");
         try{
             hotelID = Integer.parseInt(in.readLine());
             break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

       do{
        System.out.print("Enter Date: ");
        try{
           SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
           date = dateFormat.parse(in.readLine());
           Calendar c = Calendar.getInstance();
           c.setTime(date);
           c.add(Calendar.DATE, 7);
           newDate = dateFormat.format(c.getTime());
           break;
        }
        catch(Exception e){
           System.out.print("Input is invalid. " + e.getMessage());
           continue;
        }
      }while(true);

       try{
         String query = "SELECT * FROM Room r WHERE r.hotelID='" + hotelID + "' AND r.roomNo NOT IN (SELECT b.roomNo FROM Booking b WHERE b.hotelID='" + hotelID + "' AND b.bookingDate BETWEEN'" + date +"' AND '" + newDate + "');";
         esql.executeQuery(query);
      }
      catch(Exception e){
         System.err.println(e.getMessage());
      }
   }//end listHotelRoomBookingsForAWeek
   
   public static void topKHighestRoomPriceForADateRange(DBProject esql){ // THIS ONE 
    // List Top K Rooms with the highest price for a given date range 
      // Your code goes here.
      // ...
      // ...
   }//end topKHighestRoomPriceForADateRange
   
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
    // Given a customer Name, List Top K highest booking price for a customer 
      String fName;
      String lName;
      int k;

      do{
         System.out.print("Enter Customer First Name: ");
         try{
            fName = in.readLine();
            if(fName.length() <= 0 || fName.length() > 30){
               throw new RuntimeException("Invalid input. First Name can't be empty, negative, or exceed 30 characters.");
            }
            break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      do{
         System.out.print("Enter Customer Last Name: ");
         try{
            lName = in.readLine();
            if(lName.length() <= 0 || lName.length() > 30){
               throw new RuntimeException("Invalid input. Last Name can't be empty, negative, or exceed 30characters.");
            }
            break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      do{
         System.out.print("Enter k value: ");
         try{
             k = Integer.parseInt(in.readLine());
             break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      try{
         String query = "SELECT b.price FROM Customer c, Booking b WHERE c.fName='" + fName + "' AND c.lName='" + lName + "' AND c.customerID=b.customer ORDER BY b.price DESC LIMIT '" + k + "';";
         esql.executeQuery(query);
      }
      catch(Exception e){
         System.err.println(e.getMessage());
      }
   }//end topKHighestPriceBookingsForACustomer
   
   public static void totalCostForCustomer(DBProject esql){  // THIS ONE
    // Given a hotelID, customer Name and date range get the total cost incurred by the customer
      // Your code goes here.
      // ...
      // ...
   }//end totalCostForCustomer
   
   public static void listRepairsMade(DBProject esql){
    // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
      String name;

      do{
         System.out.print("Enter Maintenance Company: ");
         try{
            name = in.readLine();
            break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      try{
         String query = "SELECT r.description, r.repairType, o.hotelID, o.roomNo FROM Repair r, Room o, MaintenanceCompany m WHERE m.name='" + name + "'AND r.mCompany=m.cmpID AND r.roomNo=o.roomNo AND r.hotelID=o.hotelID;";
         esql.executeQuery(query);
      }
      catch(Exception e){
         System.err.println(e.getMessage());
      }
   }//end listRepairsMade
   
   public static void topKMaintenanceCompany(DBProject esql){ // THIS ONE 
    // List Top K Maintenance Company Names based on total repair count (descending order)
      // Your code goes here.
      // ...
      // ...
   }//end topKMaintenanceCompany
   
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
    // Given a hotelID, roomNo, get the count of repairs per year
      int hotelID;
      int roomNo;

      do{
         System.out.print("Enter Hotel ID: ");
         try{
             hotelID = Integer.parseInt(in.readLine());
             break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      do{
         System.out.print("Enter Room Number: ");
         try{
            roomNo = Integer.parseInt(in.readLine());
            break;
         }
         catch(Exception e){
            System.out.print("Input is invalid. " + e.getMessage());
            continue;
         }
      }while(true);

      try{
         String query = "SELECT EXTRACT (year FROM r.repairDate) as \"Year\", COUNT(r.rid) FROM Repair r WHERE r.hotelID='" + hotelID + "' AND r.roomNo='" + roomNo + "' GROUP BY \"Year\" ORDER BY COUNT ASC;";
         esql.executeQuery(query);
      }
      catch(Exception e){
         System.err.println(e.getMessage());
      }
   }//end listRepairsMade

}//end DBProject
