package com.yit.portal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping("/")
	public List<Product> welcome(Map<String, Object> model) {
		List<Product> list = new ArrayList<Product>();
		
		Product p = new Product();
		p.setId(1);
		p.setDescription("RAM: 4, CPU: Intel Core i7-10750H");
		p.setInstock(1);
		p.setName("Notebook DELL");
		p.setInstock(10);
		
		Product p2 = new Product();
		p2.setId(2);
		p2.setDescription("Smart TV TU7000");
		p2.setName("SAMSUNG Crystal UHD 4K");
		p2.setInstock(5);
		
		Product p3 = new Product();
		p3.setId(3);
		p3.setDescription("14976 BTU, Inverter");
		p3.setName("HAIER Air Conditioning");
		p3.setInstock(2);
		
		list.add(p);
		list.add(p2);
		list.add(p3);
		
		
		return list;
	}
	
	@GetMapping("/getProduct/{id}")
    public Product getById(@PathVariable int id) {
		Product p = new Product();
		p.setId(1);
		p.setDescription("RAM: 4, CPU: Intel Core i7-10750H");
		p.setInstock(1);
		p.setName("Notebook DELL");
		p.setInstock(10);
		return p;
	}

	@RequestMapping( value = "/getProduct", method = RequestMethod.GET)
	public ModelAndView query(@RequestParam(value="name", required=false) String name) {
		ModelAndView mv = new ModelAndView("list");
        
		RestClient rc = new RestClient();
		//String result = rc.get("http://gateway-service.ball-prod:8080/getProduct");
		String result = rc.get("http://gateway:8080/getProduct");
		List<Product> list = Product.buildJsonToCallsObject(result);
		
        mv.addObject("list",list);
        
        return mv;
	}

	@RequestMapping( value = "/cart", method = RequestMethod.GET)
	public ModelAndView cart() {
		ModelAndView mv = new ModelAndView("listCart");
        
		RestClient rc = new RestClient();
		//String result = rc.get("http://gateway-service.ball-prod:8080/getCart");
		String result = rc.get("http://gateway:8080/getCart");
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
		//String result = rc.post("http://gateway-service.ball-prod:8080/addToCart",gson.toJson(c));
		String result = rc.post("http://gateway:8080/addToCart",gson.toJson(c));
		
        return result;
	}
	
	@PostMapping("/deleteItem")
	public String deleteItem(@RequestParam("product_id") String product_id) {
		
		Cart c = new Cart();
		c.setProductId(Integer.parseInt(product_id));
		
		Gson gson = new Gson();
		
		RestClient rc = new RestClient();
		//String result = rc.post("http://gateway-service.ball-prod:8080/deleteItem",gson.toJson(c));
		String result = rc.post("http://gateway:8080/deleteItem",gson.toJson(c));
		
        return result;
	}
	
}
