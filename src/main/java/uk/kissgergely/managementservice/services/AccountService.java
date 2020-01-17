package uk.kissgergely.managementservice.services;
import java.util.List;

import org.springframework.stereotype.Service;

import uk.kissgergely.managementservice.entities.AccountEntity;

@Service
public interface AccountService {

	List<AccountEntity> getAllAccounts ();

	
}
