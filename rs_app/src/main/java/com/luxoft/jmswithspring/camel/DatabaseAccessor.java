package com.luxoft.jmswithspring.camel;

import com.luxoft.jmswithspring.camel.internalid.InternalId;
import com.luxoft.jmswithspring.database.dao.SuperDAO;
import com.luxoft.jmswithspring.model.SecuritiesForBranches;
import com.luxoft.jmswithspring.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseAccessor {

    @Autowired
    private SuperDAO superDAO;
    @Autowired
    private InternalId internalId;

    /**
     * Save transaction in database.
     *
     * @param transaction that will be saved
     * @return transaction id
     */
    public int saveTransaction(Transaction transaction) {
        superDAO.safelyInsert(transaction);
        internalId.increment();
        return transaction.getId();
    }

    /**
     * Save securities for branch in database
     *
     * @param securitiesForBranches that will be saved
     */
    public void saveSecuritiesForBranch(SecuritiesForBranches securitiesForBranches) {
        superDAO.safelyInsert(securitiesForBranches);
    }

}
