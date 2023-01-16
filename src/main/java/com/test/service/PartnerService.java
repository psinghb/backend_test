package com.test.service;

import com.test.command.LevelCommand;
import com.test.command.RegisterCommand;
import com.test.command.RewardsCommand;
import com.test.entity.Contract;
import com.test.entity.Partner;
import com.test.enums.ContractType;
import com.test.enums.Level;
import com.test.repository.ContractRepository;
import com.test.repository.PartnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final ContractRepository contractRepository;

    public Partner register(RegisterCommand registerCommand) {
        Optional<Partner> partnerOptional = partnerRepository.findById(registerCommand.getPartnerId());
        if (partnerOptional.isPresent())
            throw new RuntimeException("Given Partner already exists for id:" + registerCommand.getPartnerId());

        if (registerCommand.getParentPartnerId() != null && partnerRepository.findById(registerCommand.getParentPartnerId()).isEmpty()) {
            throw new RuntimeException("Parent Partner does not exists");
        }

        Partner parent = registerCommand.getParentPartnerId() != null ? partnerRepository.findById(registerCommand.getParentPartnerId()).get() : null;

        Partner partner = new Partner();
        partner.setId(registerCommand.getPartnerId());
        partner.setParent(parent);
        return partnerRepository.save(partner);
    }

    public Partner findById(long partnerId) {
        return partnerRepository.findById(partnerId).orElseThrow(() -> new RuntimeException("Given partner Id does not exists: " + partnerId));
    }

    public Set<Long> findAllSubPartners(Long partnerId) {
        Set<Long> all = partnerRepository.findSubPartnerIds(partnerId);
        if(all.isEmpty()) {
            all.add(partnerId);
            return all;
        }

        all.add(partnerId);
        while(true) {
            Set<Long> subs = partnerRepository.findSubPartnerIds(all);
            if(all.containsAll(subs)) {
                break;
            }
            all.addAll(subs);
        }

        return all;
    }

    public Level getLevel(LevelCommand levelCommand) {
        return getLevel(levelCommand.getPartnerId(), levelCommand.getYear(), levelCommand.getQuarter());
    }

    public Level getLevel(Long id, int year, int quarter) {
        var allPartnerIds = findAllSubPartners(id);
        var contractCount = contractRepository.countOfContractByPartnerIdInAndYearAndQuarter(allPartnerIds, year, quarter);
        return Level.fromSalesCount(contractCount);
    }

    public Long rewards(RewardsCommand levelCommand) {
        var allPartnerIds = findAllSubPartners(levelCommand.getPartnerId());
        long rewards = 0;
        Level level = getLevel(levelCommand);
        for(int i = 0; i < 8; i++) {
            var contracts = contractRepository.findByPartnerIdInAndYearAndQuarter(allPartnerIds, levelCommand.getYear() - i, levelCommand.getQuarter());
            for(Contract contract: contracts) {
                if(i ==0 && contract.getContractType().equals(ContractType.RABBIT)) {
                    rewards += 50;
                }

                if(contract.getPartner().getId().equals(levelCommand.getPartnerId())) {
                    rewards += level.getReward()*(i+1);
                } else {
                    Partner parent = contract.getPartner().getParent();
                    while (true) {
                        var parentId = parent.getId();
                        if(parentId == levelCommand.getPartnerId()) {
                            Level parentLevel = getLevel(parentId, levelCommand.getYear() - i, levelCommand.getQuarter());
                            rewards += parentLevel.diff(level) * (i + 1);
                            break;
                        }

                        parent = parent.getParent();
                    }
                }
            }
        }

        return rewards;
    }


}
