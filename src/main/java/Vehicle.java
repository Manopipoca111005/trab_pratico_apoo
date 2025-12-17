public class Vehicle{
    private String licensePlate;
    private String  brand;
    private String model;
    private int productionYear;
    Vehicle(String licensePlate, String brand, String model,int productionYear) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public static void Menu(){
        System.out.println("🚗 --- Gestão de Viaturas --- 📋");
        System.out.println("1.  ➕ Registar Viatura");
        System.out.println("2.  📋 Listar Viaturas");
        System.out.println("3.  🔍 Consultar Viatura (por matricula)");
        System.out.println("4.  📝 Atualizar Vitaura");
        System.out.println("5.  🗑️ Eliminar Viatura");
        System.out.println("0.  ↩️ Voltar");
        System.out.print("👉 Selecione uma opção: ");
    }
    public static String[] prompts(){
        return new String[] {
                "🔢 Digite a matrícula da viatura: ",
                "🚗 Digite a marca da viatura?: ",
                "🔍 Digite o modelo da viatura: ",
                "📅 Digite o ano de fabrico da viatura: ",
        };
    }

    public static String[] infoPrompts(){
        return new String[] {
                "🔢 Matricula da viatura: ",
                "🚗 Marca da Viatura: ",
                "🔍 Modelo da Viatura: ",
                "📅 Ano de fabrico da viatura: ",
        };
    }

    @Override
    public String toString(){
        return "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                "🚗 " + this.licensePlate.toUpperCase() + "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                infoPrompts()[0] + this.getLicensePlate() + "\n" +
                infoPrompts()[1] + this.getBrand() + "\n" +
                infoPrompts()[2] + this.getModel() + "\n" +
                infoPrompts()[3] + this.getProductionYear() + "\n" +
                "━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n";
    };
}