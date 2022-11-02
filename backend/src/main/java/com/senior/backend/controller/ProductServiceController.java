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
import com.senior.backend.enuns.Type;
import com.senior.backend.enuns.UnitMeasurement;
import com.senior.backend.model.ItemLevel3;
import com.senior.backend.model.ProductService;
import com.senior.backend.repository.IProductServiceRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/productservice")
public class ProductServiceController {

	@Autowired
	private IProductServiceRepository productServiceRepository;
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<ProductService> list() {

		List<ProductService> response = productServiceRepository.findAll();
		response.forEach(productService -> {
			setMaturityLevel3(productService);
		});

		return response;
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProductService findById(@PathVariable("id") Integer id) {

		ProductService response = productServiceRepository.returnById(id);
		
		return response;
	}
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody ProductService add(@RequestBody ProductService newProductService) {
		
		return getData(newProductService);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Optional<ProductService> update(@PathVariable("id") int param,
			@RequestBody ProductService newDataProductService) {

		ProductService current = productServiceRepository.findById(param).get();
		current.setName(newDataProductService.getName());
		current.setUnitMeasurement(newDataProductService.getUnitMeasurement());
		current.setQuantity(newDataProductService.getQuantity());
		current.setPrice(newDataProductService.getPrice());
		current.setType(newDataProductService.getType());
		
		productServiceRepository.save(current);

		return productServiceRepository.findById(param);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean delete(@PathVariable("id") int id) {
		productServiceRepository.deleteById(id);

		return !productServiceRepository.existsById(id);
	}

	private ProductService getData(ProductService obj) {
		ProductService newObj = new ProductService();
		newObj.setId(obj.getId());
		newObj.setName(obj.getName());
		if (obj.getUnitMeasurement() == null) {
			newObj.setUnitMeasurement(UnitMeasurement.UNDEFINED);
		} else {
			newObj.setUnitMeasurement(obj.getUnitMeasurement());
		}
		newObj.setPrice(obj.getPrice());
		newObj.setQuantity(obj.getQuantity());
		if (obj.getType() == null) {
			newObj.setType(Type.UNDEFINED);
		} else {
			newObj.setType(obj.getType());
		}

		return productServiceRepository.save(newObj);
	}
	
	private void setMaturityLevel3(ProductService productService) {
		final String PATH = "localhost:8080/productservice";

		ArrayList<String> headers = new ArrayList<String>();

		headers.add("Accept : application/json");

		headers.add("Content-type : application/json");

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule()); // ESTUDAR
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		mapper.setSerializationInclusion(Include.NON_NULL);

		try {

			ProductService clone = mapper.readValue(mapper.writeValueAsString(productService), ProductService.class);

			clone.setLinks(null);
			
			String name = clone.getName();
			UnitMeasurement unitMeasurement = clone.getUnitMeasurement();
			Integer quantity = clone.getQuantity();
			Float price = clone.getPrice();
			Type type = clone.getType();
			
			clone.setName("Different name");
			clone.setUnitMeasurement(unitMeasurement);
			clone.setQuantity(234);
			clone.setPrice((float) 123);
			clone.setType(type);
			
			String jsonUpdate = mapper.writeValueAsString(clone);

			clone.setName(name);
			clone.setUnitMeasurement(unitMeasurement);
			clone.setQuantity(quantity);
			clone.setPrice(price);
			clone.setType(type);

			clone.setId(null);

			String jsonCreate = mapper.writeValueAsString(clone);

			productService.setLinks(new ArrayList<>());

			productService.getLinks().add(new ItemLevel3("GET", PATH, null, null));

			productService.getLinks().add(new ItemLevel3("GET", PATH + "/" + productService.getId(), null, null));

			productService.getLinks().add(new ItemLevel3("DELETE", PATH + "/" + productService.getId(), null, null));

			productService.getLinks().add(new ItemLevel3("POST", PATH, headers, jsonCreate));

			productService.getLinks().add(new ItemLevel3("PUT", PATH + "/" + productService.getId(), headers, jsonUpdate));

		} catch (JsonProcessingException e) {

			e.printStackTrace();

		}

	}
}
