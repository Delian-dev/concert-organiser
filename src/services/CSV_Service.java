package services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class CSV_Service {
    private static final String FILE_PATH = "logs/audit.csv";
    private static final Path logPath = Paths.get(FILE_PATH);

    static {
        try {
            Path logDir = logPath.getParent();
            if (logDir != null && !Files.exists(logDir)) {
                Files.createDirectories(logDir);
            }

            // initializing the file
            if (Files.notExists(logPath)) {
                Files.write(logPath, List.of("Action,Entity,Timestamp"));
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize CsvLogger: " + e.getMessage());
        }
    }

    public static void logAction(String action, String entity) {
        String timestamp = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .format(LocalDateTime.now(ZoneOffset.UTC));
        String logEntry = String.format("%s,%s,%s%n", action, entity, timestamp);

        try {
            Files.write(logPath, List.of(logEntry), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Failed to log action: " + e.getMessage());
        }
    }

}
