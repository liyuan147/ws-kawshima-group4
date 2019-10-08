package kata.ex01;

import kata.ex01.model.HighwayDrive;
import kata.ex01.model.RouteType;
import kata.ex01.model.VehicleFamily;
import kata.ex01.util.HolidayUtils;

/**
 * @author kawasima
 */
public class DiscountServiceImpl implements DiscountService {

    /**
     * 平日の朝夕割引適用時間帯か判定します
     * 平日 6時〜9時, 17時〜20時
     * 開始・終了のどちらかが範囲内なら適用されます。
     *
     * @param drive 走行記録
     * @return boolean 判定結果
     */
    private boolean isWeekdayMorningOrEvening(final HighwayDrive drive) {
        final int enteredHour = drive.getEnteredAt().getHour();
        final int exitedHour = drive.getExitedAt().getHour();

        // 休日またぎのケース対応
        // 休日→平日の場合
        if (HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate())) {
            return !(exitedHour < 6);
        }
        // 平日→休日の場合
        if (HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate())) {
            return !(enteredHour > 20);
        }

        // 平日→平日の場合
        if (enteredHour < exitedHour) {
            return (enteredHour <= 9 && exitedHour >= 6) ||
                    (enteredHour <= 20 && exitedHour >= 17);
        } else {
            return (enteredHour >= 20 && exitedHour >= 6) ||
                    (enteredHour <= 17 && exitedHour <= 6);
        }
    }

    /**
     * 深夜割引適用時間帯か判定します
     * 毎日0時〜4時
     *
     * @param drive 走行記録
     * @return boolean 判定結果
     */
    private boolean isMidnight(final HighwayDrive drive) {
        final int enteredHour = drive.getEnteredAt().getHour();
        final int exitedHour = drive.getExitedAt().getHour();

        // 日付またぎ対応
        if (enteredHour < exitedHour) {
            return (enteredHour <= 4);
        } else {
            return (exitedHour <= 4);
        }
    }

    @Override
    public long calc(final HighwayDrive drive) {
        // 平日朝夕割引10回以上　地方
        if (!HolidayUtils.isHoliday(drive.getEnteredAt().toLocalDate()) ||
                !HolidayUtils.isHoliday(drive.getExitedAt().toLocalDate())) {
            if (isWeekdayMorningOrEvening(drive)) {
                if (drive.getRouteType().equals(RouteType.RURAL)) {
                    if (drive.getDriver().getCountPerMonth() >= 10) {
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
