public abstract class MANUSIA {
     protected int id;
     protected String password;
     protected String name;
     protected String address;
     protected int no_telp;

    static int currentIdNumber = 0;

    public MANUSIA(int idNum, String name, String address, int phoneNum)
    {
        currentIdNumber++;

        if(idNum==-1)
        {
            setId(currentIdNumber);
        }
        else
            setId(idNum);

        setPassword(Integer.toString(getId()));
        this.setName(name);
        this.setAddress(address);
        setNo_telp(phoneNum);
    }

    public void printInfo()
    {
        System.out.println("-----------------------------------------");
        System.out.println("\nThe details are: \n");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Phone No: " + no_telp + "\n");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(int no_telp) {
        this.no_telp = no_telp;
    }

    public static void setIDCount(int n)
    {
        currentIdNumber=n;
    }
}
