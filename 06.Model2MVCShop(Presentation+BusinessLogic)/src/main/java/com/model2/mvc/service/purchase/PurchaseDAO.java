package com.model2.mvc.service.purchase;

import java.util.HashMap;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;

//==> �ǸŰ������� CRUD �߻�ȭ/ĸ��ȭ�� DAO Interface Definition
public interface PurchaseDAO {
	
	//SELECT ONE
	public  Purchase getPurchase(int tranNo) throws Exception;
	
	//SELECT LIST
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception;
	
	//SELECT LIST
	public HashMap<String,Object> getSaleList(Search searchVO) throws Exception;
	
	//INSERT
	public void addPurchase(Purchase purchase) throws Exception;
	
	//UPDATE
	public void updatePurchase(Purchase purchase) throws Exception;
	
	//UPDATE
	public void updateTranCode(Purchase purchase) throws Exception;
	
	//UPDATE
	public void updateTranCode2(Purchase purchase) throws Exception;

	//�Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	public int getTotalCount(User user) throws Exception;
	
	//�Խ��� currentPage Row ��  return 
	public String makeCurrentPageSql(String sql , Search search) throws Exception;
}