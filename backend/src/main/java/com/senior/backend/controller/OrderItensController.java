package com.senior.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.senior.backend.model.ItemLevel3;
import com.senior.backend.model.Order;
import com.senior.backend.model.OrderItens;
import com.senior.backend.model.ProductService;
import com.senior.backend.repository.IOrderItensRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orderitens")
public class OrderItensController {
	
	@Autowired
	private IOrderItensRepository orderItensRepository;
	
	@Autowired
	private ProductServiceController productServiceController;
	
	@Autowired
	private OrderController orderController;
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<OrderItens> list() {

		List<OrderItens> response = orderItensRepository.findAll();
		response.forEach(orderItens -> {
			setMaturityLevel3(orderItens);
		});

		return response;
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public OrderItens findById(@PathVariable("id") Integer id) {

		OrderItens response = orderItensRepository.returnById(id);
		
		return response;
	}
	
	@GetMapping("findorder/{order_item_id}")
	@ResponseStatus(HttpStatus.OK)
	public List<OrderItens> findByOrderId(@PathVariable("order_item_id") Integer id) {

		List<OrderItens> response = orderItensRepository.returnIdOrder(id);
		
		return response;
	}
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody OrderItens add(@RequestBody OrderItens newOrderItens) {
		
		return getData(newOrderItens);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Optional<OrderItens> update(@PathVariable("id") int param,
			@RequestBody OrderItens newDataOrderItens) {

		OrderItens current = orderItensRepository.findById(param).get();
		current.setItens(newDataOrderItens.getItens());
		current.setQuantityPurchased(newDataOrderItens.getQuantityPurchased());
		current.setOrder(newDataOrderItens.getOrder());
		
		orderItensRepository.save(current);

		return orderItensRepository.findById(param);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean delete(@PathVariable("id") int id) {
		orderItensRepository.deleteById(id);

		return !orderItensRepository.existsById(id);
	}

	private OrderItens getData(OrderItens obj) {
		OrderItens newObj = new OrderItens();
		newObj.setId(obj.getId());
		newObj.setQuantityPurchased(obj.getQuantityPurchased());
		
		Order order = orderController.findById(obj.getOrder().getId());
		newObj.setOrder(order);
		
		ProductService item = productServiceController.findById(obj.getItens().getId());
		newObj.setItens(item);
		orderItensRepository.save(newObj);
		orderController.update(obj.getOrder().getId(), order);
		
		return newObj;
	}
	
	private void setMaturityLevel3(OrderItens orderItens) {
		final String PATH = "localhost:8080/orderitens";

		ArrayList<String> headers = new ArrayList<String>();

		headers.add("Accept : application/json");

		headers.add("Content-type : application/json");

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		mapper.setSerializationInclusion(Include.NON_NULL);

		try {

			OrderItens clone = mapper.readValue(mapper.writeValueAsString(orderItens), OrderItens.class);

			clone.setLinks(null);
			
			ProductService itens = clone.getItens();
			Short quantityPurchased = clone.getQuantityPurchased();
			Order order = clone.getOrder();
			
			clone.setItens(itens);
			clone.setQuantityPurchased((short) 12);
			clone.setOrder(order);
			
			String jsonUpdate = mapper.writeValueAsString(clone);
			
			clone.setItens(itens);
			clone.setQuantityPurchased(quantityPurchased);
			clone.setOrder(order);		

			clone.setId(null);

			String jsonCreate = mapper.writeValueAsString(clone);

			orderItens.setLinks(new ArrayList<>());

			orderItens.getLinks().add(new ItemLevel3("GET", PATH, null, null));

			orderItens.getLinks().add(new ItemLevel3("GET", PATH + "/" + orderItens.getId(), null, null));

			orderItens.getLinks().add(new ItemLevel3("DELETE", PATH + "/" + orderItens.getId(), null, null));

			orderItens.getLinks().add(new ItemLevel3("POST", PATH, headers, jsonCreate));

			orderItens.getLinks().add(new ItemLevel3("PUT", PATH + "/" + orderItens.getId(), headers, jsonUpdate));

		} catch (JsonProcessingException e) {

			e.printStackTrace();

		}

	}

}
