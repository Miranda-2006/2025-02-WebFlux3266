package com.parcial.dos.parcialdos.account.service;

import com.example.accountservice.dto.*;
import com.example.accountservice.entity.Account;
import com.example.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;


    @Override
    public AccountResponseDTO createAccount(AccountRequestDTO request) {
        Account account = Account.builder()
                .accountNumber(request.getAccountNumber())
                .ownerName(request.getOwnerName())
                .currentBalance(request.getCurrentBalance())
                .accountType(request.getAccountType())
                .build();
        return toResponseDTO(accountRepository.save(account));
    }


    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public AccountResponseDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        return toResponseDTO(account);
    }


    public String updateBalance(Long id, Double newBalance) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        Double oldBalance = account.getCurrentBalance();
        account.setCurrentBalance(newBalance);
        accountRepository.save(account);

        return String.format(
                "La cuenta %s fue actualizada: balanceAnterior=%.2f, balanceActual=%.2f",
                account.getAccountNumber(), oldBalance, newBalance
        );
    }


    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }


    public AccountOwnerBalanceDTO getOwnerAndBalanceByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        return new AccountOwnerBalanceDTO(account.getOwnerName(), account.getCurrentBalance());
    }

    @Override
    public AccountResponseDTO updateAccount(Long id, AccountRequestDTO request) { return null; }

    @Override
    public AccountResponseDTO getAccountByNumber(String accountNumber) { return null; }

    @Override
    public AccountOwnerBalanceDTO getOwnerAndBalance(Long id) { return null; }


    private AccountResponseDTO toResponseDTO(Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .ownerName(account.getOwnerName())
                .currentBalance(account.getCurrentBalance())
                .accountType(account.getAccountType())
                .build();
    }
}
