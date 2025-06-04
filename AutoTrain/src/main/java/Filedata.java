import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Filedata {
    //파일생성
    public static void defaultfile() {

        // 파일에 쓸 내용
        String content = """
            Discord_url
            코레일ID
            코레일pw
            """;

        // 파일 생성 및 내용 쓰기
        try {
            File file = new File(Globalvariable.DATAPATH);

            // 파일이 없으면 새로 생성
            if (!file.exists()) {
                file.createNewFile();
            }

            // 파일에 텍스트 쓰기
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.close();

            System.out.println("파일이 성공적으로 생성되고 작성되었습니다: " + Globalvariable.DATAPATH);
        } catch (IOException e) {
            System.out.println("파일 생성 중 오류 발생: " + e.getMessage());
        }
    }

    //파일읽고 변수에 저장
    public static void readFile() {
        File file = new File(Globalvariable.DATAPATH);
        if (!file.exists()) defaultfile();
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            Globalvariable.Discord_webhook_url = br.readLine();
            Globalvariable.korail_id = br.readLine();
            Globalvariable.korail_pw = br.readLine();

            System.out.println("파일을 성공적으로 읽고 변수에 저장했습니다.");
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류 발생: " + e.getMessage());
        }
    }

    //변수를 파일에 저장
    public static void updateFile() {
        File file = new File(Globalvariable.DATAPATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(Globalvariable.Discord_webhook_url + "\n");
            writer.write(Globalvariable.korail_id + "\n");
            writer.write(Globalvariable.korail_pw + "\n");

            System.out.println("전역 변수 내용을 저장했습니다: " + Globalvariable.DATAPATH);
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류 발생: " + e.getMessage());
        }
    }
}
