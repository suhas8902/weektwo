import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class UrlShortener {
    private static final String SHORT_URL_PREFIX = "http://";
    private Map<String, String> urlMapping = new HashMap<>();
    public static void main(String[] args) {
        UrlShortener urlShortener = new UrlShortener();
        urlShortener.run();
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter the URL to be shortened (type 'exit' to quit): ");
            String originalUrl = scanner.nextLine();
            if (originalUrl.equalsIgnoreCase("exit")) {
                break;
            }
            String shortenedUrl = shortenUrl(originalUrl);
            System.out.println("Shortened URL: " + shortenedUrl);
        }
        scanner.close();
    }
    private String shortenUrl(String originalUrl) {
        if (urlMapping.containsValue(originalUrl)) {
            for (Map.Entry<String, String> entry : urlMapping.entrySet()) {
                if (entry.getValue().equals(originalUrl)) {
                    return entry.getKey();
                }
            }
        }
        String hash = hashString(originalUrl);
        String shortenedUrl = SHORT_URL_PREFIX + hash;
        urlMapping.put(shortenedUrl, originalUrl);
        return shortenedUrl;
    }
    private String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().encodeToString(hashBytes).substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
