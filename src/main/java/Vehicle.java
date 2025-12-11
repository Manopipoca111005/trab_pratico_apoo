public class Vehicle{
    private String licensePlate;
    private String  brand;
    private String model;
    private int productionYear;
    public static String[] prompts(){
        return new String[] {
                "Digite a matricula da viatura: ",
                "Digite a marca da viatura: ",
                "Digite o modelo da viatura: ",
                "Digite o ano de fabrico da viatura: ",
        };
    }

    Vehicle(String driverLicenseNumber, String brand, String model,int productionYear) {
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
}
