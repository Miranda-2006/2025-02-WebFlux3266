package com.parcial.dos.parcialdos.account.dto;

import lombok.Data;

@Data
public class AccountRequestDTO {
    private String accountNumber;
    private String ownerName;
    private Double currentBalance;
    private Boolean accountType;
}
