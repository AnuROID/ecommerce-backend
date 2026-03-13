package com.example.ecommerce.service;

import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.request.AddItemRequest;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImplementation implements CartService {

    private CartRepository cartRepository;
    private  CartItemService cartItemService;
    private  ProductService productService;
//    private Product product;
    public CartServiceImplementation(CartRepository cartRepository,CartItemService cartItemService,ProductService productService){
        this.cartItemService=cartItemService;
        this.cartRepository=cartRepository;
        this.productService=productService;
//        this.product=product;
    }
    @Override
    public Cart creatCart(User user) {
        Cart cart=new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(req.getProductId());

        CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
        if (isPresent==null){
            CartItem cartItem=new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);
            cartItem.setCart(cart);
            int price= req.getQuantity()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createdCartItem=cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }
        return "item added to cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        Cart cart=cartRepository.findByUserId(userId);

        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalItem=0;

        for (CartItem cartItem:cart.getCartItems()){
            totalPrice+=cartItem.getPrice();
            totalDiscountedPrice+=cartItem.getDiscountedPrice();
            totalItem+=cartItem.getQuantity();
        }
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setDiscount(totalPrice-totalDiscountedPrice);

        return cartRepository.save(cart);
    }
}
