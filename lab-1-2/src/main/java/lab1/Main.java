package lab1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static Map<String, StringTransformer> transformerMap = new HashMap<String, StringTransformer>() {
        {
            put("obfuscate", Obfuscator::obfuscate);
            put("unobfuscate", Obfuscator::unobfuscate);
        }
    };

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Please provide 2 arguments: action file_path");
            return;
        }

        String command = args[0];
        String file_path = args[1];

        StringTransformer transformer = transformerMap.get(command);
        if (transformer == null){
            System.err.println("Unsupported operation");
            return;
        }

        execute(file_path, "output.xml", transformer);

    }

    public static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static void writeFile(String filename, String data) throws IOException {
        File xmlOutput = new File(filename);
        FileWriter fileWriter = new FileWriter(xmlOutput);
        fileWriter.write(data);
        fileWriter.close();
    }

    public static void execute(String inputFile, String outputFile, StringTransformer transformer) {
        try {
            String data = readFile(inputFile);
            Employees employees = Transformer.desirialize(data, Employees.class);
            for (Employee employee : employees.getEmployees()) {
                employee.setSalary(transformer.transform(employee.getSalary()));
                employee.setFirstName(transformer.transform(employee.getFirstName()));
                employee.setLastName(transformer.transform(employee.getLastName()));
                employee.setPosition(transformer.transform(employee.getPosition()));
            }
            writeFile(outputFile, Transformer.serialize(employees));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    interface StringTransformer {
        String transform(String s);
    }
}
