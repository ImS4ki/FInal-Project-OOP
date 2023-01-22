import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Types;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Library {

    private String name;
    public static ArrayList <MANUSIA> persons;
    private ArrayList <Buku> booksInLibrary;

    private ArrayList <Loan> loans;

    public int book_return_deadline;
    public double per_day_fine;

    private static Library obj;

    public static Library getInstance()
    {
        if(obj==null)
        {
            obj = new Library();
        }

        return obj;
    }

    private Library()   // default cons.
    {
        name = null;
        persons = new ArrayList();

        booksInLibrary = new ArrayList();
        loans = new ArrayList();
    }




    public void setReturnDeadline(int deadline)
    {
        book_return_deadline = deadline;
    }

    public void setFine(double perDayFine)
    {
        per_day_fine = perDayFine;
    }



    // Setter Func.
    public void setName(String n)
    {
        name = n;
    }

    /*-----------Getter FUNCs.------------*/


    public ArrayList<MANUSIA> getPersons()
    {
        return persons;
    }

    public String getLibraryName()
    {
        return name;
    }

    public ArrayList<Buku> getBooks()
    {
        return booksInLibrary;
    }

    public void addClerk(Clerk c)
    {
        persons.add(c);
    }

    public void addBorrower(Borrower b)
    {
        persons.add(b);
    }


    public void addLoan(Loan l)
    {
        loans.add(l);
    }

    public Borrower findBorrower()
    {
        System.out.println("\nEnter Borrower's ID: ");

        int id = 0;

        Scanner scanner = new Scanner(System.in);

        try{
            id = scanner.nextInt();
        }
        catch (java.util.InputMismatchException e)
        {
            System.out.println("\nInvalid Input");
        }

        for (int i = 0; i < persons.size(); i++)
        {
            if (persons.get(i).getId() == id && persons.get(i).getClass().getSimpleName().equals("Borrower"))
                return (Borrower)(persons.get(i));
        }

        System.out.println("\nSorry this ID didn't match any Borrower's ID.");
        return null;
    }

    public Clerk findClerk()
    {
        System.out.println("\nEnter Clerk's ID: ");

        int id = 0;

        Scanner scanner = new Scanner(System.in);

        try{
            id = scanner.nextInt();
        }
        catch (java.util.InputMismatchException e)
        {
            System.out.println("\nInvalid Input");
        }

        for (int i = 0; i < persons.size(); i++)
        {
            if (persons.get(i).getId() == id && persons.get(i).getClass().getSimpleName().equals("Clerk"))
                return (Clerk)(persons.get(i));
        }

        System.out.println("\nSorry this ID didn't match any Clerk's ID.");
        return null;
    }


    public void addBookinLibrary(Buku b)
    {
        booksInLibrary.add(b);
    }

    public void removeBookfromLibrary(Buku b)
    {
        boolean delete = true;


        for (int i = 0; i < persons.size() && delete; i++)
        {
            if (persons.get(i).getClass().getSimpleName().equals("Borrower"))
            {
                ArrayList<Loan> borBooks = ((Borrower)(persons.get(i))).getBorrowedBooks();

                for (int j = 0; j < borBooks.size() && delete; j++)
                {
                    if (borBooks.get(j).getBook() == b)
                    {
                        delete = false;
                        System.out.println("This particular book is currently borrowed by some borrower.");
                    }
                }
            }
        }

        if (delete) {
            booksInLibrary.remove(b);
            System.out.println("The book is successfully removed.");
        }
        else
            System.out.println("\nDelete Unsuccessful.");
    }



    // Searching Books on basis of title, Subject or Author 
    public ArrayList<Buku> searchForBooks() throws IOException
    {
        String choice;
        String title = "", subject = "", author = "";

        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true)
        {
            System.out.println("\nEnter either '1' or '2' or '3' for search by Title, Subject or Author of Book respectively: ");
            choice = sc.next();

            if (choice.equals("1") || choice.equals("2") || choice.equals("3"))
                break;
            else
                System.out.println("\nWrong Input!");
        }

        if (choice.equals("1"))
        {
            System.out.println("\nEnter the Title of the Book: ");
            title = reader.readLine();
        }

        else if (choice.equals("2"))
        {
            System.out.println("\nEnter the Subject of the Book: ");
            subject = reader.readLine();
        }

        else
        {
            System.out.println("\nEnter the Author of the Book: ");
            author = reader.readLine();
        }

        ArrayList<Buku> matchedBooks = new ArrayList();

        //Retrieving all the books which matched the user's search query
        for(int i = 0; i < booksInLibrary.size(); i++)
        {
            Buku b = booksInLibrary.get(i);

            if (choice.equals("1"))
            {
                if (b.getTitle().equals(title))
                    matchedBooks.add(b);
            }
            else if (choice.equals("2"))
            {
                if (b.getSubject().equals(subject))
                    matchedBooks.add(b);
            }
            else
            {
                if (b.getAuthor().equals(author))
                    matchedBooks.add(b);
            }
        }

        //Printing all the matched Books
        if (!matchedBooks.isEmpty())
        {
            System.out.println("\nThese books are found: \n");

            System.out.println("------------------------------------------------------------------------------");
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");

            for (int i = 0; i < matchedBooks.size(); i++)
            {
                System.out.print(i + "-" + "\t\t");
                matchedBooks.get(i).printInfo();
                System.out.print("\n");
            }

            return matchedBooks;
        }
        else
        {
            System.out.println("\nSorry. No Books were found related to your query.");
            return null;
        }
    }



    // View Info of all Books in Library
    public void viewAllBooks()
    {
        if (!booksInLibrary.isEmpty())
        {
            System.out.println("\nBooks are: ");

            System.out.println("------------------------------------------------------------------------------");
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");

            for (int i = 0; i < booksInLibrary.size(); i++)
            {
                System.out.print(i + "-" + "\t\t");
                booksInLibrary.get(i).printInfo();
                System.out.print("\n");
            }
        }
        else
            System.out.println("\nCurrently, Library has no books.");
    }


    //Computes total fine for all loans of a borrower
    public double computeFine2(Borrower borrower)
    {
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("No.\t\tBook's Title\t\tBorrower's Name\t\t\tIssued Date\t\t\tReturned Date\t\t\t\tFine(Rs)");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        double totalFine = 0;
        double per_loan_fine = 0;

        for (int i = 0; i < loans.size(); i++)
        {
            Loan l = loans.get(i);

            if ((l.getBorrower() == borrower))
            {
                per_loan_fine = l.computeFine1();
                System.out.print(i + "-" + "\t\t" + loans.get(i).getBook().getTitle() + "\t\t\t" + loans.get(i).getBorrower().getName() + "\t\t" + loans.get(i).getIssuedDate() +  "\t\t\t" + loans.get(i).getReturnDate() + "\t\t\t\t" + per_loan_fine  + "\n");

                totalFine += per_loan_fine;
            }
        }

        return totalFine;
    }


    public void createPerson(char x)
    {
        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\nEnter Name: ");
        String n = "";
        try {
            n = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Enter Address: ");
        String address = "";
        try {
            address = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }

        int phone = 0;

        try{
            System.out.println("Enter Phone Number: ");
            phone = sc.nextInt();
        }
        catch (java.util.InputMismatchException e)
        {
            System.out.println("\nInvalid Input.");
        }


        if (x == 'c')
        {
            double salary = 0;

            try{
                System.out.println("Enter Salary: ");
                salary = sc.nextDouble();
            }
            catch (java.util.InputMismatchException e)
            {
                System.out.println("\nInvalid Input.");
            }

            Clerk c = new Clerk(-1,n,address,phone,salary,-1);
            addClerk(c);

            System.out.println("\nClerk with name " + n + " created successfully.");
            System.out.println("\nYour ID is : " + c.getId());
            System.out.println("Your Password is : " + c.getPassword());
        }


        else if (x == 'l')
        {
            double salary = 0;
            try{
                System.out.println("Enter Salary: ");
                salary = sc.nextDouble();
            }
            catch (java.util.InputMismatchException e)
            {
                System.out.println("\nInvalid Input.");
            }

        }


        else
        {
            Borrower b = new Borrower(-1,n,address,phone);
            addBorrower(b);
            System.out.println("\nBorrower with name " + n + " created successfully.");

            System.out.println("\nYour ID is : " + b.getId());
            System.out.println("Your Password is : " + b.getPassword());
        }
    }



    public void createBook(String title, String subject, String author)
    {
        Buku b = new Buku(-1,title,subject,author,false);

        addBookinLibrary(b);

        System.out.println("\nBook with Title " + b.getTitle() + " is successfully created.");
    }



    // Called when want an access to Portal
    public MANUSIA login()
    {
        Scanner input = new Scanner(System.in);

        int id = 0;
        String password = "";

        System.out.println("\nEnter ID: ");

        try{
            id = input.nextInt();
        }
        catch (java.util.InputMismatchException e)
        {
            System.out.println("\nInvalid Input");
        }

        System.out.println("Enter Password: ");
        password = input.next();

        for (int i = 0; i < persons.size(); i++)
        {
            if (persons.get(i).getId() == id && persons.get(i).getPassword().equals(password))
            {
                System.out.println("\nLogin Successful");
                return persons.get(i);
            }
        }
        System.out.println("\nSorry! Wrong ID or Password");
        return null;
    }



    public void viewHistory()
    {
        if (!loans.isEmpty())
        {
            System.out.println("\nIssued Books are: ");

            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("No.\tBook's Title\tBorrower's Name\t  Issuer's Name\t\tIssued Date\t\t\tReceiver's Name\t\tReturned Date\t\tFine Paid");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");

            for (int i = 0; i < loans.size(); i++)
            {
                if(loans.get(i).getIssuer()!=null)
                    System.out.print(i + "-" + "\t" + loans.get(i).getBook().getTitle() + "\t\t\t" + loans.get(i).getBorrower().getName() + "\t\t" + loans.get(i).getIssuer().getName() + "\t    " + loans.get(i).getIssuedDate());

                if (loans.get(i).getReceiver() != null)
                {
                    System.out.print("\t" + loans.get(i).getReceiver().getName() + "\t\t" + loans.get(i).getReturnDate() +"\t   " + loans.get(i).getFineStatus() + "\n");
                }
                else
                    System.out.print("\t\t" + "--" + "\t\t\t" + "--" + "\t\t" + "--" + "\n");
            }
        }
        else
            System.out.println("\nNo issued books.");
    }



    public Connection makeConnection()
    {
        try
        {
            String host = "jdbc:mysql://localhost/ProjekFInalOOP";
            String uName = "root";
            String uPass= "nickcakep";
            Connection con = DriverManager.getConnection( host, uName, uPass );
            return con;
        }
        catch ( SQLException err )
        {
            System.out.println( err.getMessage( ) );
            return null;
        }
    }



    public void populateLibrary(Connection con) throws SQLException, IOException
    {
        Library lib = this;
        Statement stmt = con.createStatement( );

        /* --- Populating Book ----*/
        String SQL = "SELECT * FROM buku_tbl";
        ResultSet rs = stmt.executeQuery( SQL );

        if(!rs.next())
        {
            System.out.println("\nNo Books Found in Library");
        }
        else
        {
            int maxID = 0;

            do
            {
                if(rs.getString("TITLE") !=null && rs.getString("AUTHOR")!=null && rs.getString("SUBJECT")!=null && rs.getInt("ID")!=0)
                {
                    String title=rs.getString("TITLE");
                    String author=rs.getString("AUTHOR");
                    String subject=rs.getString("SUBJECT");
                    int id= rs.getInt("ID");
                    boolean issue=rs.getBoolean("IS_ISSUED");
                    Buku b = new Buku(id,title,subject,author,issue);
                    addBookinLibrary(b);

                    if (maxID < id)
                        maxID = id;
                }
            }while(rs.next());


            Buku.setIDCount(maxID);
        }



        SQL="SELECT ID,PNAME,ADDRESS,PASSWORD,PHONE_NO,SALARY,DESK_NO FROM PERSON INNER JOIN CLERK ON ID=C_ID INNER JOIN STAFF ON S_ID=C_ID";

        rs=stmt.executeQuery(SQL);

        if(!rs.next())
        {
            System.out.println("No clerks Found in Library");
        }
        else
        {
            do
            {
                int id=rs.getInt("ID");
                String cname=rs.getString("PNAME");
                String adrs=rs.getString("ADDRESS");
                int phn=rs.getInt("PHONE_NO");
                double sal=rs.getDouble("SALARY");
                int desk=rs.getInt("DESK_NO");
                Clerk c = new Clerk(id,cname,adrs,phn,sal,desk);

                addClerk(c);
            }
            while(rs.next());

        }


        SQL="SELECT ID,PNAME,ADDRESS,PASSWORD,PHONE_NO,SALARY,OFFICE_NO FROM PERSON INNER JOIN LIBRARIAN ON ID=L_ID INNER JOIN STAFF ON S_ID=L_ID";

        rs=stmt.executeQuery(SQL);
        if(!rs.next())
        {
            System.out.println("No Librarian Found in Library");
        }
        else
        {
            do
            {
                int id=rs.getInt("ID");
                String lname=rs.getString("PNAME");
                String adrs=rs.getString("ADDRESS");
                int phn=rs.getInt("PHONE_NO");
                double sal=rs.getDouble("SALARY");
                int off=rs.getInt("OFFICE_NO");
            }while(rs.next());

        }



        SQL="SELECT ID,PNAME,ADDRESS,PASSWORD,PHONE_NO FROM PERSON INNER JOIN BORROWER ON ID=B_ID";

        rs=stmt.executeQuery(SQL);

        if(!rs.next())
        {
            System.out.println("No Borrower Found in Library");
        }
        else
        {
            do
            {
                int id=rs.getInt("ID");
                String name=rs.getString("PNAME");
                String adrs=rs.getString("ADDRESS");
                int phn=rs.getInt("PHONE_NO");

                Borrower b= new Borrower(id,name,adrs,phn);
                addBorrower(b);

            }while(rs.next());

        }



        SQL="SELECT * FROM LOAN";

        rs=stmt.executeQuery(SQL);
        if(!rs.next())
        {
            System.out.println("No Books Issued Yet!");
        }
        else
        {
            do {
                int borid = rs.getInt("BORROWER");
                int bokid = rs.getInt("BOOK");
                int iid = rs.getInt("ISSUER");
                Integer rid = (Integer) rs.getObject("RECEIVER");
                int rd = 0;
                Date rdate;

                Date idate = new Date(rs.getTimestamp("ISS_DATE").getTime());

                if (rid != null)    // if there is a receiver
                {
                    rdate = new Date(rs.getTimestamp("RET_DATE").getTime());
                    rd = (int) rid;
                } else {
                    rdate = null;
                }

                boolean fineStatus = rs.getBoolean("FINE_PAID");

                boolean set = true;

                Borrower bb = null;


                for (int i = 0; i < getPersons().size() && set; i++) {
                    if (getPersons().get(i).getId() == borid) {
                        set = false;
                        bb = (Borrower) (getPersons().get(i));
                    }
                }

                set = true;
                Staff s[] = new Staff[2];

                else
                {
                    for (int k = 0; k < getPersons().size() && set; k++) {
                        if (getPersons().get(k).getId() == iid && getPersons().get(k).getClass().getSimpleName().equals("Clerk")) {
                            set = false;
                            s[0] = (Clerk) (getPersons().get(k));
                        }
                    }
                }

                set = true;
                if (rid == null) {
                    s[1] = null;
                    rdate = null;
                } else {

                    set = true;

                    ArrayList<Buku> books = getBooks();

                    for (int k = 0; k < books.size() && set; k++) {
                        if (books.get(k).getBookID() == bokid) {
                            set = false;
                            Loan l = new Loan(bb, books.get(k), s[0], s[1], idate, rdate, fineStatus);
                            loans.add(l);
                        }
                    }

                }
                while (rs.next()) ;
            }



        SQL="SELECT * FROM bukuterpinjam_tbl";

        rs=stmt.executeQuery(SQL);
        if(!rs.next())
        {
            System.out.println("No Books on Hold Yet!");
        }
        else
        {
            do
            {
                int borid=rs.getInt("BORROWER");
                int bokid=rs.getInt("BOOK");
                Date off=new Date (rs.getDate("REQ_DATE").getTime());

                boolean set=true;
                Borrower bb =null;

                ArrayList<MANUSIA> persons = lib.getPersons();

                for(int i=0;i<persons.size() && set;i++)
                {
                    if(persons.get(i).getId()==borid)
                    {
                        set=false;
                        bb=(Borrower)(persons.get(i));
                    }
                }

                set=true;

                ArrayList<Buku> books = lib.getBooks();

        }



        // Borrowed Books
        SQL="SELECT ID,BOOK FROM PERSON INNER JOIN BORROWER ON ID=B_ID INNER JOIN BORROWED_BOOK ON B_ID=BORROWER ";

        rs=stmt.executeQuery(SQL);

        if(!rs.next())
        {
            System.out.println("No Borrower has borrowed yet from Library");
        }
        else
        {

            do
            {
                int id=rs.getInt("ID");
                int bid=rs.getInt("BOOK");

                Borrower bb=null;
                boolean set=true;
                boolean okay=true;

                for(int i=0;i<lib.getPersons().size() && set;i++)
                {
                    if(lib.getPersons().get(i).getClass().getSimpleName().equals("Borrower"))
                    {
                        if(lib.getPersons().get(i).getId()==id)
                        {
                            set =false;
                            bb=(Borrower)(lib.getPersons().get(i));
                        }
                    }
                }

                set=true;

                ArrayList<Loan> books = loans;

                for(int i=0;i<books.size() && set;i++)
                {
                    if(books.get(i).getBook().getBookID()==bid &&books.get(i).getReceiver()==null )
                    {
                        set=false;
                        Loan bBook= new Loan(bb,books.get(i).getBook(),books.get(i).getIssuer(),null,books.get(i).getIssuedDate(),null,books.get(i).getFineStatus());
                        bb.addBorrowedBook(bBook);
                    }
                }

            }while(rs.next());
        }

        ArrayList<MANUSIA> persons = lib.getPersons();


        int max=0;

        for(int i=0;i<persons.size();i++)
        {
            if (max < persons.get(i).getId())
                max=persons.get(i).getId();
        }

        MANUSIA.setIDCount(max);
    }



    public void fillItBack(Connection con) throws SQLException,SQLIntegrityConstraintViolationException
    {


        String template = "DELETE FROM LIBRARY.peminjamanbuku_tbl";
        PreparedStatement stmts = con.prepareStatement(template);

        stmts.executeUpdate();



        template = "DELETE FROM LIBRARY.bukuterpinjam_tbl";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();



        template = "DELETE FROM LIBRARY.bukuterpinjam_tbl";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();



        template = "DELETE FROM LIBRARY.Buku";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();



        template = "DELETE FROM LIBRARY.CLERK";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();


        template = "DELETE FROM LIBRARY.peminjam_tbl";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();



        template = "DELETE FROM LIBRARY.staff_tbl";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();


        template = "DELETE FROM LIBRARY.manusia_tbl";
        stmts = con.prepareStatement(template);

        stmts.executeUpdate();

        Library lib = this;


        for(int i=0;i<lib.getPersons().size();i++)
        {
            template = "INSERT INTO LIBRARY.PERSON (ID,PNAME,PASSWORD,ADDRESS,PHONE_NO) values (?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(template);

            stmt.setInt(1, lib.getPersons().get(i).getId());
            stmt.setString(2, lib.getPersons().get(i).getName());
            stmt.setString(3,  lib.getPersons().get(i).getPassword());
            stmt.setString(4, lib.getPersons().get(i).getAddress());
            stmt.setInt(5, lib.getPersons().get(i).getNo_telp());

            stmt.executeUpdate();
        }


        for(int i=0;i<lib.getPersons().size();i++)
        {
            if (lib.getPersons().get(i).getClass().getSimpleName().equals("Clerk"))
            {
                template = "INSERT INTO LIBRARY.STAFF (S_ID,TYPE,SALARY) values (?,?,?)";
                PreparedStatement stmt = con.prepareStatement(template);

                stmt.setInt(1,lib.getPersons().get(i).getId());
                stmt.setString(2,"Clerk");
                stmt.setDouble(3, ((Clerk)(lib.getPersons().get(i))).getSalary());

                stmt.executeUpdate();

                template = "INSERT INTO LIBRARY.CLERK (C_ID,DESK_NO) values (?,?)";
                stmt = con.prepareStatement(template);

                stmt.setInt(1,lib.getPersons().get(i).getId());
                stmt.setInt(2, ((Clerk)(lib.getPersons().get(i))).deskNo);

                stmt.executeUpdate();
            }

        }


        for(int i=0;i<lib.getPersons().size();i++)
        {
            if (lib.getPersons().get(i).getClass().getSimpleName().equals("Borrower"))
            {
                template = "INSERT INTO LIBRARY.BORROWER(B_ID) values (?)";
                PreparedStatement stmt = con.prepareStatement(template);

                stmt.setInt(1, lib.getPersons().get(i).getId());

                stmt.executeUpdate();
            }
        }

        ArrayList<Buku> books = lib.getBooks();

        /*Filling Book's Table*/
        for(int i=0;i<books.size();i++)
        {
            template = "INSERT INTO LIBRARY.BOOK (ID,TITLE,AUTHOR,SUBJECT,IS_ISSUED) values (?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(template);

            stmt.setInt(1,books.get(i).getBookID());
            stmt.setString(2,books.get(i).getTitle());
            stmt.setString(3, books.get(i).getAuthor());
            stmt.setString(4, books.get(i).getSubject());
            stmt.setBoolean(5, books.get(i).isIssued());
            stmt.executeUpdate();

        }

        /* Filling Loan Book's Table*/
        for(int i=0;i<loans.size();i++)
        {
            template = "INSERT INTO LIBRARY.LOAN(L_ID,BORROWER,BOOK,ISSUER,ISS_DATE,RECEIVER,RET_DATE,FINE_PAID) values (?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(template);

            stmt.setInt(1,i+1);
            stmt.setInt(2,loans.get(i).getBorrower().getId());
            stmt.setInt(3,loans.get(i).getBook().getBookID());
            stmt.setInt(4,loans.get(i).getIssuer().getId());
            stmt.setTimestamp(5,new java.sql.Timestamp(loans.get(i).getIssuedDate().getTime()));
            stmt.setBoolean(8,loans.get(i).getFineStatus());
            if(loans.get(i).getReceiver()==null)
            {
                stmt.setNull(6,Types.INTEGER);
                stmt.setDate(7,null);
            }
            else
            {
                stmt.setInt(6,loans.get(i).getReceiver().getId());
                stmt.setTimestamp(7,new java.sql.Timestamp(loans.get(i).getReturnDate().getTime()));
            }

            stmt.executeUpdate();

        }

    } /

}