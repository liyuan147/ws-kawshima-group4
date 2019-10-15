package kata.ex01.model;


import kata.ex01.util.HolidayUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 期間を表すモデル。
 */
public class TimeFrame {

    private LocalDateTime start;

    private LocalDateTime end;

    private List<LocalDate> dates;

    public TimeFrame(List<LocalDate> dates, LocalDateTime start, LocalDateTime end) {
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
    public boolean fixAndIsWeekday(Rule rule) {

        for (LocalDate date : dates) {
            if (!HolidayUtils.isHoliday(date) && overlapWithRuleTime(date, rule)) {
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
    public boolean fix(Rule rule) {

        for (LocalDate date : dates) {
            if (overlapWithRuleTime(date, rule)) {
                return true;
            }
        }

        return false;
    }

    private boolean overlapWithRuleTime(LocalDate date, Rule rule) {
        return !(start.isAfter(date.atTime(rule.getEnd())) || end.isBefore(date.atTime(rule.getStart())));
    }
}
