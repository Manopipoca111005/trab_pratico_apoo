/**
 * Represents a Client in the system.
 * A client has personal information such as name, NIF, phone number, and
 * address.
 */
public class Client implements HasNif {
    private String name;
    private int nif;
    private String tlm;
    private String address;

    /**
     * Retrieves the client's NIF.
     *
     * @return The NIF of the client.
     */
    @Override
    public int getClientNif() {
        return this.nif;
    }

    /**
     * Retrieves the driver's NIF.
     *
     * @return Always returns 0 as this is a Client.
     */
    @Override
    public int getDriverNif() {
        return 0;
    }

    /**
     * Constructs a new Client with the specified details.
     *
     * @param name    The name of the client.
     * @param nif     The Tax Identification Number (NIF) of the client.
     * @param tlm     The phone number of the client.
     * @param address The address of the client.
     */
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

    /**
     * Displays the Client management menu options to the console.
     */
    public static void Menu() {
        System.out.println("👥 --- Gestão de Clientes --- 📋");
        System.out.println("1.  ➕ Registar Cliente");
        System.out.println("2.  📋 Listar Cliente");
        System.out.println("3.  🔍 Consultar Cliente (por NIF)");
        System.out.println("4.  📝 Atualizar Cliente");
        System.out.println("5.  🗑️ Eliminar Cliente");
        System.out.println("0.  ↩️ Voltar");
        System.out.print("👉 Selecione uma opção: ");
    }

    /**
     * Provides prompts for collecting client information.
     *
     * @return An array of prompt strings.
     */
    public static String[] prompts() {
        return new String[] {
                "👤Digite o nome do cliente: ",
                "🔢Digite o número de identificação fiscal do cliente: ",
                "📱Digite o número de telemóvel do cliente: ",
                "🏠Digite a morada do cliente: "
        };
    }

    /**
     * Provides labels for displaying client information.
     *
     * @return An array of label strings.
     */
    public static String[] infoPrompts() {
        return new String[] {
                "📝 Nome do cliente: ",
                "🪪 Número do cartão de cidadão do cliente: ",
                "📱 Número de telemóvel do cliente: ",
                "🏠 Morada do cliente: "
        };
    }

    /**
     * Returns a string representation of the Client.
     *
     * @return A formatted string containing client details.
     */
    @Override
    public String toString() {
        return "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                "👤 " + this.name.toUpperCase() + "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                infoPrompts()[1] + this.getClientNif() + "\n" +
                infoPrompts()[2] + this.getTlm() + "\n" +
                infoPrompts()[3] + this.getAddress() + "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n";
    }
}
