package kata.ex01;

import kata.ex01.model.HighwayDrive;

/**
 * @author kawasima
 */
public interface DiscountService {
    /**
     * 割引率の計算を行う。
     * @param drive 走行情報
     * @return 割引率のパーセンテージ
     */
    long calc(HighwayDrive drive);
}
