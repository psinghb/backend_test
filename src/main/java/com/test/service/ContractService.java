package com.test.service;

import com.opencsv.CSVReader;
import com.test.command.LoadCommand;
import com.test.entity.Contract;
import com.test.entity.Partner;
import com.test.enums.ContractAction;
import com.test.enums.ContractType;
import com.test.repository.ContractRepository;
import com.test.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileReader;

@Service
@AllArgsConstructor
public class ContractService {

    private final PartnerService partnerService;
    private final ContractRepository contractRepository;

    public void load(LoadCommand command) throws Exception {
        var path = command.getFilename();
        FileReader reader = new FileReader(path);
        CSVReader csvReader = new CSVReader(reader);

        String[] nextRecord;
        while ((nextRecord = csvReader.readNext()) != null) {
            saveContract(nextRecord);
        }
    }

    protected Contract saveContract(String[] record) {
        if(record.length != 5) throw new RuntimeException("CSV not in proper format");
        long partnerId = Util.toLong(record[0]);
        Partner partner = partnerService.findById(partnerId);
        long contractId = Util.toLong(record[1]);
        if(contractRepository.findById(contractId).isPresent()) {
            throw new RuntimeException("CSV file contains duplicate contract id: " + contractId);
        }

        Contract contract = new Contract();
        contract.setContractId(contractId);
        contract.setPartner(partner);
        contract.setContractType(ContractType.from(record[2]));
        contract.setContractAction(ContractAction.from(record[4]));
        contract.setDate(Util.parseDate(record[3]));
        return contractRepository.save(contract);
    }
}


