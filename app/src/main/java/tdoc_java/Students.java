package tdoc_java;

public class Students {
    private int ID;
    private String Name;
    private String Regd;
    private String Roll;

    public Students(int ID,String Name,String Regd,String Roll) {
        this.ID = ID;
        this.Name = Name;
        this.Regd = Regd;
        this.Roll =Roll;
    }

    public int getID() {
        return ID;
    }



    public String getName() {
        return Name;
    }



    public String getRegd() {
        return Regd;
    }


    public String getRoll() {
        return Roll;
    }

}
