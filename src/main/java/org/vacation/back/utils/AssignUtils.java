package org.vacation.back.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vacation.back.common.VacationStatus;
import org.vacation.back.domain.Duty;
import org.vacation.back.domain.Member;
import org.vacation.back.repository.DutyRepository;
import org.vacation.back.repository.MemberRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class AssignUtils {

    private final DutyRepository dutyRepository;

    private final MemberRepository memberRepository;

    /**
     * 매달 1일에 해당 메소드 실행된다.
     * */
    public void assign(){

        Calendar calendar = Calendar.getInstance();
        Map<Integer, Member> map = new HashMap<>();

        int currentMonth = calendar.get(Calendar.MONTH) + 1; // 현재 몇월인지
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd"); //뒤에 yyyy-MM-dd 중에 마지막 dd 이다.
        List<Integer> excludeList = dutyRepository.findAll(currentMonth).stream() // 일단 신청한 Duty를 받아서 LocadateTime중 dd부분 날짜를 받는다
                .map(duty -> Integer.parseInt(duty.getDay().format(formatter))) // 1~31 중 제외를 해서 받아야 한다.
                .toList();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1); //이건 모르겠다 구글에ㅔ서 긁어옴 밑에 날짜를 받기 위함이 아닌가 싶음
        int lastDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); // 이번달 마지막 날 2월은 28일 5월은 31일
         // 테스트할 때는 currentMonth, lastDayOfMonth를 입력으로 받아야한다.

        for (int i = 1; i < lastDayOfMonth; i++) { // 1일부터 마지막(31)일까지
            if(!excludeList.contains(i)){ //위에서 신청한 날짜를 제외한 나머지를 map에 때려박는다.
                map.put(i,null);
            }
        } // 1~31일까지 신청한 날 제외하고 넣음

        List<Member> memberList = memberRepository.findAllActivation(); // 모든 Member가져온다.
        List<Integer> availableKeys = getAvailableKeys(excludeList); //exclude를 제외한 일들을 가져온다 -> 여기적으면 너무 더러워서 메소드로 뺌
        Random random = new Random(); //랜덤으로 떄려박는다

        for (Integer key : availableKeys){ // 신청 안 받은 날들
            Member member = memberList.get(random.nextInt(memberList.size())); // 가져온 Member를 랜덤으로 돌린ㄴ다.
            map.put(key, member);  // 그러고 map에 넣는다.map킼 값은 날짜이고 value는 Member객체이다. ->
        }       // 2일만 신청을 받았다면  2를 제외한 1~31 날에 Member가 랜덤으로 박힌다.
        String tempMonth = "";


        if(currentMonth < 10)  tempMonth ="0"+currentMonth; //Localdate 형식 맞추려함
        else tempMonth = Integer.toString(currentMonth);

        String date = LocalDate.now().getYear() +"-"+tempMonth;

        for (var entry : map.entrySet()) { //Map에 있는 데이터를 Duty로 만들어 저장한다.
            date = date.substring(0,7);
            date = date + "-" +(entry.getKey() < 10 ? "0"+entry.getKey() : entry.getKey());
            LocalDate temp = LocalDate.parse(date);
            Duty duty = Duty.builder()
                    .day(temp)
                    .member(entry.getValue())
                    .status(VacationStatus.WAITING)
                    .build();

            dutyRepository.save(duty);
        }
    }

    /**
     * @apiNote 1~31까지 중 제외한 수이다.
     * */
    private static List<Integer> getAvailableKeys(List<Integer> excludeList) {
        List<Integer> availableKeys = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            if (!excludeList.contains(i)) {
                availableKeys.add(i);
            }
        }
        return availableKeys;
    }
}
