import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AutoUi {
    public static void run() {
        JFrame frame = new JFrame("예약 정보 입력");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(0, 4, 5, 5));

        List<String> dateList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusMonths(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (!today.isAfter(endDate)) {
            dateList.add(today.format(formatter));
            today = today.plusDays(1);
        }

        // ===== 텍스트 필드 생성 및 전역 변수 초기값 기입 =====
        JTextField discordField = new JTextField(Globalvariable.Discord_webhook_url);
        List<JCheckBox> seatCheckBoxes = new ArrayList<>();
        for (String option : Globalvariable.SEATOPTION) {
            seatCheckBoxes.add(new JCheckBox(option));
        }
        JPanel seatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        for (JCheckBox cb : seatCheckBoxes) {
            seatPanel.add(cb);
        }

        JTextField korailIdField = new JTextField(Globalvariable.korail_id);
        JPasswordField korailPwField = new JPasswordField(Globalvariable.korail_pw);

        JComboBox<String> departureComboBox = new JComboBox<>(Globalvariable.STATION_LIST.toArray(new String[0]));
        JComboBox<String> arrivalComboBox = new JComboBox<>(Globalvariable.STATION_LIST.toArray(new String[0]));

        JComboBox<String> dateComboBox = new JComboBox<>(dateList.toArray(new String[0]));
        JComboBox<String> timeComboBox = new JComboBox<>(Globalvariable.HOUR_LIST.toArray(new String[0]));

        // ===== UI에 항목 추가 =====
        frame.add(new JLabel("코레일 ID:"));
        frame.add(korailIdField);
        frame.add(new JLabel("코레일 PW:"));
        frame.add(korailPwField);
        
        frame.add(new JLabel("디스코드url:"));
        frame.add(discordField);
        frame.add(new JLabel("좌석 타입:"));
        frame.add(seatPanel);

        frame.add(new JLabel("승차역:"));
        frame.add(departureComboBox);
        frame.add(new JLabel("하차역:"));
        frame.add(arrivalComboBox);
        frame.add(new JLabel("날짜:"));
        frame.add(dateComboBox);
        frame.add(new JLabel("시간:"));
        frame.add(timeComboBox);

        // ===== 버튼 =====
        JButton submitButton = new JButton("제출");
        submitButton.addActionListener(e -> {
            // 버튼 클릭 시 현재 입력된 값 전역 변수에 업데이트
            Globalvariable.Discord_webhook_url = discordField.getText();
            Globalvariable.korail_id = korailIdField.getText();
            Globalvariable.korail_pw = new String(korailPwField.getPassword());
            Globalvariable.start_station = (String) departureComboBox.getSelectedItem();
            Globalvariable.end_station = (String) arrivalComboBox.getSelectedItem();
            String selectedDate = (String) dateComboBox.getSelectedItem();  // yyyy-MM-dd
            String selectedHour = (String) timeComboBox.getSelectedItem();  // HH
            Globalvariable.seatTypeSet.clear();
            for (int i = 0; i < seatCheckBoxes.size(); i++) {
                if (seatCheckBoxes.get(i).isSelected()) {
                    Globalvariable.seatTypeSet.add(Globalvariable.SEATOPTION[i]);
                    Globalvariable.seatTypeSet.add(Globalvariable.SEATOPTION[i] + "(매진임박)");
                }
            }

            LocalDate date = LocalDate.parse(selectedDate);
            String formatted = String.format("%02d%02d%s", date.getMonthValue(), date.getDayOfMonth(), selectedHour);
            Globalvariable.datetime = formatted;
            Filedata.updateFile();
            JOptionPane.showMessageDialog(frame, "입력 완료!");
            new Thread(() -> Automation.run()).start();
        });

        frame.add(submitButton);
        frame.setVisible(true);
    }

}
