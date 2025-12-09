import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

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
    public String[] line;
    public static ArrayList<String> attributeValues;
    static void main(String[] args){

        boolean isFirstUse = true;

        if(isFirstUse) {
            Header();
            System.out.println("\n👋 Olá! Eu sou o seu novo Gestor TVDE.");
            System.out.println("Para começarmos a trabalhar, escolha uma das opções abaixo:");
            System.out.println("-----------------------------------------");
            System.out.println("  [1] 📂 Ler dados de ficheiros existentes");
            System.out.println("  [2] 📝 Iniciar um novo negócio (Criar ficheiros)");
            System.out.println("-----------------------------------------");
            System.out.print("Opção desejada: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
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
                            findClientByNIF(line),
                            LocalDateTime.parse(line[1], FORMATTER),
                            line[2],
                            line[3],
                            Double.parseDouble(line[4])
                    ), "reservations.txt");

                    travels = readFiles(line -> new Travel(
                            line[0],
                            line[1],
                            line[2],
                            LocalDateTime.parse(line[3], FORMATTER),
                            LocalDateTime.parse(line[4], FORMATTER),
                            line[5],
                            line[6],
                            Double.parseDouble(line[7]),
                            Double.parseDouble(line[8])
                    ), "travels.txt");

                case 2:
                    fileName = "drivers.txt";
                    System.out.print("Insira o caminho do local onde deseja guardar o ficheiro " + fileName + ": " );
                    filePath = scanner.nextLine();
                    try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
                        ArrayList<Driver> Newdrivers = new ArrayList<>();
                        Driver newDriver = new Driver();
                        System.out.println("Vamos criar primeiro o seu ficheiro " + fileName + ": ");
                        System.out.println("Digite o nome do condutor: ");
                        newDriver.setName(scanner.nextLine());
                        System.out.println("Digite o número de identificação do condutor: ");
                        newDriver.setNIC(scanner.nextInt());
                        System.out.println("Digite o número da carta de condução do condutor");
                        newDriver.setAddress(scanner.nextLine());
                        System.out.println("Digite o número de segurança social do condutor: ");
                        newDriver.setNiss(scanner.nextLong());
                        System.out.println("Digite o número de telemóvel do condutor: ");
                        newDriver.setTlm(scanner.nextLine());
                        System.out.println("Digite a morada do condutor: ");
                        newDriver.setAddress(scanner.nextLine());

                        printWriter.print(newDriver.getName());
                        printWriter.print(";");
                        printWriter.print(newDriver.getNIC());


                        System.out.println("Dados escritos com sucesso em " + fileName);

                    } catch (IOException e) {
                        System.err.println("Ocorreu um erro de I/O: " + e.getMessage());
                    }
            }
        }
        else{
            Header();

            System.out.println("\n👋 Bem-vindo/a de volta!");
            System.out.println("Em que posso ajudar hoje? (Digite 'menu' para opções)");
        }
    }
    static void Header(){
        System.out.println("=========================================");
        System.out.println("       🚗 SISTEMA DE GESTÃO TVDE 📊       ");
        System.out.println("=========================================");

    }

    //Ver no yt ou usando a IA o que é Function e como usar!
    static <T> ArrayList<T> readFiles(Function<String[], T> lineMapper, String fileName){
        ArrayList<T> list = new ArrayList<>();
        System.out.print("Insira o caminho do ficheiro onde tem os dados do seu " + fileName + ": " );
        filePath = scanner.nextLine();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
            String [] line;
            while (bufferedReader.ready()){
                line = bufferedReader.readLine().split(";");
                T newObject = lineMapper.apply(line);
                list.add(newObject);
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

    static <T> ArrayList<T> writeFiles(Function<String[], T> lineMapper, String fileName){
        ArrayList<T> list = new ArrayList<>();
        System.out.print("Insira o caminho do ficheiro " + fileName + "onde deseja imprimir os dados");
        filePath = scanner.nextLine();
        try(PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))){
            String [] line;
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

    public static Client findClientByNIF(String [] line){
        for (Client client : clients){
            if(client.getNif() == Integer.parseInt(line[0])){
                return  client;
            }
        }
        return null;
    }

    public void typeOfObjetc(){
        if(fileName == "drivers"){
            Driver newDriver = new Driver();
            ArrayList<String> attributeValues = new ArrayList<>();
            System.out.println(newDriver.prompts()[0]);
            attributeValues.add(scanner.nextLine());
            System.out.println(newDriver.prompts()[1]);
            attributeValues.add(scanner.nextLine());
            System.out.println(newDriver.prompts()[2]);
            attributeValues.add(scanner.nextLine());
            System.out.println(newDriver.prompts()[3]);
            attributeValues.add(scanner.nextLine());
            System.out.println(newDriver.prompts()[4]);
            attributeValues.add(scanner.nextLine());
            System.out.println(newDriver.prompts()[5]);
            attributeValues.add(scanner.nextLine());
        }
    }

}

