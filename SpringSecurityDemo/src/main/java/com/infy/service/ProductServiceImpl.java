package com.infy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.infy.dtos.ProductDTO;
import com.infy.entity.Product;
import com.infy.entity.UserInfo;
import com.infy.exception.ProductException;
import com.infy.repos.ProductRepository;
import com.infy.repos.UserInfoRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	private ModelMapper modelMapper;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public ProductDTO saveProduct(ProductDTO productDTO) {

		Product product = mapToEntity(productDTO);
		product.setCreatedAt(LocalDateTime.now());
		product.setUpdatedAt(LocalDateTime.now());
		Product savedProduct = productRepository.save(product);
		return mapToDto(savedProduct);
	}

	@Override
	public ProductDTO getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductException("Product with ID '" + id + "' not found."));
		return mapToDto(product);
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		List<Product> findAll = productRepository.findAll();
		return findAll.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductException("Product with ID '" + id + "' not found."));
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());

		return mapToDto(productRepository.save(product));
	}

	@Override
	public String deleteProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductException("Product with ID '" + id + "' not found."));
		productRepository.deleteById(id);
		return "Product with" + id + " deleted successfully";
	}

	private ProductDTO mapToDto(Product product) {

		return modelMapper.map(product, ProductDTO.class);
	}

	private Product mapToEntity(ProductDTO productDTO) {

		return modelMapper.map(productDTO, Product.class);
	}
	
	

	@Override
	public String addUser(UserInfo userInfo) {
		
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		userInfoRepository.save(userInfo);
		return "User added successfully";
	}

}
