package com.test.entity;

import com.test.enums.ContractAction;
import com.test.enums.ContractType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Contract {
    @Id
    Long contractId;

    @ManyToOne
    private Partner partner;

    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @Enumerated(EnumType.STRING)
    private ContractAction contractAction;

    private LocalDate date;
}
