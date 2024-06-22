
package com.infy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.dtos.AuthRequest;
import com.infy.dtos.ProductDTO;
import com.infy.entity.UserInfo;
import com.infy.service.JwtService;
import com.infy.service.ProductService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	private ProductService productService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public ProductController(ProductService productService) {

		this.productService = productService;
	}

	@GetMapping("/welcome")
	public String getWelcomeMessage() {
		return "Welcome to Spring Security";
	}

	

    @PostMapping("/adduser")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo){
        return new ResponseEntity<String>(productService.addUser(userInfo),HttpStatus.CREATED);
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO){
		return new ResponseEntity<ProductDTO>(productService.saveProduct(productDTO), HttpStatus.CREATED);	
	}
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
		return new ResponseEntity<ProductDTO>(productService.getProductById(id), HttpStatus.OK);
    }
    
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOs = productService.getAllProducts();
        return new ResponseEntity<>(productDTOs, HttpStatus.OK);
    }
    
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
		return new ResponseEntity<>(productService.updateProduct(id, productDTO), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id){
		 productService.deleteProductById(id);
		 return new ResponseEntity<>(" Product deleted successfully!", HttpStatus.OK); 
    }
    
    @PostMapping("/authenticate")
    public String AuthenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    	
    	Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
    	if(authenticate.isAuthenticated()) {
    	return jwtService.generateToken(authRequest.getUsername());
    	}else {
    		throw new UsernameNotFoundException("Invalid user request!");
    	}
    	
    	
    }
    

}
