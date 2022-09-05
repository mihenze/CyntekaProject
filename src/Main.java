import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        File input = new File("D:/testFile/input.txt");
        File output = new File("D:/testFile/output.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(input));
                BufferedWriter writer = new BufferedWriter(new FileWriter(output))){
            //считываем данные
            List<String> stringsN = getList(reader);
            List<String> stringsM = getList(reader);

            //получаем результирующие строки
            List<String> result = Sequences.sequence(stringsN, stringsM);

            //записываем
            for (int i = 0; i < result.size(); i++){
                writer.write(result.get(i)+ "\n");
            }

        } catch (Exception exception){
            exception.printStackTrace();
        }

    }

    public static List<String> getList(BufferedReader reader) throws IOException {
        //считываем число
        int n = 0;
        String str = reader.readLine();
        if (str != null) {
            n = Integer.parseInt(str);
        }

        //считываем строки
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < n; i++){
            if (reader.ready())
                strings.add(reader.readLine());
            else throw new IOException("Wrong number of lines");
        }

        return strings;
    }
}
