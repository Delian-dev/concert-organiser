package models;

public class Client {
    private int clientId;
    private String username;
    private int age;
    private String email;
    private String phone;

    public Client(String username , int age, String email, String phone){
        this.username = username;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }


    public Client(int clientId, String username , int age, String email, String phone){
        this.clientId = clientId;
        this.username = username;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    public int getclientId(){ return clientId; }
    public String getUsername(){ return username; }
    public int getAge(){ return age; }
    public String getEmail(){ return email; }
    public String getPhone(){ return phone; }

    public void setUsername(String username){ this.username = username; } //when calling setter in app also be careful to call a service method afterward in order to sync the db
    public void setAge(int age){ this.age = age;}
    public void setEmail(String email){ this.email = email; }
    public void setPhone(String phone){ this.phone = phone; }

    @Override
    public String toString() {
        return "Client{" +
                "id_client=" + clientId +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
