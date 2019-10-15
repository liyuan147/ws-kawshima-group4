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

        TimeRange morningTime = new TimeRange(drivingDates, LocalTime.of(6, 0), LocalTime.of(9, 0));
        TimeRange eveningTime = new TimeRange(drivingDates, LocalTime.of(17, 0), LocalTime.of(20, 0));
        TimeRange midnightTime = new TimeRange(drivingDates, LocalTime.of(0, 0), LocalTime.of(4, 0));

        // 平日朝夕割引10回以上　地方
        if ((morningTime.matchAndIsWeekday(drive.getEnteredAt(), drive.getExitedAt()) || eveningTime.matchAndIsWeekday(drive.getEnteredAt(), drive.getExitedAt()))) {
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
        if (midnightTime.match(drive.getEnteredAt(), drive.getExitedAt())) {
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
