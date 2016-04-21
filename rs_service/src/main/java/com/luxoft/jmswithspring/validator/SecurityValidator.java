package com.luxoft.jmswithspring.validator;

import com.luxoft.jmswithspring.database.dao.SecuritiesForBranchesDAO;
import com.luxoft.jmswithspring.model.AccessType;
import com.luxoft.jmswithspring.model.Security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecurityValidator implements Validator<Security> {
    private static final Logger log = LoggerFactory.getLogger(SecurityValidator.class);

    @Autowired
    private SecuritiesForBranchesDAO dao;

    @Override
    public boolean validate(Security security) {

        List<Integer> listOfIds = security.getSecurityIds();
        for (int secId : listOfIds) {
            if (dao.getAccessType(secId).equals(AccessType.FORBID)) {
                log.info("Security with ID:{} not allowed for branch with ID:{}", secId, security.getBranchId());
                return false;
            }
        }

        log.info("All securities are allowed");
        return true;
    }

}
