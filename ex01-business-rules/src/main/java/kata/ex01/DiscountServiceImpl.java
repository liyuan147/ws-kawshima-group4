package kata.ex01;

import kata.ex01.model.*;
import kata.ex01.util.HolidayUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {

    @Override
    public long calc(final HighwayDrive drive) {
        List<LocalDate> drivingDates = new ArrayList<>();
        drivingDates.add(drive.getEnteredAt().toLocalDate());
        drivingDates.add(drive.getExitedAt().toLocalDate());
        TimeFrame utilizationTime = new TimeFrame(drivingDates, drive.getEnteredAt(), drive.getExitedAt());

        Rule morningRule = new Rule(LocalTime.of(6, 0), LocalTime.of(9, 0));
        Rule eveningRule = new Rule(LocalTime.of(17, 0), LocalTime.of(20, 0));
        Rule midNightRule = new Rule(LocalTime.of(0, 0), LocalTime.of(4, 0));

        // 平日朝夕割引10回以上　地方
        if ((utilizationTime.fixAndIsWeekday(morningRule) || utilizationTime.fixAndIsWeekday(eveningRule))) {
            if (drive.getRouteType().equals(RouteType.RURAL)) {
                if (drive.getDriver().getCountPerMonth() >= 10) {
                    return 50;
                }
                if (drive.getDriver().getCountPerMonth() >= 5) {
                    return 30;
                }
            }
        }

        // 深夜割引
        if (utilizationTime.fix(midNightRule)) {
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
