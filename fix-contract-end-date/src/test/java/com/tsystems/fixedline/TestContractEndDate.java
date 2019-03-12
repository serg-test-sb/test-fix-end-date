package com.tsystems.fixedline;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.Month;
import org.testng.annotations.Test;

public class TestContractEndDate {

    private FixedLineContractEndDate fixedLineContractEndDate = new FixedLineContractEndDate();

    private Vertrag getContract(List<Kondition> konditions, LocalDate startContractDate) {
        return new Vertrag(
                new Veertragsgegenstand(
                        konditions,
                        new IstTermin(startContractDate)
                ));
    }

    private List<Kondition> getRelevantContract() {
        List<Kondition> konditions = new ArrayList<Kondition>();

        Kondition kondition1 = new Kondition("MC44",
                new VertragsLaufZeit(10, "TAG"),
                new VerlaengerungsFrist(1, "MON")

        );

        Kondition kondition2 = new Kondition("ZC02",
                new VertragsLaufZeit(12, "MON"),
                new VerlaengerungsFrist(9, "MON")
        );

        Kondition kondition3 = new Kondition("ZK02",
                new VertragsLaufZeit(5, "TAG"),
                new VerlaengerungsFrist(2, "JHR")
        );

        Kondition kondition4 = new Kondition("ZUIT",
                new VertragsLaufZeit(0, ""),
                new VerlaengerungsFrist(0, "")
        );


        konditions.add(kondition1);
        konditions.add(kondition2);
        konditions.add(kondition3);
        konditions.add(kondition4);

        return konditions;
    }

    private List<Kondition> getNoRelevantContract() {
        List<Kondition> konditions = new ArrayList<Kondition>();
        Kondition kondition1 = new Kondition("ZK03",
                new VertragsLaufZeit(1, "MON"),
                new VerlaengerungsFrist(1, "JHR")
        );

        Kondition kondition2 = new Kondition("02HG",
                new VertragsLaufZeit(0, ""),
                new VerlaengerungsFrist(0, "")
        );

        konditions.add(kondition1);
        konditions.add(kondition2);
        return konditions;
    }


    private List<Kondition> getNoValidTimePeriodNegative() {
        List<Kondition> konditions = new ArrayList<Kondition>();
        Kondition kondition1 = new Kondition("ZC02",
                new VertragsLaufZeit(-1, "JHR"),
                new VerlaengerungsFrist(-9, "MON")
        );
        konditions.add(kondition1);
        return konditions;
    }

    private List<Kondition> getNoValidTimePeriodZero() {
        List<Kondition> konditions = new ArrayList<Kondition>();
        Kondition kondition1 = new Kondition("ZC02",
                new VertragsLaufZeit(0, "JHR"),
                new VerlaengerungsFrist(0, "MON")
        );
        konditions.add(kondition1);
        return konditions;
    }

    public List<Kondition> getNoValidTypeDurationPeriod() {
        List<Kondition> konditions = new ArrayList<Kondition>();
        Kondition kondition1 = new Kondition("ZC02",
                new VertragsLaufZeit(1, "JJJ"),
                new VerlaengerungsFrist(12, "MON")
        );
        konditions.add(kondition1);
        return konditions;
    }

    private List<Kondition> getNoValidTypeRenewalPeriod() {
        List<Kondition> konditions = new ArrayList<Kondition>();
        Kondition kondition1 = new Kondition("ZC02",
                new VertragsLaufZeit(1, "MON"),
                new VerlaengerungsFrist(12, "MMM")
        );
        konditions.add(kondition1);
        return konditions;
    }

    @Test
    public void check2RenewalTime() throws Exception{
        LocalDate currentDate = LocalDate.of(2017, Month.AUGUST, 30);
        LocalDate startConstractDate = LocalDate.of(2015, Month.JUNE,15);
        Assert.assertEquals(fixedLineContractEndDate.getKondition(getContract(getRelevantContract(), startConstractDate),currentDate), "15.12.2017", "Check of the end date of the contract with the two renewal periods has failed");
    }
    @Test
    public void check1RenewalTime() throws Exception{
        LocalDate currentDate =LocalDate.of(2017, Month.AUGUST,26);
        LocalDate startConstractDate = LocalDate.of(2016, Month.JUNE,15);
        Assert.assertEquals(fixedLineContractEndDate.getKondition(getContract(getRelevantContract(), startConstractDate),currentDate), "15.03.2018", "Check of the end date of the contract with the one renewal period has failed");
    }
    @Test
    public void checkNoRenewalTime() throws Exception{
        LocalDate currentDate =LocalDate.of(2017, Month.AUGUST,26);
        LocalDate startConstractDate = LocalDate.of(2017, Month.JUNE,15);
        Assert.assertEquals(fixedLineContractEndDate.getKondition(getContract(getRelevantContract(), startConstractDate),currentDate), "15.06.2018", "Check of the end date of the contract without a renewal period has failed");
    }
    @Test
    public void checkStartDateEqualCurrentDate() throws Exception{
        LocalDate currentDate =LocalDate.of(2018, Month.JUNE,15);
        LocalDate startConstractDate = LocalDate.of(2018, Month.JUNE,15);
        Assert.assertEquals(fixedLineContractEndDate.getKondition(getContract(getRelevantContract(), startConstractDate),currentDate), "15.06.2019", "Check of the start date of the contract equal current date has failed");
    }
    @Test
    public void checkCurrentDateEqualEndDateDurationTime() throws Exception{
        LocalDate currentDate =LocalDate.of(2019, Month.JUNE,15);
        LocalDate startConstractDate = LocalDate.of(2018, Month.JUNE,15);
        Assert.assertEquals(fixedLineContractEndDate.getKondition(getContract(getRelevantContract(), startConstractDate),currentDate), "15.06.2019", "Check of the end date of the contract equal the end date of the contract duration time has failed");
    }
    @Test
    public void checkCurrentDateEqualEndDateRenewalTime() throws Exception{
        LocalDate currentDate =LocalDate.of(2020, Month.MARCH,15);
        LocalDate startConstractDate = LocalDate.of(2018, Month.JUNE,15);
        Assert.assertEquals(fixedLineContractEndDate.getKondition(getContract(getRelevantContract(), startConstractDate),currentDate), "15.03.2020", "Check of the end date of the contract equal the end date of the contract renewal time has failed");
    }
    @Test
    public void checkFutureStartDate() throws Exception{
        LocalDate currentDate =LocalDate.of(2017, Month.AUGUST,26);
        LocalDate startConstractDate = LocalDate.of(2018, Month.JUNE,15);
        Assert.assertEquals(fixedLineContractEndDate.getKondition(getContract(getRelevantContract(), startConstractDate),currentDate), "15.06.2019", "Check of the end date of the contract with a future start date has failed");
    }
    @Test(expectedExceptions = Exception.class)
    public void checkZeroTimePeriod() throws Exception{
        LocalDate currentDate =LocalDate.of(2017, Month.AUGUST,26);
        LocalDate startConstractDate = LocalDate.of(2017, Month.JUNE,15);
        fixedLineContractEndDate.getKondition(getContract(getNoValidTimePeriodZero(), startConstractDate),currentDate);
    }
    @Test(expectedExceptions = Exception.class)
    public void checkNegativeTimePeriod() throws Exception{
        LocalDate currentDate =LocalDate.of(2017, Month.AUGUST,26);
        LocalDate startConstractDate = LocalDate.of(2017, Month.JUNE,15);
        fixedLineContractEndDate.getKondition(getContract(getNoValidTimePeriodNegative(), startConstractDate),currentDate);

    }
    @Test(expectedExceptions = Exception.class)
    public void checkNoRelevantContract() throws Exception{
        LocalDate currentDate =LocalDate.of(2017, Month.AUGUST,26);
        LocalDate startConstractDate = LocalDate.of(2017, Month.JUNE,15);
        fixedLineContractEndDate.getKondition(getContract(getNoRelevantContract(), startConstractDate),currentDate);

    }
    @Test(expectedExceptions = Exception.class)
    public void checkNoValidTypeDurationPeriod() throws Exception{
        LocalDate currentDate =LocalDate.of(2017, Month.AUGUST,26);
        LocalDate startConstractDate = LocalDate.of(2017, Month.JUNE,15);
        fixedLineContractEndDate.getKondition(getContract(getNoValidTypeDurationPeriod(), startConstractDate),currentDate);

    }
    @Test(expectedExceptions = Exception.class)
    public void checkNoValidTypeRenewalPeriod() throws Exception{
        LocalDate currentDate =LocalDate.of(2017, Month.AUGUST,26);
        LocalDate startConstractDate = LocalDate.of(2017, Month.JUNE,15);
        fixedLineContractEndDate.getKondition(getContract(getNoValidTypeRenewalPeriod(), startConstractDate),currentDate);

    }


}
