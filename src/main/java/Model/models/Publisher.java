package Model.models;

public class Publisher {
    private String name;
    private String address;
    private String Telephone;

    public Publisher(String name,String address,String telephone){
        this.name=name;
        this.address=address;
        this.Telephone=telephone;
    }

    public String getPublishername() {
        return this.name;
    }
    public void setPublishername(String name) {
        this.name = name;
    }
    public String getAddress(){return this.address;}
    public void setAddress(String address){this.address = address;}
    public String getTelephone(){return this.Telephone;}
    public void setTelephone(String telephone){this.Telephone = telephone;}
}
