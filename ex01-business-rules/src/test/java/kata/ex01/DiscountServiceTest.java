package kata.ex01;

import kata.ex01.model.Driver;
import kata.ex01.model.HighwayDrive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static kata.ex01.model.RouteType.RURAL;
import static kata.ex01.model.RouteType.URBAN;
import static kata.ex01.model.VehicleFamily.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kawasima
 */
public class DiscountServiceTest {
    DiscountService discountService;
    private Driver driver(int usingCount) {
        Driver driver = new Driver();
        driver.setCountPerMonth(usingCount);
        return driver;
    }

    @BeforeEach
    void setUp() {
        discountService = new DiscountServiceImpl();
    }

    @Test
    public void test平日朝夕割引50() {
        HighwayDrive drive = new HighwayDrive();

        drive.setEnteredAt(LocalDateTime.of(2019, 10, 1, 23, 0));
        drive.setExitedAt(LocalDateTime.of(2019, 10, 2, 6, 30));
        drive.setDriver(driver(10));
        drive.setVehicleFamily(STANDARD); // 普通車
        drive.setRouteType(RURAL); // 地方

        assertThat(discountService.calc(drive)).isEqualTo(50);
    }

    @Test
    public void test平日朝夕割引30() {
        HighwayDrive drive = new HighwayDrive();

        drive.setEnteredAt(LocalDateTime.of(2019, 10, 1, 23, 0));
        drive.setExitedAt(LocalDateTime.of(2019, 10, 2, 6, 30));
        drive.setDriver(driver(8));
        drive.setVehicleFamily(STANDARD); // 普通車
        drive.setRouteType(RURAL); // 地方

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test平日朝夕割引0() {
        HighwayDrive drive = new HighwayDrive();

        drive.setEnteredAt(LocalDateTime.of(2019, 10, 1, 6, 0));
        drive.setExitedAt(LocalDateTime.of(2019, 10, 1, 10, 0));
        drive.setDriver(driver(1));
        drive.setVehicleFamily(STANDARD); // 普通車
        drive.setRouteType(RURAL); // 地方

        assertThat(discountService.calc(drive)).isEqualTo(0);
    }

    @Test
    public void test深夜割引(){
        HighwayDrive drive = new HighwayDrive();

        drive.setEnteredAt(LocalDateTime.of(2019, 10, 2, 0, 1));
        drive.setExitedAt(LocalDateTime.of(2019, 10, 2, 3, 30));
        drive.setDriver(driver(1));
        drive.setVehicleFamily(STANDARD); // 普通車
        drive.setRouteType(RURAL); // 地方

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test休日割引その他地方(){
        HighwayDrive drive = new HighwayDrive();

        drive.setEnteredAt(LocalDateTime.of(2019, 10, 13, 22, 0));
        drive.setExitedAt(LocalDateTime.of(2019, 10, 13, 23, 30));
        drive.setDriver(driver(1));
        drive.setVehicleFamily(OTHER); // その他
        drive.setRouteType(RURAL); // 地方

        assertThat(discountService.calc(drive)).isEqualTo(0);
    }

    @Test
    public void test休日割引都市(){
        HighwayDrive drive = new HighwayDrive();

        drive.setEnteredAt(LocalDateTime.of(2019, 10, 13, 22, 0));
        drive.setExitedAt(LocalDateTime.of(2019, 10, 13, 23, 30));
        drive.setDriver(driver(1));
        drive.setVehicleFamily(MOTORCYCLE); // 二輪車
        drive.setRouteType(URBAN); // 都市

        assertThat(discountService.calc(drive)).isEqualTo(0);
    }

    @Test
    public void test休日割引普通車(){
        HighwayDrive drive = new HighwayDrive();

        drive.setEnteredAt(LocalDateTime.of(2019, 10, 13, 23, 0));
        drive.setExitedAt(LocalDateTime.of(2019, 10, 14, 6, 30));
        drive.setDriver(driver(1));
        drive.setVehicleFamily(STANDARD); // 普通車
        drive.setRouteType(RURAL); // 地方

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

    @Test
    public void test休日朝夕は休日割が適用される() {
        HighwayDrive drive = new HighwayDrive();
        drive.setEnteredAt(LocalDateTime.of(2016, 4, 1, 23, 0));
        drive.setExitedAt(LocalDateTime.of(2016, 4, 2, 6, 30));
        drive.setDriver(driver(10));
        drive.setVehicleFamily(STANDARD);
        drive.setRouteType(RURAL);

        assertThat(discountService.calc(drive)).isEqualTo(30);
    }

}
