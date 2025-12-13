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
    public static String nif;
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
                findByNIF(line, clients),
                LocalDateTime.parse(line[1], FORMATTER),
                line[2],
                line[3],
                Double.parseDouble(line[4])
        ), "reservations.txt");

        travels = readFiles(line -> new Travel(
                findByNIF(line, drivers),
                findByNIF(line, clients),
                findVehiculeBylicensePlate(line),
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
            switch (option) {
                case 1:
                    Driver.Menu();
                    option = scanner.nextInt();
                    switch (option) {
                        case 1:
                            drivers = writeFiles(line -> new Driver(
                                    attributeValues.get(0),
                                    Integer.parseInt(attributeValues.get(1)),
                                    attributeValues.get(2),
                                    Long.parseLong(attributeValues.get(3)),
                                    Integer.parseInt(attributeValues.get(4)),
                                    attributeValues.get(5),
                                    attributeValues.get(6)
                            ), "drivers.txt");
                            break;
                        case 2:
                            for (Driver driver : drivers) {
                                System.out.println(driver);
                            }
                            break;
                        case 3:
                            System.out.println("Digite o número de identificação do condutor: ");
                            nif = scanner.next();
                            line = new String[]{nif};
                            findByNIF(line,drivers);
                            break;
                        case 4:
                            System.out.println("Digite o número de identificação do condutor: ");
                            nif = scanner.next();
                            line = new String[]{nif};
                            System.out.println(Driver.infoPrompts()[0]);
                            System.out.println(Driver.infoPrompts()[1]);
                            System.out.println(Driver.infoPrompts()[2]);
                            System.out.println(Driver.infoPrompts()[3]);
                            System.out.println(Driver.infoPrompts()[4]);
                            System.out.println(Driver.infoPrompts()[5]);
                            driverFound = findByNIF(line, drivers);
                            System.out.println(Driver.prompts()[0]);
                            value = scanner.nextLine();
                            if(!value.isEmpty()){
                                driverFound.setName(value);
                            }
                            System.out.println(Driver.prompts()[1]);
                            if(!value.isEmpty()){
                                driverFound.setNIC(Integer.parseInt(value));
                            }
                            System.out.println(Driver.prompts()[2]);
                            if(!value.isEmpty()){
                                driverFound.setDriverLicenseNumber(value);
                            }
                            System.out.println(Driver.prompts()[3]);
                            if(!value.isEmpty()){
                                driverFound.setNiss(Long.parseLong(value));
                            }
                            System.out.println(Driver.prompts()[4]);
                            if(!value.isEmpty()){
                                driverFound.setNif(Integer.parseInt(value));
                            }
                            System.out.println(Driver.prompts()[5]);
                            if(!value.isEmpty()){
                                driverFound.setTlm(value);
                            }
                            System.out.println(Driver.prompts()[6]);
                            if(!value.isEmpty()){
                                driverFound.setAddress(value);
                            }
                            break;
                        case 5:
                            System.out.println("Digite o número de identificação do condutor: ");
                            nif = scanner.next();
                            line = new String[]{nif};
                            driverFound = findByNIF(line, drivers);
                            drivers.remove(driverFound);
                            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
                                ArrayList<String[]> lines = new ArrayList<>();
                                while (bufferedReader.ready()){
                                    line = bufferedReader.readLine().split(";");
                                    lines.add(line);
                                }
                                for(String l : line){
                                    if(l.equals(nif)){
                                        lines.remove(line);
                                    }
                                }
                                System.out.println("Dados lidos com sucesso!");
                            }
                            catch (FileNotFoundException e) {
                                System.out.println("Ficheiro não encontrado!");
                            }
                            catch (IOException e){
                                System.out.println("Alguma coisa correu mal!");
                            }
                            break;
                    }   case 0:
                            break;
                case 2:
                    drivers = writeFiles(line -> new Driver(
                            attributeValues.get(0),
                            Integer.parseInt(attributeValues.get(1)),
                            attributeValues.get(2),
                            Long.parseLong(attributeValues.get(3)),
                            Integer.parseInt(attributeValues.get(4)),
                            attributeValues.get(5),
                            attributeValues.get(6)
                    ), "drivers.txt");

                    vehicles = writeFiles(line -> new Vehicle(
                            attributeValues.get(0),
                            attributeValues.get(1),
                            attributeValues.get(2),
                            Integer.parseInt(attributeValues.get(3))
                    ), "vehicles.txt");

                    clients = writeFiles(line -> new Client(
                            attributeValues.get(0),
                            Integer.parseInt(attributeValues.get(1)),
                            attributeValues.get(2),
                            attributeValues.get(3)
                    ), "clientes.txt");

                    reservations = writeFiles(line -> new Reservation(
                            (Client) findByNIF(line.toArray(new String[0]), clients),
                            LocalDateTime.parse(attributeValues.get(1), FORMATTER),
                            attributeValues.get(2),
                            attributeValues.get(3),
                            Double.parseDouble(attributeValues.get(4))
                    ), "reservations.txt");

                    travels = writeFiles(line -> new Travel(
                            findByNIF(attributeValues.toArray(new String[10]), drivers),
                            (Client) findByNIF(line.toArray(new String[0]), clients),
                            findVehiculeBylicensePlate(attributeValues.toArray(new String[10])),
                            LocalDateTime.parse(attributeValues.get(3), FORMATTER),
                            LocalDateTime.parse(attributeValues.get(4), FORMATTER),
                            attributeValues.get(5),
                            attributeValues.get(6),
                            Double.parseDouble(attributeValues.get(7)),
                            Double.parseDouble(attributeValues.get(8))
                    ), "travels.txt");

            }

}
    static <T> ArrayList<T> readFiles(Function<String[], T> lineMapper, String fileName){
        filePath = "";
        ArrayList<T> list = new ArrayList<>();
        if(fileName.equals("drivers.txt")){
            filePath = "/Users/pipocadev/Documents/GitHub/trab_pratico_apoo/drivers.txt";
        }else if(fileName.equals("vehicles.txt")){
            filePath = "/Users/pipocadev/Documents/GitHub/trab_pratico_apoo/vehicles.txt";
        }else  if(fileName.equals("clientes.txt")){
            filePath = "/Users/pipocadev/Documents/GitHub/trab_pratico_apoo/clients.txt";
        } else if (fileName .equals("reservations.txt")) {
            filePath = "/Users/pipocadev/Documents/GitHub/trab_pratico_apoo/reservations.txt";
        } else if (fileName.equals("travels.txt")) {
            filePath = "/Users/pipocadev/Documents/GitHub/trab_pratico_apoo/travels.txt";
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
        String filePath = "";
        ArrayList<T> list = new ArrayList<>();

        if (fileName.equals("drivers.txt")) {
            filePath = "/Users/pipocadev/Documents/GitHub/trab_pratico_apoo/drivers.txt";
        } else if (fileName.equals("vehicles.txt")) {
            filePath = "/Users/pipocadev/Documents/GitHub/trab_pratico_apoo/vehicles.txt";
        } else if (fileName.equals("clientes.txt")) {
            filePath = "/Users/pipocadev/Documents/GitHub/trab_pratico_apoo/clients.txt";
        } else if (fileName.equals("reservations.txt")) {
            filePath = "/Users/pipocadev/Documents/GitHub/trab_pratico_apoo/reservations.txt";
        } else if (fileName.equals("travels.txt")) {
            filePath = "/Users/pipocadev/Documents/GitHub/trab_pratico_apoo/travels.txt";
        } else {
            System.out.println("nome de ficheiro incorreto!");
            return list;
        }

        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            ArrayList<String> attributesValues = new ArrayList<>();
            T newObjectWriter = null;

            String switchKey = fileName.substring(0, fileName.lastIndexOf('.'));

            printWriter.println();

            switch (switchKey) {
                case "drivers":
                    System.out.print(Driver.prompts()[0]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(0));
                    printWriter.print(";");
                    System.out.print(Driver.prompts()[1]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(1));
                    printWriter.print(";");
                    System.out.print(Driver.prompts()[2]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(2));
                    printWriter.print(";");
                    System.out.print(Driver.prompts()[3]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(3));
                    printWriter.print(";");
                    System.out.print(Driver.prompts()[4]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(4));
                    printWriter.print(";");
                    System.out.print(Driver.prompts()[5]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(5));
                    printWriter.print(";");
                    System.out.print(Driver.prompts()[6]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(6));
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "vehicles":
                    System.out.print(Vehicle.prompts()[0]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(0));
                    printWriter.print(";");
                    System.out.print(Vehicle.prompts()[1]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(1));
                    printWriter.print(";");
                    System.out.print(Vehicle.prompts()[2]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(2));
                    printWriter.print(";");
                    System.out.print(Vehicle.prompts()[3]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(3));
                    printWriter.print(";");
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "clientes":
                    System.out.print(Client.prompts()[0]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(0));
                    printWriter.print(";");
                    System.out.print(Client.prompts()[1]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(1));
                    printWriter.print(";");
                    System.out.print(Client.prompts()[2]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(2));
                    printWriter.print(";");
                    System.out.print(Client.prompts()[3]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(3));
                    printWriter.print(";");
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "reservations":
                    System.out.print(Reservation.prompts()[0]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(0));
                    printWriter.print(";");
                    System.out.print(Reservation.prompts()[1]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(1));
                    printWriter.print(";");
                    System.out.print(Reservation.prompts()[2]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(2));
                    printWriter.print(";");
                    System.out.print(Reservation.prompts()[3]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(3));
                    printWriter.print(";");
                    System.out.print(Reservation.prompts()[4]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(4));
                    printWriter.print(";");
                    newObjectWriter = lineMapper.apply(attributesValues);
                    list.add(newObjectWriter);
                    break;
                case "travels":
                    System.out.println(Travel.prompts()[0]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(0));
                    printWriter.print(";");
                    System.out.print(Travel.prompts()[1]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(1));
                    printWriter.print(";");
                    System.out.print(Travel.prompts()[2]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(2));
                    printWriter.print(";");
                    System.out.print(Travel.prompts()[3]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(3));
                    printWriter.print(";");
                    System.out.print(Travel.prompts()[4]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(4));
                    printWriter.print(";");
                    System.out.print(Travel.prompts()[5]);
                    attributesValues.add(scanner.next());
                    printWriter.print(attributesValues.get(5));
                    printWriter.print(";");
                    System.out.print(Travel.prompts()[6]);
                    attributesValues.add(scanner.nextLine());
                    printWriter.print(attributesValues.get(6));
                    printWriter.print(";");
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

    public static <T extends HasNif> T findByNIF(String [] line, ArrayList<T> objects){
        for (T object : objects){
            if (object.getNif() == Integer.parseInt(line[0])){
                return  object;
            }
        }
        return null;
    }

    public static Vehicle findVehiculeBylicensePlate(String[] line){
        for (Vehicle vehicle : vehicles){
            if(vehicle.getLicensePlate().equals(line[2])){
                return  vehicle;
            }
        }
        return null;
    }
}

