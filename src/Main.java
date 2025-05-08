import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Child child = new Child(1123, "Child", 1, 0);
        Teacher teacher = new Teacher("Teacher", 123);
        Parent parent = new Parent("Parent", 1213);
        CommandHandler commandHandler = new CommandHandler(teacher, parent, child);
        Scanner sc = new Scanner(System.in);

        FileTasks(commandHandler);
        FileWishes(commandHandler);
        FileCommands(commandHandler);

        while (true) {
            System.out.println("Input (type 'exit' to quit): ");
            String in = sc.nextLine().trim();
            if (in.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            }
            if (in.isEmpty()) {
                System.out.println("Error: Empty input detected!");
                continue;
            }
            List<String> tokenizedInput = inputsplitter(in);
            commandHandler.operationselector(tokenizedInput);
        }
        sc.close();
    }

    private static void FileTasks(CommandHandler commandHandler) {
        File file1 = new File("Tasks.txt");
        if (!file1.exists()) {
            System.out.println("Error: Tasks does not exist!");
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader("Tasks.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    commandHandler.operationselector(inputsplitter(line));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error: Unable to open file!");
        }
    }

    private static void FileWishes(CommandHandler commandHandler) {
        File file2 = new File("Wishes.txt");
        if (!file2.exists()) {
            System.out.println("Error: Wishes does not exist!");
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader("Wishes.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    commandHandler.operationselector(inputsplitter(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to open file!");
        }
    }

    private static void FileCommands(CommandHandler commandHandler) {
        File file3 = new File("Commands.txt");
        if (!file3.exists()) {
            System.out.println("Error: Commands does not exist!");
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader("Commands.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    commandHandler.operationselector(inputsplitter(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to open file!");
        }
    }

    private static List<String> inputsplitter(String in) {
        List<String> prt = new ArrayList<>();
        boolean isWithinQuotes = false;
        StringBuilder sb = new StringBuilder();
        for (char c : in.toCharArray()) {
            if (c == '"') {
                isWithinQuotes = !isWithinQuotes;
            } else if (c == ' ' && !isWithinQuotes) {
                if (!sb.isEmpty()) {
                    prt.add(sb.toString());
                    sb.setLength(0);
                }
            } else {
                sb.append(c);
            }
        }
        if (!sb.isEmpty()) {
            prt.add(sb.toString());
        }
        return prt;
    }
}
