package uk.kissgergely.managementservice.dtos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.kissgergely.managementservice.entities.AccountEntity;
import uk.kissgergely.managementservice.vos.AccountVO;

public class AccountDTO {

	private static final Logger LOG = LoggerFactory.getLogger(AccountDTO.class); 
		
	
	AccountEntity accountEntity;
	AccountVO accountVO;

	public AccountDTO() {
		super();
	}

	public AccountDTO(AccountEntity accountEntity) {
		super();
		this.accountEntity = accountEntity;
		this.accountVO = transferEntity(accountEntity);
	}

	public AccountDTO(AccountVO accountVO) {
		super();
		this.accountVO = accountVO;
		this.accountEntity = transferVO(accountVO);
	}

	private AccountEntity transferVO(AccountVO accountVO) {
		if (accountVO.getAccountId() != null && !accountVO.getAccountId().equals("")) {
			AccountEntity accountEntity = new AccountEntity(accountVO.getAccountName(),
					accountVO.getAccountDescription(), accountVO.getAccountId());
			LOG.info("transfer accountVO {} -> to accountEntity {} ", accountVO, accountEntity);
			return accountEntity;
		} else {
			AccountEntity accountEntity = new AccountEntity(accountVO.getAccountName(), accountVO.getAccountDescription());
			LOG.info("transfer accountVO {} -> to accountEntity {} ", accountVO, accountEntity);
			return accountEntity;
		}
	}

	private AccountVO transferEntity(AccountEntity accountEntity) {
		return new AccountVO(accountEntity.getHostReference(), accountEntity.getAccountName(),
				accountEntity.getDescription());
	}

	public AccountEntity getAccountEntity() {
		return accountEntity;
	}

	public void setAccountEntity(AccountEntity accountEntity) {
		this.accountEntity = accountEntity;
	}

	public AccountVO getAccountVO() {
		return accountVO;
	}

	public void setAccountVO(AccountVO accountVO) {
		this.accountVO = accountVO;
	}

}
