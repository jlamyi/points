package com.james.points.services;

import com.james.points.entity.Transaction;

import java.util.*;

public class PointsService extends Object {
    private HashMap<String, Integer> balanceMap = new HashMap<>();
    private List<Transaction> history = new ArrayList<>();
    private int historyStartIndex = 0;
    private Integer updatedValue;

    public void addToBalance (String payerName, Integer points) throws PointsServiceException {
        Integer balance = balanceMap.get(payerName);

        if (balance == null) {
            if (points < 0) {
                throw new PointsServiceException("new transaction causes balance to be negative! Aborted.");
            } else {
                balanceMap.put(payerName, points);
            }
        } else {
            if (points + balance < 0) {
                throw new PointsServiceException("new transaction causes balance to be negative! Aborted.");
            } else {
                balanceMap.put(payerName, points + balance);
            }
        }
    }

    public void addTransaction (Transaction trans) throws PointsServiceException {
        String payerName = trans.getPayerName();
        Integer points = trans.getPoints();

        try {
            addToBalance(payerName, points);
            history.add(trans);
        } catch (PointsServiceException e) {
            throw e;
        }
    }

    public void addToDeductionMap(HashMap<String, Integer> deductionMap, String payerName, Integer points) {
        Integer deductedPoints = deductionMap.get(payerName);

        if (deductedPoints == null) {
            deductionMap.put(payerName, points);
        } else {
            deductionMap.put(payerName, deductedPoints + points);
        }
    }

    public List<Transaction> deductPoints(int targetPoints) throws PointsServiceException {
        HashMap<String, Integer> deductionMap = new HashMap<>();
        List<Transaction> rtn = new ArrayList<>();

        if (balanceMap.size() == 0) {
            if (targetPoints == 0) {
                return rtn;
            }
            throw new PointsServiceException("No record in balance! Aborted.");
        }

        for (int i = historyStartIndex; i < history.size(); i++) {
            Transaction trans = history.get(i);
            String payerName = trans.getPayerName();
            Integer points = (i == historyStartIndex && updatedValue != null) ? updatedValue : trans.getPoints();
            Integer deductionPoints = points >= targetPoints ? targetPoints : points;

            try {
                addToDeductionMap(deductionMap, payerName, -deductionPoints);
            } catch (PointsServiceException e) {
                throw e;
            }

            if (points >= targetPoints) {
                for (String key : deductionMap.keySet()) {
                    rtn.add(new Transaction.Builder()
                            .payerName(key)
                            .points(deductionMap.get(key))
                            .transactionDate(new Date())
                            .build());
                    addToBalance(key, deductionMap.get(key));
                }
                updatedValue = points - targetPoints;
                historyStartIndex = i;
                return rtn;
            }

            targetPoints -= points;
        }

        throw new PointsServiceException("No enough points for deduction! Aborted.");
    }

    public HashMap<String, Integer> getBalance() {
        return balanceMap;
    }
}
