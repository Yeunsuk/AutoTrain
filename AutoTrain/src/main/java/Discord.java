import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class Discord {
    public static void sendWebhook() {
        try {
            URI uri = URI.create(Globalvariable.Discord_webhook_url);
            URL url = uri.toURL();

            //url로 http 연결
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            //전송할 메시지
            String message = Globalvariable.datetime + ", " + Globalvariable.start_station + "-" + Globalvariable.end_station + " 티켓이 예약되었습니다.";
            String jsonPayload = String.format("{\"content\": \"%s\"}", message);

            //데이터 전송
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            //서버 응답코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode == 204) {
                System.out.println("디스코드 웹훅 전송 성공");
            } else {
                System.err.println("웹훅 전송 실패: HTTP 상태 코드 " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
