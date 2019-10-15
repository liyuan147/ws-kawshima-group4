package kata.ex01.model;

import java.time.LocalTime;

/**
 * ルールを表すモデル
 */
public class Rule {

    private LocalTime start;

    private LocalTime end;

    public Rule(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
