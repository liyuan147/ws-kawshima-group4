package kata.ex01;

import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;
import kata.ex01.model.VehicleFamily;
import kata.ex01.util.HolidayUtils;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {


    @Override
    public long calc(HighwayDrive drive) {

        //平日朝夕割引10回以上　地方
        if(((drive.getEnteredAt().getHour() >= 6 || drive.getEnteredAt().getHour() <= 9) ||
            (drive.getEnteredAt().getHour() >= 17 || drive.getEnteredAt().getHour() <= 20) ) ||
             ((drive.getExitedAt().getHour() >= 6 || drive.getExitedAt().getHour() <= 9) ||
              (drive.getExitedAt().getHour() >= 17 || drive.getExitedAt().getHour() <= 20))){
            if(!HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate()) ||
               !HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate())){
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

//        if ((!HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate()) ||
//             !HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate())) &&
//                ((drive.getEnteredAt().getHour() >= 6 || drive.getExitedAt().getHour() <= 9) ||
//                  (drive.getEnteredAt().getHour() >= 17 || drive.getExitedAt().getHour() <= 20)) &&
//                    drive.getRouteType().equals(RouteType.RURAL) &&
//                    drive.getDriver().getCountPerMonth() >= 10 ) {
//                      return 50;
//        }
//
//                  if (drive.getDriver().getCountPerMonth() >= 5) {
//                      return 30;
//                  }




        // 休日割引
        if (HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate()) || HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate())) {
            if (drive.getRouteType().equals(RouteType.RURAL)) {
                if (!drive.getVehicleFamily().equals(VehicleFamily.OTHER)) {
                    return 30;
                }
            }
        }

        // 深夜割引
        // fix 時刻判定
        if (drive.getEnteredAt().getHour() >= 0 && drive.getExitedAt().getHour() <= 4) {
            return 30;
        }

        return 0;
    }
}
