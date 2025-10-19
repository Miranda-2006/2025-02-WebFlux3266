package com.parcial.dos.parcialdos.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountOwnerBalanceDTO {
    private String ownerName;
    private Double currentBalance;
}
