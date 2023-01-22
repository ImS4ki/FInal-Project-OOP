public class Clerk extends Staff {

    int deskNo;
    public static int currentdeskNumber = 0;

    public Clerk(int id, String name, String address,int phonenumber, double salary,int desknumber)
    {
        super(id,name,address,phonenumber,salary);

        if(deskNo == -1)
        {
            deskNo = currentdeskNumber;
        }
        else
        {
            deskNo=desknumber;
        }

        currentdeskNumber++;
    }

    @Override
    public void printInfo()
    {
        super.printInfo();
        System.out.println("Desk Number: " + deskNo);
    }
}
