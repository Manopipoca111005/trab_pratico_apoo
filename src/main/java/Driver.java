public class Driver implements HasNif{
    private String name; // Nome
    private int NIC; // Número de Identificação Civil
    private String driverLicenseNumber;
    private long niss;
    private int nif;
    private String tlm;
    private String address;

    @Override
    public int getNif() {
        return this.nif;
    }
    Driver(String name, int NIC, String driverLicenseNumber, long niss, int nif, String tlm, String address) {
        this.name = name;
        this.NIC = NIC;
        this.driverLicenseNumber = driverLicenseNumber;
        this.niss = niss;
        this.nif = nif;
        this.tlm = tlm;
        this.address = address;
    }

    public int getNIC() {
        return NIC;
    }

    public void setNIC(int NIC) {
        this.NIC = NIC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public long getNiss() {
        return niss;
    }

    public void setNiss(long niss) {
        this.niss = niss;
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


    public static String[] prompts(){
        return new String[] {
                "Digite o nome do condutor: ",
                "Digite o número de identificação do condutor: ",
                "Digite o número da carta de condução do condutor",
                "Digite o número de segurança social do condutor: ",
                "Digite o número de identificação fiscal do condutor: ",
                "Digite o número de telemóvel do condutor: ",
                "Digite a morada do condutor: "
        };
    }

    public static String [] infoPrompts(){
        return new String[] {
                "Nome do condutor: ",
                "Número de identificação fiscal do condutor: ",
                "Número da carta de condução do condutor: ",
                "Número de segurança social: ",
                "Número de telemóvel do condutor: ",
                "Morada do condutor: "
        };
    }

    public static void Menu(){
        System.out.println("🚗 --- Gestão de Condutores --- 📋");
        System.out.println("1  ✨ Criar Condutor");
        System.out.println("2  📝 Listar Condutores");
        System.out.println("3  🔍 Consultar Condutor (por ID)");
        System.out.println("4  ✏️ Atualizar Condutor");
        System.out.println("5  ❌ Eliminar Condutor");
        System.out.println("0  ↩️ Voltar");
        System.out.print("👉 Selecione uma opção: ");
    }
}
