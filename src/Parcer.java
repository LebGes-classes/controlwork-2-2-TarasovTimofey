import java.time.LocalTime;
import java.util.*;

public class Parcer {
    private Map<BroadcastsTime, List<Program>> programGuide;
    private List<Program> allPrograms;

    public void read(List<String> lines) {
        this.programGuide = new TreeMap<>();
        this.allPrograms = new ArrayList<>();
        String currentChannel = null;

        // Обработка данных из файла
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line.startsWith("#")) {
                currentChannel = line.substring(1);
            } else if (!line.isEmpty()) {
                BroadcastsTime time = new BroadcastsTime(line);
                String title = lines.get(++i).trim(); // Читаем следующую строку как название
                Program program = new Program(currentChannel, time, title);
                programGuide.computeIfAbsent(time, k -> new ArrayList<>()).add(program);
                allPrograms.add(program);
            }
        }

        outputPrograms();

        searchByNow();

        searchByChannelNow();

        searchByName();


    }

    public void outputPrograms() {
        // вывод всех программ в порядке возрастания канал, время показа
        System.out.println("Все программы:");
        allPrograms.stream()
                .sorted(Comparator.comparing(Program::getChannel)
                        .thenComparing(Program::getTime))
                .forEach(System.out::println);

        // вывод всех программ, которые идут сейчас
        BroadcastsTime currentTime = new BroadcastsTime(LocalTime.now());
        System.out.println("\nПрограммы, идущие сейчас:");
        programGuide.getOrDefault(currentTime, Collections.emptyList()).forEach(System.out::println);

    }

    public void searchByNow() {
        // вывод всех программ, которые идут сейчас
        BroadcastsTime currentTime = new BroadcastsTime(LocalTime.now());
        System.out.println("\nПрограммы, идущие сейчас:");
        this.programGuide.getOrDefault(currentTime, Collections.emptyList()).forEach(System.out::println);
    }

    public void searchByChannelNow() {
        // поиск программ определенного канала, которые идут сейчас
        BroadcastsTime currentTime = new BroadcastsTime(LocalTime.now());
        String searchChannel = "Первый";
        System.out.println("\nПрограммы канала \"" + searchChannel + "\", идущие сейчас:");
        this.programGuide.getOrDefault(currentTime, Collections.emptyList()).stream()
                .filter(program -> program.getChannel().equals(searchChannel))
                .forEach(System.out::println);

        searchByChannel();
    }
    public void searchByName() {
        // поиск программ по названию
        String searchTitle = "Новости";
        System.out.println("\nПрограммы с названием \"" + searchTitle + "\":");
        this.allPrograms.stream()
                .filter(program -> program.getTitle().contains(searchTitle))
                .forEach(System.out::println);
    }
    public void searchByChannel() {
        // поиск программ определенного канала в промежутке времени
        String searchChannel2 = "Россия 1";
        BroadcastsTime startTime = new BroadcastsTime("08:00");
        BroadcastsTime endTime = new BroadcastsTime("12:00");
        System.out.println("\nПрограммы канала \"" + searchChannel2 + "\" в промежутке от " + startTime + " до " + endTime + ":");
        this.programGuide.entrySet().stream()
                .filter(entry -> entry.getKey().between(startTime, endTime))
                .flatMap(entry -> entry.getValue().stream())
                .filter(program -> program.getChannel().equals(searchChannel2))
                .forEach(System.out::println);

    }

    public Map<BroadcastsTime, List<Program>> getProgramGuide() {
        return programGuide;
    }

    public void setProgramGuide(Map<BroadcastsTime, List<Program>> programGuide) {
        this.programGuide = programGuide;
    }

    public List<Program> getAllPrograms() {
        return allPrograms;
    }

    public void setAllPrograms(List<Program> allPrograms) {
        this.allPrograms = allPrograms;
    }
}
