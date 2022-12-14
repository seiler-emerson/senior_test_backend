package com.senior.backend.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import com.senior.backend.enuns.Type;
import com.senior.backend.model.ItemLevel3;
import com.senior.backend.model.Order;
import com.senior.backend.model.OrderItens;
import com.senior.backend.repository.IOrderRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	@Lazy
	private OrderItensController orderItemController;

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<Order> list() {

		List<Order> response = orderRepository.findAll();
		response.forEach(order -> {
			setMaturityLevel3(order);
		});

		return response;
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Order findById(@PathVariable("id") UUID id) {

		Order response = orderRepository.returnById(id);

		return response;
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Order add(@RequestBody Order newOrder) {

		return getData(newOrder);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Optional<Order> update(@PathVariable("id") UUID param, @RequestBody Order newDataOrder) {

		Order current = orderRepository.findById(param).get();
		current.setDiscount(newDataOrder.getDiscount());
		current.setDate(newDataOrder.getDate());

		List<OrderItens> listItens = new ArrayList<>();

		listItens = orderItemController.findByOrderId(param);
		current.setListItens(listItens);
		System.out.println("LISTA: " + listItens.get(0).getItens().getPrice());

		Float valorTotal = 0.0f;
		Float discount = current.getDiscount() / 100;

		for (Integer count = 0; count < listItens.size(); count++) {
			if (listItens.get(count).getItens().getType() == Type.PRODUCT) {
				Float priceDiscount = listItens.get(count).getItens().getPrice() * (1 - discount)
						* listItens.get(count).getQuantityPurchased();
				valorTotal = (valorTotal + priceDiscount);
			} else {
				Float priceNoDiscount = listItens.get(count).getItens().getPrice()
						* listItens.get(count).getQuantityPurchased();
				valorTotal = (valorTotal + priceNoDiscount);
			}
			current.setAmount(valorTotal);
		}

		orderRepository.save(current);

		return orderRepository.findById(param);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean delete(@PathVariable("id") UUID id) {
		orderRepository.deleteById(id);

		return !orderRepository.existsById(id);
	}

	private Order getData(Order obj) {
		Order newObj = new Order();
		newObj.setId(obj.getId());
		newObj.setDiscount(obj.getDiscount());
		if (obj.getDate() == null) {
			newObj.setDate(LocalDateTime.now());
		}
		newObj.setListItens(obj.getListItens());

		return orderRepository.save(newObj);
	}

	private void setMaturityLevel3(Order order) {
		final String PATH = "localhost:8080/order";

		ArrayList<String> headers = new ArrayList<String>();

		headers.add("Accept : application/json");

		headers.add("Content-type : application/json");

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule()); // ESTUDAR
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		mapper.setSerializationInclusion(Include.NON_NULL);

		try {

			Order clone = mapper.readValue(mapper.writeValueAsString(order), Order.class);

			clone.setLinks(null);

			Float discount = clone.getDiscount();
			Float amount = clone.getAmount();
			LocalDateTime date = clone.getDate();
			List<OrderItens> listItens = clone.getListItens();

			clone.setDiscount((float) 12.00);
			clone.setAmount((float) 1234);
			clone.setDate(LocalDateTime.of(2022, 12, 11, 12, 00));
			clone.setListItens(listItens);

			String jsonUpdate = mapper.writeValueAsString(clone);

			clone.setDiscount(discount);
			clone.setAmount(amount);
			clone.setDate(date);
			clone.setListItens(listItens);

			clone.setId(null);

			String jsonCreate = mapper.writeValueAsString(clone);

			order.setLinks(new ArrayList<>());

			order.getLinks().add(new ItemLevel3("GET", PATH, null, null));

			order.getLinks().add(new ItemLevel3("GET", PATH + "/" + order.getId(), null, null));

			order.getLinks().add(new ItemLevel3("DELETE", PATH + "/" + order.getId(), null, null));

			order.getLinks().add(new ItemLevel3("POST", PATH, headers, jsonCreate));

			order.getLinks().add(new ItemLevel3("PUT", PATH + "/" + order.getId(), headers, jsonUpdate));

		} catch (JsonProcessingException e) {

			e.printStackTrace();

		}

	}

}
