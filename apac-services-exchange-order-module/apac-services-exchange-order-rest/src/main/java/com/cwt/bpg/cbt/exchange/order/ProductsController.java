package com.cwt.bpg.cbt.exchange.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.bpg.cbt.exchange.order.model.Product;
import com.cwt.bpg.cbt.exchange.order.products.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "Exchange Order")
public class ProductsController
{
    @Autowired
    private ProductService service;

    @GetMapping(path = "/products/{countryCode}", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    @ResponseBody
    @ApiOperation(value = "Pulls list of products and vendors for a specific market")
    public ResponseEntity<List<Product>> getProducts(
            @PathVariable
            @ApiParam(value = "Country code of the requested market") 
            String countryCode) {
        return new ResponseEntity<>(service.getProducts(countryCode), HttpStatus.OK);
    }

}
