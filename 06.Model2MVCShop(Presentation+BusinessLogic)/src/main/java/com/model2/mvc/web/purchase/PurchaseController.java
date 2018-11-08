package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@Controller
public class PurchaseController {

	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	//setter Method 구현 않음
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	//setter Method 구현 않음
	
	public PurchaseController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView(@RequestParam("prod_no") int prodNo) throws Exception{
		
		System.out.println(":: [PurchaseController]의 [addPurchaseView]실행");
		
		//==> Model(data) / View(jsp) 정보를 갖는 ModelAndView 생성
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", productService.getProduct(prodNo));
		modelAndView.setViewName("/purchase/addPurchaseView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/addPurchase.do")
	public ModelAndView addPurchase(@RequestParam("prodNo") int prodNo, @ModelAttribute("purchase") Purchase purchase, HttpSession session) throws Exception{
		
		System.out.println(":: [PurchaseController]의 [addPurchase]실행");
		
		//Business Logic
		purchase.setTranCode("1");
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		purchase.setBuyer((User)session.getAttribute("user"));
		
		System.out.println(":: [PurchaseController]의 [addPurchase]의 purchase : "+purchase);
		//==> Model(data) / View(jsp) 정보를 갖는 ModelAndView 생성
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product", productService.getProduct(prodNo));
		purchaseService.addPurchase(purchase);
		
		modelAndView.setViewName("/purchase/addPurchase.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/listPurchase.do")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search, @ModelAttribute("purchase") Purchase purchase,HttpServletRequest request, HttpSession session) throws Exception{
		
		System.out.println(":: [PurchaseController]의 [listPurchase]실행");
		
		purchase.setBuyer((User)session.getAttribute("user"));
		
		if (search.getCurrentPage()==0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		//Business Logic
		Map<String, Object> map = purchaseService.getPurchaseList(search, purchase.getBuyer().getUserId());
		
		Page resultPage = new Page(search.getCurrentPage(),((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(":: [ProductController]의 [listProduct]실행 => resultPage : "+resultPage);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.setViewName("/purchase/listPurchase.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchaseView.do")
	public ModelAndView updatePurchaseView(@RequestParam("tranNo") String tranNo) throws Exception{
		
		System.out.println(":: [PurchaseController]의 [updatePurchaseView]실행");
		
		System.out.println(tranNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("purchase", purchaseService.getPurchase(Integer.parseInt(tranNo)));
		modelAndView.setViewName("/purchase/updatePurchase.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/updatePurchase.do")
	public ModelAndView updatePurchase(@ModelAttribute("purchase") Purchase purchase) throws Exception{
		
		System.out.println(":: [PurchaseController]의 [updatePurchaseView]실행");
		
		ModelAndView modelAndView = new ModelAndView();
		purchaseService.updatePurchase(purchase);
		modelAndView.setViewName("/getPurchase.do?tranNo="+purchase.getTranNo());
		
		return modelAndView;
	}
	
	@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase(@RequestParam("tranNo") String tranNo) throws Exception{
		
		System.out.println(":: [PurchaseController]의 [getPurchase]실행");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/purchase/getPurchase.jsp");
		modelAndView.addObject("purchase", purchaseService.getPurchase(Integer.parseInt(tranNo)));
		
		
		
		return modelAndView;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
