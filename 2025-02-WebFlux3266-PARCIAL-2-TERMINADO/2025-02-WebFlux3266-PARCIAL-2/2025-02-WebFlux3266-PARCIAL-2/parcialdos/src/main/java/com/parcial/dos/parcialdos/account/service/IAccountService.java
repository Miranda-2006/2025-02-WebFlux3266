package com.parcial.dos.parcialdos.account.service;

import com.example.accountservice.dto.*;
import java.util.List;

public interface IAccountService {
    AccountResponseDTO createAccount(AccountRequestDTO request);
    AccountResponseDTO updateAccount(Long id, AccountRequestDTO request);
    AccountResponseDTO getAccountById(Long id);
    AccountResponseDTO getAccountByNumber(String accountNumber);
    List<AccountResponseDTO> getAllAccounts();
    void deleteAccount(Long id);
    AccountOwnerBalanceDTO getOwnerAndBalance(Long id);
}
