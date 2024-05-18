import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Parcer parcer = new Parcer(); //создаем парсер
        List<String> lines = Files.readAllLines(new File("/Users/codeinium/programming/java/2sem/kr/src/data.txt").toPath());
        parcer.read(lines);//читаем данные из файла
        Writer writer = new Writer(parcer.getAllPrograms(), "tv_guide.xlsx");
        Writer.saveToExcel(parcer.getAllPrograms(), "tv_guide.xlsx"); //сохранение данных в экслеь
    }
}
