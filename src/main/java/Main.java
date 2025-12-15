import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;
import java.lang.reflect.*;

public class Main {
    public static String fileName;
    public static String filePath;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    static public Scanner scanner = new Scanner(System.in);
    public static ArrayList<Driver> drivers = new ArrayList<>();
    public static ArrayList<Vehicle> vehicles = new ArrayList<>();
    public static ArrayList<Client> clients = new ArrayList<>();
    public static ArrayList<Reservation> reservations = new ArrayList<>();
    public static ArrayList<Travel> travels = new ArrayList<>();
    public static String[] line;
    public static ArrayList<String> attributeValues;
    public static Driver driverFound;
    public static void Header(){
        System.out.println("=========================================");
        System.out.println("       🚗 SISTEMA DE GESTÃO TVDE 📊       ");
        System.out.println("=========================================");

    }

    public static void Menu(){
    System.out.println("------------------------------------------");
        System.out.println("         💻 Menu Principal Sistema TVDE    ");
        System.out.println("------------------------------------------");
        System.out.println("1. 👤 Gestão de Condutores (CRUD)");
        System.out.println("2. 🚗 Gestão de Viaturas (CRUD)");
        System.out.println("3. 👥 Gestão de Clientes (CRUD)");
        System.out.println("4. 📅 Gestão de Reservas (CRUD, Inserir/Remover)");
        System.out.println("5. 🗺️ Gestão de Viagens (CRUD, Inserir/Remover)");
        System.out.println("6. ➡️ Criar Viagem a partir de Reserva");
        System.out.println("7. 📊 Pesquisas e Estatísticas");
        System.out.println("8. 📁 Gestão de Ficheiros e Dados (Gravar/Ler)");
        System.out.println("0. ❌ Sair do Sistema");
        System.out.println("------------------------------------------");
        System.out.print("👉 Selecione uma opção: ");
    }

    public static void infoTitle(String emoji, String title){
        String line = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
        System.out.println(line);
        System.out.println(emoji + " " + title.toUpperCase());
        System.out.println(line + "\n");
    }

    public static void main(String[] args){
        drivers = readFiles(line -> new Driver(
                line[0],
                Integer.parseInt(line[1]),
                line[2],
                Long.parseLong(line[3]),
                Integer.parseInt(line[4]),
                line[5],
                line[6]
        ), "drivers.txt");

        vehicles = readFiles(line -> new Vehicle(
                line[0],
                line[1],
                line[2],
                Integer.parseInt(line[3])
        ), "vehicles.txt");

        clients = readFiles(line -> new Client(
                line[0],
                Integer.parseInt(line[1]),
                line[2],
                line[3]
        ), "clientes.txt");

        reservations = readFiles(line -> new Reservation(
                findByNIF(Integer.parseInt(line[0]), clients),
                LocalDateTime.parse(line[1], FORMATTER),
                line[2],
                line[3],
                Double.parseDouble(line[4])
        ), "reservations.txt");

        travels = readFiles(line -> new Travel(
                findByNIF(Integer.parseInt(line[0]), drivers),
                findByNIF(Integer.parseInt(line[1]), clients),
                findVehiculeBylicensePlate(line[2]),
                LocalDateTime.parse(line[3], FORMATTER),
                LocalDateTime.parse(line[4], FORMATTER),
                line[5],
                line[6],
                Double.parseDouble(line[7]),
                Double.parseDouble(line[8])
        ), "travels.txt");
            Header();
            Menu();
            int option = scanner.nextInt();
            String value = "";
            int nif;
            switch (option) {
                case 1:
                    Driver.Menu();
                    option = scanner.nextInt();
                    switch (option) {
                        case 1:
                            drivers = writeFiles(line -> new Driver(
                                    line.get(0),
                                    Integer.parseInt(line.get(1)),
                                    line.get(2),
                                    Long.parseLong(line.get(3)),
                                    Integer.parseInt(line.get(4)),
                                    line.get(5),
                                    line.get(6)
                            ), "drivers.txt");
                            break;
                        case 2:
                            infoTitle("📋", "INFORMAÇÕES DOS CONDUTORES");
                            for (Driver driver : drivers) {
                                System.out.println(driver);
                            }
                            break;
                        case 3:
                            System.out.print("Digite o número de identificação do condutor: ");
                            nif = scanner.nextInt();
                            driverFound = findByNIF(nif, drivers);
                            System.out.println(driverFound);
                            break;
                        case 4:
                            System.out.print("Digite o número de identificação do condutor: ");
                            nif = scanner.nextInt();
                            scanner.nextLine();
                            findByNIF(nif, drivers);
                            for (Driver driver : drivers) {
                                System.out.println(driver);
                            }
                            driverFound = findByNIF(nif, drivers);
                            System.out.print(Driver.prompts()[0]);
                            value = scanner.nextLine();
                            if (!value.isEmpty()) {
                                driverFound.setName(value);
                            }
                            System.out.print(Driver.prompts()[1]);
                            if (!value.isEmpty()) {
                                driverFound.setNIC(Integer.parseInt(value));
                            }
                            System.out.print(Driver.prompts()[2]);
                            if (!value.isEmpty()) {
                                driverFound.setDriverLicenseNumber(value);
                            }
                            System.out.print(Driver.prompts()[3]);
                            if (!value.isEmpty()) {
                                driverFound.setNiss(Long.parseLong(value));
                            }
                            System.out.print(Driver.prompts()[4]);
                            if (!value.isEmpty()) {
                                driverFound.setNif(Integer.parseInt(value));
                            }
                            System.out.print(Driver.prompts()[5]);
                            if (!value.isEmpty()) {
                                driverFound.setTlm(value);
                            }
                            System.out.print(Driver.prompts()[6]);
                            if (!value.isEmpty()) {
                                driverFound.setAddress(value);
                            }
                            break;
                        case 5:
                            filePath = "drivers.txt";
                            line = null;
                            System.out.print("Digite o número de identificação do condutor: ");
                            nif = scanner.nextInt();
                            scanner.nextLine();
                            driverFound = findByNIF(nif, drivers);
                            drivers.remove(driverFound);
                            try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))){
                                for (Driver driver : drivers) {
                                        printWriter.print(driver.getName());
                                        printWriter.print(";");
                                        printWriter.print(driver.getNIC());
                                        printWriter.print(";");
                                        printWriter.print(driver.getDriverLicenseNumber());
                                        printWriter.print(";");
                                        printWriter.print(driver.getNiss());
                                        printWriter.print(";");
                                        printWriter.print(driver.getNif());
                                        printWriter.print(";");
                                        printWriter.print(driver.getTlm());
                                        printWriter.print(";");
                                        printWriter.println(driver.getAddress());
                                }
                                System.out.println("Dados eleminados com sucesso!");
                            } catch (FileNotFoundException e) {
                                System.out.println("Ficheiro não encontrado!");
                            } catch (IOException e) {
                                System.out.println("Alguma coisa correu mal!");
                            }
                            break;
                        case 0:
                            break;
                    }
                    break;
                case 2:
                    drivers = writeFiles(line -> new Driver(
                            line.get(0),
                            Integer.parseInt(line.get(1)),
                            line.get(2),
                            Long.parseLong(line.get(3)),
                            Integer.parseInt(line.get(4)),
                            line.get(5),
                            line.get(6)
                    ), "drivers.txt");

                    vehicles = writeFiles(line -> new Vehicle(
                            line.get(0),
                            line.get(1),
                            line.get(2),
                            Integer.parseInt(line.get(3))
                    ), "vehicles.txt");

                    clients = writeFiles(line -> new Client(
                            line.get(0),
                            Integer.parseInt(line.get(1)),
                            line.get(2),
                            line.get(3)
                    ), "clientes.txt");

                    reservations = writeFiles(line -> new Reservation(
                            findByNIF(Integer.parseInt(line.get(0)), clients),
                            LocalDateTime.parse(line.get(1), FORMATTER),
                            line.get(2),
                            line.get(3),
                            Double.parseDouble(line.get(4))
                    ), "reservations.txt");

                    travels = writeFiles(line -> new Travel(
                            findByNIF(Integer.parseInt(line.get(0)), drivers),
                            findByNIF(Integer.parseInt(line.get(1)), clients),
                            findVehiculeBylicensePlate(line.get(2)),
                            LocalDateTime.parse(line.get(3), FORMATTER),
                            LocalDateTime.parse(line.get(4), FORMATTER),
                            line.get(5),
                            line.get(6),
                            Double.parseDouble(line.get(7)),
                            Double.parseDouble(line.get(8))
                    ), "travels.txt");
                    break;
            }
}
    static <T> ArrayList<T> readFiles(Function<String[], T> lineMapper, String fileName){
        ArrayList<T> list = new ArrayList<>();
        switch (fileName){
            case "drivers.txt":
                filePath = "drivers.txt";
                break;
            case "vehicles.txt":
                filePath = "vehicles.txt";
                break;
            case "clientes.txt":
                filePath = "clients.txt";
                break;
            case "reservations.txt":
                filePath = "reservations.txt";
                break;
            case "travels.txt":
                filePath = "travels.txt";
                break;
            default:
                System.out.println("Ficheiro não encontrado!");
        }
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
            String [] line;
            while (bufferedReader.ready()){
                line = bufferedReader.readLine().split(";");
                T newObjectReader = lineMapper.apply(line);
                list.add(newObjectReader);
            }
            System.out.println("Dados lidos com sucesso!");
        }
        catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado!");
        }
        catch (IOException e){
            System.out.println("Alguma coisa correu mal!");
        }
        return list;
    }

    public static <T> ArrayList<T> writeFiles(Function<ArrayList<String>, T> lineMapper, String fileName) {
        ArrayList<T> list = new ArrayList<>();
        switch (fileName){
            case "drivers.txt":
                filePath = "drivers.txt";
                break;
            case "vehicles.txt":
                filePath = "vehicles.txt";
                break;
            case "clientes.txt":
                filePath = "clients.txt";
                break;
            case "reservations.txt":
                filePath = "reservations.txt";
                break;
            case "travels.txt":
                filePath = "travels.txt";
                break;
            default:
                System.out.println("Ficheiro não encontrado!");
                return list;
        }

        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            File file = new File(filePath);
            if(file.length() > 0){
                printWriter.println();
            }
            ArrayList<String> attributesValues = new ArrayList<>();
            T newObjectWriter = null;
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            switch (filePath) {
                case "drivers.txt":
                    for (int i = 0; i < Driver.prompts().length; i++) {
                        System.out.print(Driver.prompts()[i]);
                        attributesValues.add(scanner.nextLine());
                        printWriter.print(attributesValues.get(i));
                        printWriter.print(";");
                    }
                    printWriter.println();
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "vehicles.txt":
                    for (int i = 0; i < Vehicle.prompts().length; i++) {
                        System.out.print(Vehicle.prompts()[i]);
                        System.out.flush();
                        attributesValues.add(scanner.next());
                        printWriter.print(attributesValues.get(i));
                        printWriter.print(";");
                        printWriter.flush();
                    }
                    printWriter.println();
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "clientes.txt":
                    for (int i = 0; i < Client.prompts().length; i++) {
                        System.out.print(Client.prompts()[i]);
                        System.out.flush();
                        attributesValues.add(scanner.next());
                        printWriter.print(attributesValues.get(i));
                        printWriter.print(";");
                        System.out.flush();
                    }
                    printWriter.println();
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "reservations.txt":
                    for (int i = 0; i < Reservation.prompts().length; i++) {
                        System.out.println(Reservation.prompts()[i]);
                        System.out.flush();
                        attributesValues.add(scanner.next());
                        printWriter.print(attributesValues.get(i));
                        printWriter.print(";");
                        System.out.flush();
                    }
                    printWriter.println();
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "travels.txt":
                    for (int i = 0; i < Travel.prompts().length; i++) {
                        System.out.print(Travel.prompts()[i]);
                        System.out.flush();
                        attributesValues.add(scanner.next());
                        printWriter.print(attributesValues.get(i));
                        printWriter.print(";");
                        System.out.flush();
                    }
                    printWriter.println();
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                default:
                    System.out.println("Erro interno na lógica do switch. Ficheiro não processado.");
            }
            System.out.println("Dados escritos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao escrever ou ficheiro não encontrado/acessível! Detalhes: " + e.getMessage());
        }

        return list;
    }

    public static <T extends HasNif> T findByNIF(int nif, ArrayList<T> objects){
        for (T object : objects){
            if (object.getNif() == nif){
                return  object;
            }
        }
        return null;
    }

    public static Vehicle findVehiculeBylicensePlate(String licensePlate){
        for (Vehicle vehicle : vehicles){
            if(vehicle.getLicensePlate() == licensePlate){
                return  vehicle;
            }
        }
        return null;
    }
}

