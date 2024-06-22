package com.infy.service;

import java.util.List;

import com.infy.dtos.ProductDTO;
import com.infy.entity.UserInfo;

public interface ProductService {
	
	public ProductDTO saveProduct(ProductDTO prodcutDTO);
	public ProductDTO getProductById(Long id);
	public List<ProductDTO> getAllProducts();
	public ProductDTO updateProduct(Long id, ProductDTO productDTO);
	public String deleteProductById(Long id);
	public String addUser(UserInfo userInfo);

}
