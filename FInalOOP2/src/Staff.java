public class Staff extends MANUSIA
{
    protected double salary;

    public Staff(int id, String name, String address, int phone_no, double salary)
    {
        super(id,name,address,phone_no);
        salary = salary;
    }

    @Override
    public void printInfo()
    {
        super.printInfo();
        System.out.println("Salary: " + salary + "\n");
    }

    public double getSalary()
    {
        return salary;
    }


}
