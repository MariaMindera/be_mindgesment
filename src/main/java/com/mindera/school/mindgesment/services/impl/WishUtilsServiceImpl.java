package com.mindera.school.mindgesment.services.impl;

import com.mindera.school.mindgesment.data.entities.TransactionEntity;
import com.mindera.school.mindgesment.data.entities.UserEntity;
import com.mindera.school.mindgesment.data.entities.WishEntity;
import com.mindera.school.mindgesment.data.repositories.TransactionRepository;
import com.mindera.school.mindgesment.data.repositories.UserRepository;
import com.mindera.school.mindgesment.data.repositories.WishRepository;
import com.mindera.school.mindgesment.exceptions.DontHaveUserInformation;
import com.mindera.school.mindgesment.exceptions.NotExits;
import com.mindera.school.mindgesment.exceptions.UserNotExists;
import com.mindera.school.mindgesment.services.WishUtilsService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class WishUtilsServiceImpl implements WishUtilsService {

    private final TransactionRepository transactionRepository;

    private final WishRepository wishRepository;

    private final UserRepository userRepository;

    private final MapperFacade mapper;

    @Autowired
    public WishUtilsServiceImpl(TransactionRepository transactionRepository, WishRepository wishRepository, UserRepository userRepository, MapperFacade mapper) {
        this.transactionRepository = transactionRepository;
        this.wishRepository = wishRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserEntity findUserEntity(final String username) {
        return userRepository
                .findByUsername(username)
                .map(userEntity -> mapper.map(userEntity, UserEntity.class))
                .orElseThrow(() -> new UserNotExists(username));
    }

    @Override
    public WishEntity findWishEntity(String wishId) {
        return wishRepository.findById(wishId)
                .map(wishEntity -> mapper.map(wishEntity, WishEntity.class))
                .orElseThrow(() -> new NotExits("Wish", wishId));
    }

    @Override
    public Map<String, Double> getTotalIncomeAndExpenseByUser(UserEntity user) {
        Double totalIncome;
        Double totalExpense;
        YearMonth dateOfFirstTransactionUser;

        if (transactionRepository.countByUser(user) > 0) {
            totalIncome = transactionRepository.totalIncomeInHalfAYearByUser(user.getId());
            totalExpense = transactionRepository.totalExpenseInHalfAYearByUser(user.getId());

            dateOfFirstTransactionUser = YearMonth.from(transactionRepository.dateOfFirstTransactionByUser(user.getId()));

            if (dateOfFirstTransactionUser.isBefore(YearMonth.now().minusMonths(5))) {
                totalIncome = totalIncome / 6;
                totalExpense = totalExpense / 6;
            } else {
                var differenceMonths = dateOfFirstTransactionUser.until(YearMonth.now(), ChronoUnit.MONTHS);

                if (differenceMonths == 0) {
                    if (user.getEarnings() > 0 || user.getExpenses() > 0) {
                        totalIncome = user.getEarnings();
                        totalExpense = user.getExpenses();
                    } else {
                        throw new DontHaveUserInformation();
                    }
                } else {
                    totalIncome = totalIncome / differenceMonths;
                    totalExpense = totalExpense / differenceMonths;
                }
            }
        } else {
            if (user.getEarnings() > 0 || user.getExpenses() > 0) {
                totalIncome = user.getEarnings();
                totalExpense = user.getExpenses();
            } else {
                throw new DontHaveUserInformation();
            }
        }

        var response = new HashMap<String, Double>();
        response.put("totalIncome", totalIncome);
        response.put("totalExpense", totalExpense);
        return response;
    }

    @Override
    public Double getProposalAmount(Double totalIncome, Double totalExpense) {
        var effortRate = 1 - totalExpense / totalIncome;
        if (effortRate > 0.33) effortRate = 0.33;
        return totalIncome * effortRate;
    }

    @Override
    public YearMonth calculateData(double total, double balance, double proposalAmount) {
        return YearMonth.now()
                .plusMonths((int) Math.ceil((total - balance) / proposalAmount));
    }

    @Override
    public Long getBalanceWish(WishEntity wish) {
        if (wish.getTransactions() == null || wish.getTransactions().isEmpty())
            return 0L;

        return wish.getTransactions().stream()
                .map(TransactionEntity::getAmount)
                .reduce(0.00, Double::sum)
                .longValue();
    }
}
