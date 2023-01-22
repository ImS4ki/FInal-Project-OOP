import java.io.*;
import java.util.*;

public class Borrower extends MANUSIA
{
    private ArrayList<Loan> borrowedBooks;
    public Borrower(int id,String name, String address, int phoneNum)
    {
        super(id,name,address,phoneNum);
        borrowedBooks = new ArrayList();
    }


    // Printing Borrower's Info
    @Override
    public void printInfo()
    {
        super.printInfo();
        printBorrowedBooks();
    }

    public void printBorrowedBooks()
    {
        if (!borrowedBooks.isEmpty())
        {
            System.out.println("\nBorrowed Books are: ");

            System.out.println("------------------------------------------------------------------------------");
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");

            for (int i = 0; i < borrowedBooks.size(); i++)
            {
                System.out.print(i + "-" + "\t\t");
                borrowedBooks.get(i).getBook().printInfo();
                System.out.print("\n");
            }
        }
        else
            System.out.println("\nNo borrowed books.");
    }

    public void updateBorrowerInfo() throws IOException
    {
        String choice;

        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("\nDo you want to update " + getName() + "'s Name ? (y/n)");
        choice = sc.next();

        updateBorrowerName(choice, reader);


        System.out.println("\nDo you want to update " + getName() + "'s Address ? (y/n)");
        choice = sc.next();

        updateBorrowerAddress(choice, reader);

        System.out.println("\nDo you want to update " + getName() + "'s Phone Number ? (y/n)");
        choice = sc.next();

        updateBorrowerPhoneNumber(choice, sc);

        System.out.println("\nBorrower is successfully updated.");

    }

    private void updateBorrowerPhoneNumber(String choice, Scanner sc) {
        if(choice.equals("y"))
        {
            System.out.println("\nType New Phone Number: ");
            setNo_telp(sc.nextInt());
            System.out.println("\nThe phone number is successfully updated.");
        }
    }

    private void updateBorrowerAddress(String choice, BufferedReader reader) throws IOException {
        if(choice.equals("y"))
        {
            System.out.println("\nType New Address: ");
            setAddress(reader.readLine());
            System.out.println("\nThe address is successfully updated.");
        }
    }

    private void updateBorrowerName(String choice, BufferedReader reader) throws IOException {
        if(choice.equals("y"))
        {
            System.out.println("\nType New Name: ");
            setName(reader.readLine());
            System.out.println("\nThe name is successfully updated.");
        }
    }


    public void addBorrowedBook(Loan iBook)
    {
        borrowedBooks.add(iBook);
    }

    public void removeBorrowedBook(Loan iBook)
    {
        borrowedBooks.remove(iBook);
    }


    public ArrayList<Loan> getBorrowedBooks()
    {
        return borrowedBooks;
    }


}