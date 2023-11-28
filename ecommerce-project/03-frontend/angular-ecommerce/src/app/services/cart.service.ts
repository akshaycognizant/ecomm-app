import { Injectable } from '@angular/core';
import { CartItem } from '../common/cart-item';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  cartItems: any[] = [];
  totalPrice: Subject<number> = new BehaviorSubject<number>(0);
  totalQuantity: Subject<number> = new BehaviorSubject<number>(0);

  //storage: Storage = sessionStorage;
  storage: Storage = localStorage;

  constructor() {
    let data = JSON.parse(this.storage.getItem('cartItems')!);
    if(data != null){
      this.cartItems = data;
    }
    this.computeCartTotals();
   }

  addToCart(theCartItem: CartItem){
    //check if we already have item in cart
    let alreadyExistInCart: boolean = false;
    let existingCartItem!: CartItem ;

    
    if(this.cartItems.length > 0){
      //find out item in the cart based on item id
      // for(let tempCartItem of this.cartItems){
      //   if(tempCartItem.id === theCartItem.id){
      //     existingCartItem = tempCartItem;
      //     break;
      //   }
      // }
      // this method can be written as Arrays.find method to do same funtionality
      // find method returns first element while condition matches then automatically breaks out of the loop
      existingCartItem = this.cartItems.find(tempCartItem => tempCartItem.id === theCartItem.id);

      //check if we found it
      alreadyExistInCart = (existingCartItem != undefined);
    }
    if(alreadyExistInCart){
      existingCartItem.quantity++;
    }
    else{
      this.cartItems.push(theCartItem);
    }
    //compute cart total price and total quantity
    this.computeCartTotals();
  }
  removeFromCart(theCartItem: CartItem){
     theCartItem.quantity--;
     if(theCartItem.quantity === 0){
      this.remove(theCartItem);
     }
     else{
      this.computeCartTotals();
     }
  }
  remove(theCartItem: CartItem) {
    //get index of item in the array
    const itemIndex = this.cartItems.findIndex(tempCartItem => tempCartItem.id === theCartItem.id);
    //if found, remove the item from the array at the diven index
    if(itemIndex > -1){
       this.cartItems.splice(itemIndex,1);
    }
    this.computeCartTotals();
  }

  computeCartTotals() {
     let totalPriceValue: number = 0;
     let totalQuantityValue: number = 0;
     for(let currentCartItem of this.cartItems){
      totalPriceValue += currentCartItem.quantity * currentCartItem.unitPrice;
      totalQuantityValue += currentCartItem.quantity;
     }

     //publish the new values, all subscriber will recieve the new data
     this.totalPrice.next(totalPriceValue); // next to publish the data
     this.totalQuantity.next(totalQuantityValue);
     
     this.logCartData(totalPriceValue,totalQuantityValue);

     //persist cart data
     this.persistCartItems();
  }
  logCartData(totalPriceValue: number, totalQuantityValue: number) {
    console.log('contents of cart');
    for(let cartItem of this.cartItems){
      const subTotalPrice = cartItem.quantity * cartItem.unitPrice;
      console.log(`name=${cartItem.name}, quantity=${cartItem.quantity}, unitPrice=${cartItem.unitPrice}, sub total price=${subTotalPrice}`)
    }

    console.log(`totalPrice=${totalPriceValue.toFixed(2)} , totalQuantityValue=${totalQuantityValue}`);
    console.log('------');
  }
  persistCartItems(){
    this.storage.setItem('cartItems', JSON.stringify(this.cartItems));
  }
  
}
