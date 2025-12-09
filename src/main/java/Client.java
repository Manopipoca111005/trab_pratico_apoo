public class Client implements Promptable {
    private String name;
    private int nif;
    private String tlm;
    private String address;

    @Override
    public String[] prompts(){
        return new String[]{
            "Digite o nome do cliente: ",
            "Digite o número de identificação fiscal do cliente: ",
            "Digite o número de telemóvel do cliente: ",
            "Digite a morada do cliente: "
        };
    }

    Client(String name, int nif, String tlm, String address) {
        this.name = name;
        this.nif = nif;
        this.tlm = tlm;
        this.address = address;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getNif() {
        return nif;
    }
    public void setNif(int nif) {
        this.nif = nif;
    }
    public String getTlm() {
        return tlm;
    }
    public void setTlm(String tlm) {
        this.tlm = tlm;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
