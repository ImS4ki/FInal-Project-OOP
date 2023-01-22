import java.io.*;
import java.util.*;
public class Buku {
    private int bookID;
     private String title;
     private String subject;
     private String author;
     private boolean isIssued;
    static int currentIdNumber = 0;

    public Buku(int id, String t, String s, String a, boolean issued){
        currentIdNumber++;
        if(id==-1)
        {
            bookID = currentIdNumber;
        }
        else
            bookID=id;

        title = t;
        subject = s;
        author = a;
        isIssued = issued;

    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public void printInfo()
    {
        System.out.println(title + "\t\t\t" + author + "\t\t\t" + subject);
    }
    public static void setIDCount(int n)
    {
        currentIdNumber = n;
    }
    public void changeBookInfo() throws IOException
    {
        Scanner scanner = new Scanner(System.in);
        String input;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\nUpdate Author? (y/n)");
        input = scanner.next();

        if(input.equals("y"))
        {
            System.out.println("\nEnter new Author: ");
            author = reader.readLine();
        }

        System.out.println("\nUpdate Subject? (y/n)");
        input = scanner.next();

        if(input.equals("y"))
        {
            System.out.println("\nEnter new Subject: ");
            subject = reader.readLine();
        }

        System.out.println("\nUpdate Title? (y/n)");
        input = scanner.next();

        if(input.equals("y"))
        {
            System.out.println("\nEnter new Title: ");
            title = reader.readLine();
        }

        System.out.println("\nBook is successfully updated.");

    }

    public void issueBook(Borrower borrower, Staff staff) {
        if (isIssued) {
            System.out.println("\nThe book " + title + " is already issued.");
        }

        else
        {
            setIssued(true);

            Loan iHistory = new Loan(borrower,this,staff,null,new Date(),null,false);

            Library.getInstance().addLoan(iHistory);
            borrower.addBorrowedBook(iHistory);

            System.out.println("\nThe book " + title + " is successfully issued to " + borrower.getName() + ".");
            System.out.println("\nIssued by: " + staff.getName());
        }
    }

    public void returnBook(Borrower borrower, Loan l, Staff staff)
    {
        l.getBook().setIssued(false);
        l.setReturnedDate(new Date());
        l.setReceiver(staff);

        borrower.removeBorrowedBook(l);

        l.payFine();

        System.out.println("\nThe book " + l.getBook().getTitle() + " is successfully returned by " + borrower.getName() + ".");
        System.out.println("\nReceived by: " + staff.getName());
    }
}
