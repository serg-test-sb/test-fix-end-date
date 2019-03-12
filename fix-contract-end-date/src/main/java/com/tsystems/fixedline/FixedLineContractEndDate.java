package com.tsystems.fixedline;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class FixedLineContractEndDate {

    private LocalDate getEndDate(LocalDate beginContractDate, LocalDate currentDate, int contractDurationTime,
                                String contractDurationTimeType, int contractRenewalTime, String contractRenewalTimeType)
            throws Exception {
        LocalDate endContractDate;
        if (contractDurationTime <= 0) {
            throw new Exception("Not valid contract duration time");
        }
        if (contractRenewalTime <= 0) {
            throw new Exception("Not valid contract renewal time");
        }
        switch (contractDurationTimeType) {
            case "JHR":
                endContractDate=beginContractDate.plusYears(contractDurationTime);
                break;
            case "MON":
                endContractDate=beginContractDate.plusMonths(contractDurationTime);
                break;
            case "TAG":
                endContractDate=beginContractDate.plusDays(contractDurationTime);
                break;
            default:
                throw new Exception("Not valid contract duration time type");
        }
        if (currentDate.isBefore(endContractDate) || currentDate.equals(endContractDate)) {
            return endContractDate;
        }

        switch (contractRenewalTimeType) {
            case "JHR":
                while (currentDate.isAfter(endContractDate)) {
                    endContractDate=endContractDate.plusYears(contractRenewalTime);
                }
                break;
            case "MON":
                while (currentDate.isAfter(endContractDate)) {
                    endContractDate=endContractDate.plusMonths(contractRenewalTime);
                }
                break;
            case "TAG":
                while (currentDate.isAfter(endContractDate)) {
                    endContractDate=endContractDate.plusDays(contractRenewalTime);
                }
                break;
            default:
                throw new Exception("Not valid contract renewal time type");
        }

            return endContractDate;
    }




    String getKondition(Vertrag vertrag, LocalDate currentDate) throws Exception{
        for (Kondition i : vertrag.veertragsgegenstand.konditionList) {
            if (i.art.matches("Z.02")) {
                return getEndDate(vertrag.veertragsgegenstand.istTermin.vertragsBeginnTerminIst,
                        currentDate,i.vertragsLaufZeit.anzahl,i.vertragsLaufZeit.einheit,
                        i.verlaengerungsFrist.anzahl, i.verlaengerungsFrist.einheit)
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }
        }
        throw new Exception("There is no art contains 'Zx02' in the contract");
    }
}


