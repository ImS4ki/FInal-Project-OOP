import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;

public class Loan
{
    private Borrower borrower;
    private Buku book;

    private Staff issuer;
    private Date issuedDate;

    private Date dateReturned;
    private Staff receiver;

    private boolean finePaid;

    public Loan(Borrower bor, Buku book, Staff iss, Staff rec, Date issDate, Date recDate, boolean fPaid)  // Para cons.
    {
        borrower = bor;
        book = book;
        issuer = iss;
        receiver = rec;
        issuedDate = issDate;
        dateReturned = recDate;
        finePaid = fPaid;
    }



    public Buku getBook()
    {
        return book;
    }

    public Staff getIssuer()
    {
        return issuer;
    }

    public Staff getReceiver()
    {
        return receiver;
    }

    public Date getIssuedDate()
    {
        return issuedDate;
    }

    public Date getReturnDate()
    {
        return dateReturned;
    }

    public Borrower getBorrower()
    {
        return borrower;
    }

    public boolean getFineStatus()
    {
        return finePaid;
    }

    public void setReturnedDate(Date dReturned)
    {
        dateReturned = dReturned;
    }

    public void setFineStatus(boolean fStatus)
    {
        finePaid = fStatus;
    }

    public void setReceiver(Staff r)
    {
        receiver = r;
    }





    //Computes fine for a particular loan only
    public double computeFine1()
    {


        double totalFine = 0;

        if (!finePaid)
        {
            Date iDate = issuedDate;
            Date rDate = new Date();

            long days =  ChronoUnit.DAYS.between(rDate.toInstant(), iDate.toInstant());
            days=0-days;

            days = days - Library.getInstance().book_return_deadline;

            if(days>0)
                totalFine = days * Library.getInstance().per_day_fine;
            else
                totalFine=0;
        }
        return totalFine;
    }


    public void payFine()
    {


        double totalFine = computeFine1();

        if (totalFine > 0)
        {
            System.out.println("\nTotal Fine generated: Rs " + totalFine);

            System.out.println("Do you want to pay? (y/n)");

            Scanner input = new Scanner(System.in);

            String choice = input.next();

            if(choice.equals("y") || choice.equals("Y"))
                finePaid = true;

            if(choice.equals("n") || choice.equals("N"))
                finePaid = false;
        }
        else
        {
            System.out.println("\nNo fine is generated.");
            finePaid = true;
        }
    }



    public void renewIssuedBook(Date iDate)
    {
        issuedDate = iDate;

        System.out.println("\nThe deadline of the book " + getBook().getTitle() + " has been extended.");
        System.out.println("Issued Book is successfully renewed!\n");
    }













}