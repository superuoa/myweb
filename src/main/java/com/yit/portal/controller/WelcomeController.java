package com.yit.portal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.yit.portal.bean.Cart;
import com.yit.portal.bean.Product;
import com.yit.portal.bean.User;
import com.yit.portal.util.RestClient;

@RestController
public class WelcomeController {

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping("/dd")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "welcome test rollout 14";
	}

	@RequestMapping( value = "/", method = RequestMethod.GET)
	public ModelAndView query(@RequestParam(value="name", required=false) String name) {
		ModelAndView mv = new ModelAndView("list");
        
		RestClient rc = new RestClient();
		String result = rc.get("http://gateway-service.ball-prod:8080/getProduct");
		
		List<Product> list = Product.buildJsonToCallsObject(result);
		
        mv.addObject("list",list);
        
        return mv;
	}

	@RequestMapping( value = "/cart", method = RequestMethod.GET)
	public ModelAndView cart() {
		ModelAndView mv = new ModelAndView("listCart");
        
		RestClient rc = new RestClient();
		String result = rc.get("http://gateway-service.ball-prod:8080/getCart");
		
		List<Cart> list = Cart.buildJsonToCallsObject(result);
		
        mv.addObject("list",list);
        
        return mv;
	}
	
	@PostMapping("/addToCart")
	public String save(@RequestParam("id") String id) {
		
		Cart c = new Cart();
		c.setProductId(Integer.parseInt(id));
		
		Gson gson = new Gson();
		
		RestClient rc = new RestClient();
		String result = rc.post("http://gateway-service.ball-prod:8080/addToCart",gson.toJson(c));
		
        return result;
	}
	
	@PostMapping("/deleteItem")
	public String deleteItem(@RequestParam("product_id") String product_id) {
		
		Cart c = new Cart();
		c.setProductId(Integer.parseInt(product_id));
		
		Gson gson = new Gson();
		
		RestClient rc = new RestClient();
		String result = rc.post("http://gateway-service.ball-prod:8080/deleteItem",gson.toJson(c));
		
        return result;
	}
	
}
