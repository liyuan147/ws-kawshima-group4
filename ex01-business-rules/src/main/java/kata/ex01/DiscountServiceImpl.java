package kata.ex01;

import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;
import kata.ex01.model.VehicleFamily;
import kata.ex01.util.HolidayUtils;

import java.time.LocalDateTime;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {

    private boolean isWeekdayMorningOrEvening(HighwayDrive drive){
        // 休日またぎのケース対応
        // 休日→平日の場合
        if(HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate())){
            return !(drive.getExitedAt().getHour() < 6);
        }
        // 平日→休日の場合
        if(HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate())){
            return !(drive.getEnteredAt().getHour() > 20);
        }

        // 平日→平日の場合
        if(drive.getEnteredAt().getHour() < drive.getExitedAt().getHour()){
            return (drive.getEnteredAt().getHour() <= 9 && drive.getExitedAt().getHour() >= 6) ||
                    (drive.getEnteredAt().getHour() <= 20 && drive.getExitedAt().getHour() >= 17);
        } else {
            return (drive.getEnteredAt().getHour() >= 20 && drive.getExitedAt().getHour() >= 6) ||
                    (drive.getEnteredAt().getHour() <= 17 && drive.getExitedAt().getHour() <= 6);
        }
    }

    private boolean isMidnight(HighwayDrive drive){
        // 日付またぎ対応
        if(drive.getEnteredAt().getHour() < drive.getExitedAt().getHour()){
            return (drive.getEnteredAt().getHour() <= 4);
        } else {
            return (drive.getExitedAt().getHour() <= 4);
        }
    }

    @Override
    public long calc(HighwayDrive drive) {
        // 平日朝夕割引10回以上　地方
        if(!HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate()) ||
           !HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate())){
            if(isWeekdayMorningOrEvening(drive)){
                if(drive.getRouteType().equals(RouteType.RURAL)){
                    if(drive.getDriver().getCountPerMonth() >= 10){
                        return 50;
                    }
                    if (drive.getDriver().getCountPerMonth() >= 5) {
                        return 30;
                    }
                }
            }
        }

        // 深夜割引
        if (isMidnight(drive)) {
            return 30;
        }

        // 休日割引
        if (HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate()) || HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate())) {
            if (drive.getRouteType().equals(RouteType.RURAL)) {
                if (!drive.getVehicleFamily().equals(VehicleFamily.OTHER)) {
                    return 30;
                }
            }
        }



        return 0;
    }
}
