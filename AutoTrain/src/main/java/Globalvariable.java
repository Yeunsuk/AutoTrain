import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Globalvariable {
    // 웹사이트 URL 
    public static final String KORAIL_URL = "https://www.korail.com/ticket/main";
    // 팝업
    public static final String CLOSE_POPUP_SELECTOR = "button.btn_by-blue.btn_pop-close";
    public static final String LOADING_POPUP_SELECTOR = "NetFunnel_Loading_Popup";
    public static final String LOADING_POPUP_TYPE03_SELECTOR = "div.type_tckRelay_03";
    public static final String LOADING_POPUP_TYPE03_CLOSE_SELECTOR = "button.btn_bn-blue.btn_pop-close";
    //로그인
    public static final String LOGIN_PART_BUTTON = "a.btnGoLogin";
    public static final String ID_SELECTOR = "div.loginInput input#id";
    public static final String PASSWORD_SELECTOR = "div.loginInput input#password";
    public static final String LOGIN_BUTTON = "div.btnWrap button.btn_bn-depblue";
    //정거장
    public static final String START_STATION_SELECTOR = "#container > section.section01.main-visual.sc-box > div.ticket_box > div > div.start > div > a";
    public static final String END_STATION_SELECTOR = "#container > section.section01.main-visual.sc-box > div.ticket_box > div > div.end > div > a";
    public static final String STATIONLIST_SELECTOR = ".travel-ch_list .list_wrap span.ch_tag";
    //시간
    public static final String DAY_START_SELECTOR = "#container > section.section01.main-visual.sc-box > div.ticket_box > div > div.day_start > div > a";
    public static final String MONTHTEXT_SELECTOR = "div.slick-slide.slick-active.slick-current p.date";
    public static final String NEXTMONTH_BUTTON = "#main > div.ReactModalPortal > div > div > div > div.con_Wrap > div > div.slick-slider.datepk_wrap.clear.slick-initialized > button.slick-arrow.slick-next";
    public static final String DAYTEXT_SELECTOR = "//div[contains(@class,'slick-slide') and contains(@class,'slick-active') and contains(@class,'slick-current')]//*[contains(text(),'";
    public static final String TIMETEXT_SELECTOR = "//div[contains(@class,'timeSelect')]//*[contains(text(),'";
    public static final String NEXTTIME_BUTTON = "#main > div.ReactModalPortal > div > div > div > div.con_Wrap > div > div.timeSelect > div > div > button.slick-arrow.slick-next";
    public static final String DAYCONFIRM_BUTTON = "#main > div.ReactModalPortal > div > div > div > div.con_Wrap > div > div.btn_wrap > button.btn_bn-blue"; 
    public static final String SEARCH_BUTTON = "#container > section.section01.main-visual.sc-box > div.ticket_box > div > button";
    //티켓
    public static final String TICKETLIST_SELECTOR = "div.tckWrap li.tckList.clear";
    public static final String TICKETTEXT_SELECTOR = "div.inner.type02";
    public static final String TICKET_PART_SELECTOR = "div.ticket_reserv_inner";
    public static final String TICKET_SHOW_BUTTON = "button.reserv_btn.active";
    public static final String PREBUY_TICKET_BUTTON = "button.btn_bn-blue02.reservbtn";
    public static final String TICKET_POPUP_CLOSE_BUTTON = "button.btn_close";
    public static final String TICKET_POPUP_SELECTOR = "span.blind";
    public static final String PREBUT_TICKET_PART_SELECTOR = "div.tck_inner";
    public static final String PREBUY_TICKET_CONFIRM_BUTTON = "button.btn_bn-blue";
    public static final String MORETICKET_BUTTON = "#tab_tab_info1 > ul > li:nth-child(1) > div > a";
    //UI
    public static final List<String> STATION_LIST = List.of(
        "서울", "용산", "광명", "영등포", "수원", "평택", "천안아산", "천안", "오송", "조치원",
        "대전", "서대전", "김천구미", "구미", "동대구", "대구", "경주", "울산(통도사)", "포항", "경산",
        "밀양", "부산", "구포", "창원중앙", "평창", "진부(오대산)", "강릉", "익산", "전주", "광주송정",
        "목포", "순천", "청량리", "여수EXPO", "동해", "정동진", "안동", "서원주", "원주", "마산",
        "행신", "나주", "정읍", "남원"
    );
    public static final List<String> HOUR_LIST = List.of(
        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
        "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"
    );
    public static final String[] SEATOPTION = {"일반식", "입석 + 좌석", "특실"};

    //입력데이터
    public static final String DATAPATH = "data.txt";
    public static String korail_id = "";
    public static String korail_pw = "";
    public static String start_station = "";
    public static String end_station = "";
    public static String datetime = "";
    public static String Discord_webhook_url = "";
    public static Set<String> seatTypeSet = new HashSet<>();
    

    public static String getNthSelector(int nth) {
        return ":nth-of-type(" + nth + ")";
    }

}
