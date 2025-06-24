import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Automation {
    public static void run() {
        WebDriver driver = new ChromeDriver();
        try{
            driver.get(Globalvariable.KORAIL_URL);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            while(Automation.closePopupIfExists(driver, Globalvariable.CLOSE_POPUP_SELECTOR)) {
                Thread.sleep(1000);
            }
            Automation.Login(driver, wait);

            Automation.selectStation(driver, wait, Globalvariable.START_STATION_SELECTOR, Globalvariable.start_station);
            Automation.selectStation(driver, wait, Globalvariable.END_STATION_SELECTOR, Globalvariable.end_station);

            Automation.selectDateAndTime(driver, wait, Globalvariable.datetime);
            Automation.selectTicket(driver, wait);
            Discord.succesWebhook();
            
        }catch (Exception e) {
           e.printStackTrace();
        }
    }

    //코레일 로그인
    public static void Login(WebDriver driver, WebDriverWait wait) {
        try {
            // 1. 로그인 버튼 클릭
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(Globalvariable.LOGIN_PART_BUTTON)));
            loginBtn.click();
            System.out.println("로그인 버튼 클릭");

            // 2. ID 입력
            WebElement idInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(Globalvariable.ID_SELECTOR)));
            idInput.clear();
            idInput.sendKeys(Globalvariable.korail_id);
            System.out.println("ID 입력");

            // 3. PW 입력
            WebElement pwInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(Globalvariable.PASSWORD_SELECTOR)));
            pwInput.clear();
            pwInput.sendKeys(Globalvariable.korail_pw);
            System.out.println("비밀번호 입력");

            // 4. 로그인 버튼 클릭
            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(Globalvariable.LOGIN_BUTTON)));
            submitBtn.click();
            System.out.println("로그인 시도");
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
            Discord.failWebhook("로그인, " + e.getMessage());
        }
    }

    //팝업 닫기
    public static boolean closePopupIfExists(WebDriver driver, String cssSelector) {
        try {
            WebElement closeButton = driver.findElement(By.cssSelector(cssSelector));
            if (closeButton.isDisplayed()) {
                closeButton.click();
                System.out.println("팝업 닫기 클릭 완료");
                return true;
            }
        } catch (NoSuchElementException e) {
            System.out.println("팝업 닫기 버튼이 없음");
            Discord.failWebhook("팝업 닫기 버튼 없음");
        }
        return false;
    }

    //정거장 선택
    public static void selectStation(WebDriver driver, WebDriverWait wait, String startStationSelector, String stationName) {
        try {
            // 시작역 선택창 클릭
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(startStationSelector)
            ));
            element.click();

            // 역 이름 인덱스 찾기
            int index = Globalvariable.STATION_LIST.indexOf(stationName);

            if (index != -1) {
                WebElement tag = driver.findElement(
                    By.cssSelector(Globalvariable.STATIONLIST_SELECTOR + Globalvariable.getNthSelector(index + 1))
                );

                // 스크롤
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tag);
                Thread.sleep(200);

                tag.click();
                System.out.println(stationName + " 역 클릭 완료!");
            } else {
                System.out.println(stationName + " 은(는) 리스트에 없습니다.");
            }
        } catch (Exception e) {
            System.out.println("역 선택 중 오류 발생: " + e.getMessage());
            Discord.failWebhook("역 선택, " + e.getMessage());
        }
    }

    // 요소 출력
    public static void PrintElement(WebDriver driver, WebDriverWait wait, String clickSelector, String listSelector) {
        try {
            // 클릭할 요소 대기 및 클릭
            WebElement clickTarget = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(clickSelector)));
            clickTarget.click();
            Thread.sleep(300);

            // 클릭 후 표시되는 요소들 대기 및 가져오기
            List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(listSelector)));

            // 요소 텍스트 출력
            for (WebElement item : items) {
                String text = item.getText().trim();
                if (!text.isEmpty()) {
                    System.out.println(text);
                }
            }
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
        }
    }

    //날짜 선택
    public static void selectDateAndTime(WebDriver driver, WebDriverWait wait, String date) {
        try {
            String month = ". " + date.substring(0,2) + ".";
            String day = date.substring(2,4);
            String time = date.substring(4,6);
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofMillis(500));
            WebElement clickTarget = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(Globalvariable.DAY_START_SELECTOR)));
            clickTarget.click();

            // 1: 날짜 요소 대기 후 추출
            WebElement dateElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(Globalvariable.MONTHTEXT_SELECTOR)));
            String dateText = dateElement.getText().trim();
            System.out.println(dateText);

            // 2: 월 선택
            if (!dateText.contains(month)) {
                WebElement timeNextButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(Globalvariable.NEXTMONTH_BUTTON)));
                timeNextButton.click();

                // 다음 달 로딩까지 잠시 대기
                wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.cssSelector(Globalvariable.MONTHTEXT_SELECTOR), month));
            }
            Thread.sleep(500);

            // 4: 날짜 클릭
            WebElement targetDay = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(Globalvariable.DAYTEXT_SELECTOR + day + "')]")));
            targetDay.click();
            System.out.println("날짜 선택");

            // 5: 시간 클릭
            int maxTries = 5;
            int attempt = 0;
            boolean timeSelected = false;

            while (attempt < maxTries) {
                try {
                    WebElement hour = wait1.until(ExpectedConditions.elementToBeClickable(
                        By.xpath(Globalvariable.TIMETEXT_SELECTOR + time + "')]")));
                    hour.click();
                    System.out.println("시간 선택");
                    timeSelected = true;
                    break; // 성공했으면 루프 종료
                } catch (TimeoutException e) {
                    System.out.println("시간 찾지 못함, 다음 버튼 클릭 시도 (" + (attempt + 1) + "/" + maxTries + ")");
                    WebElement timeNextButton = wait1.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector(Globalvariable.NEXTTIME_BUTTON)));
                    timeNextButton.click();
                }
                attempt++;
            }

            if (!timeSelected) {
                System.out.println("19시 선택 실패: 최대 시도 횟수 초과");
                return;
            }

            // 6: 확인 버튼 클릭
            WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(Globalvariable.DAYCONFIRM_BUTTON)));
            confirmBtn.click();
            System.out.println("확인 버튼 클릭");

            // 7: 조회 버튼 클릭
            WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(Globalvariable.SEARCH_BUTTON)));
            confirm.click();
            System.out.println("확인 버튼 클릭");

        } catch (Exception e) {
            System.out.println("에러 발생: " + e.getMessage());
            Discord.failWebhook("날짜 선택, " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 티켓 탐색
    public static void selectTicket(WebDriver driver, WebDriverWait wait) {
        try {
            while(true) {
                // 1. li 요소 목록 찾기
                List<WebElement> items = driver.findElements(By.cssSelector(Globalvariable.TICKETLIST_SELECTOR));

                for (WebElement item : items) {
                    try {
                        // 2. 각 li 내부의 div.inner.type02 요소 텍스트 가져오기
                        WebElement innerDiv = item.findElement(By.cssSelector(Globalvariable.TICKETTEXT_SELECTOR));
                        List<WebElement> subElements = innerDiv.findElements(By.xpath(".//*"));
                        for (WebElement el : subElements) {
                            String subText = el.getText();
                            if (Globalvariable.seatTypeSet.contains(subText)) {
                                el.click();  // 문자열 a가 포함된 하위 요소 클릭
                                buyTicket(driver, wait);
                                return;
                            }
                        }


                    }catch (Exception e) {
                        System.out.println("요소 처리 중 오류 발생: " + e.getMessage());
                        Discord.failWebhook("티켓 탐색 처리, " + e.getMessage());
                    }
                }
                // 2: 새로고침
                driver.navigate().refresh();
                //Thread.sleep(2 * 60 * 1000);
                Thread.sleep(3);

                while(loadingPopupExists(driver)) Thread.sleep(1000);
                try {
                    WebElement alertPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(Globalvariable.LOADING_POPUP_TYPE03_SELECTOR)));
                    WebElement confirmBtn = alertPopup.findElement(By.cssSelector(Globalvariable.LOADING_POPUP_TYPE03_CLOSE_SELECTOR));
                    confirmBtn.click();
                    System.out.println("정차 경고 팝업 확인 버튼 클릭");
                } catch (TimeoutException e) {
                    System.out.println("정차 경고 팝업 없음");
                    Discord.failWebhook("정차 경고 팝업, " + e.getMessage());
                }

                // 3: 팝업제거
                while(Automation.closePopupIfExists(driver, Globalvariable.CLOSE_POPUP_SELECTOR)) {
                    Thread.sleep(1000);
                }

                // 4: 더보기 버튼 클릭
                WebElement moreBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(Globalvariable.MORETICKET_BUTTON)));
                moreBtn.click();
                System.out.println("더보기 버튼 클릭");
            }
        } catch (Exception e) {
            System.out.println("에러 발생: " + e.getMessage());
            Discord.failWebhook("티켓 탐색, " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 로딩 대기
    public static boolean loadingPopupExists(WebDriver driver) {
        try {
            WebElement loadingPopup = driver.findElement(By.id(Globalvariable.LOADING_POPUP_SELECTOR));
            return loadingPopup.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // 장바구니 담기기
    public static void buyTicket(WebDriver driver, WebDriverWait wait) {
        try {
            while(loadingPopupExists(driver)) Thread.sleep(1000);
            // 1. 정차 경고 팝업이 있으면 확인 버튼 클릭
            try {
                WebElement alertPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(Globalvariable.LOADING_POPUP_TYPE03_SELECTOR)));
                WebElement confirmBtn = alertPopup.findElement(By.cssSelector(Globalvariable.LOADING_POPUP_TYPE03_CLOSE_SELECTOR));
                confirmBtn.click();
                System.out.println("정차 경고 팝업 확인 버튼 클릭");
            } catch (TimeoutException e) {
                System.out.println("정차 경고 팝업 없음");
            }

            // 2. 예매 영역 진입
            WebElement reservInner = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(Globalvariable.TICKET_PART_SELECTOR)));

            // 2-1. 예매 열기 버튼 있으면 클릭
            try {
                WebElement openBtn = reservInner.findElement(By.cssSelector(Globalvariable.TICKET_SHOW_BUTTON));
                wait.until(ExpectedConditions.elementToBeClickable(openBtn)).click();
                System.out.println("예매 열기 버튼 클릭");
            } catch (NoSuchElementException e) {
                System.out.println("예매 열기 버튼 없음");
            }

            // 2-2. 예매 버튼 클릭
            WebElement reserveBtn = reservInner.findElement(By.cssSelector(Globalvariable.PREBUY_TICKET_BUTTON));
            wait.until(ExpectedConditions.elementToBeClickable(reserveBtn)).click();
            System.out.println("예매 버튼 클릭");

            // 3. 예매 레이어 닫기 버튼이 있으면 클릭
            try {
                WebElement closeLayerBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(Globalvariable.TICKET_POPUP_CLOSE_BUTTON)));
                WebElement span = closeLayerBtn.findElement(By.cssSelector(Globalvariable.TICKET_POPUP_SELECTOR));
                if (span.getText().contains("레이어닫기")) {
                    closeLayerBtn.click();
                    System.out.println("레이어 닫기 버튼 클릭");
                }
            } catch (TimeoutException e) {
                System.out.println("예매 레이어 닫기 버튼 없음");
            }

            // 4. 장바구니 버튼 클릭
            WebElement tckInner = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(Globalvariable.PREBUT_TICKET_PART_SELECTOR)));
            WebElement cartBtn = tckInner.findElement(By.xpath(".//button[text()='장바구니']"));
            wait.until(ExpectedConditions.elementToBeClickable(cartBtn)).click();
            System.out.println("장바구니 버튼 클릭");

            // 5. 확인 버튼 클릭
            WebElement confirmBtnFinal = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(Globalvariable.PREBUY_TICKET_CONFIRM_BUTTON)));
            confirmBtnFinal.click();
            System.out.println("장바구니 확인 버튼 클릭");

        } catch (Exception e) {
            System.out.println("addToCartAfterClick 실행 중 오류: " + e.getMessage());
            Discord.failWebhook("장바구니, " + e.getMessage());
            e.printStackTrace();
        }
    }
}