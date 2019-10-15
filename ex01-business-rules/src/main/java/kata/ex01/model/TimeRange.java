package kata.ex01.model;

import kata.ex01.util.HolidayUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * ルールを表すモデル
 */
public class TimeRange {

    private LocalTime start;

    private LocalTime end;

    private List<LocalDate> dates;

    public TimeRange(List<LocalDate> dates, LocalTime start, LocalTime end) {
        this.dates = dates;
        this.start = start;
        this.end = end;
    }



    /**
     * 平日かつルールに適応するかを判定する。
     * 開始・終了のどちらかが範囲内なら適用されます。
     *
     * @return boolean 判定結果
     */
    public boolean matchAndIsWeekday(LocalDateTime from, LocalDateTime to) {

        for (LocalDate date : dates) {
            if (!HolidayUtils.isHoliday(date) && overlapWithRuleTime(from, to, date)) {
                return true;
            }
        }

        return false;
    }

    /**
     * ルールに適応するかを判定する。
     * 開始・終了のどちらかが範囲内なら適用されます。
     *
     * @return boolean 判定結果
     */
    public boolean match(LocalDateTime from, LocalDateTime to) {

        for (LocalDate date : dates) {
            if (overlapWithRuleTime(from, to, date)) {
                return true;
            }
        }

        return false;
    }

    private boolean overlapWithRuleTime(LocalDateTime from, LocalDateTime to, LocalDate date) {
        return !(from.isAfter(date.atTime(end)) || to.isBefore(date.atTime(start)));
    }
}
