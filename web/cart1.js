// Timestamp of cart that page was last updated with
var lastCartUpdate = 0;

/*
 * Adds the specified item to the shopping cart, via Ajax call
 * itemCode - product code of the item to add. This method is called when "Add to Cart" button is pressed and call the servlet
 * to add the items into the cart.
 */
function addToCart(itemCode) {

 var req = newXMLHttpRequest();

 req.onreadystatechange = getReadyStateHandler(req, updateCart);
 
 req.open("POST", "cart123.do", true);
 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 req.send("action=add&item="+itemCode);
}
/*
 * Removes the specified item from the shopping cart, via Ajax call
 * itemCode - product code of the item. This method is called when "Remove Item" button is clicked. It deletes the specified item from the cart and ignores the action if the 
 * item is not avaliable in the cart. 
 */
function removeFromCart(itemCode)
{
 var req=newXMLHttpRequest(); 
 req.onreadystatechange = getReadyStateHandler(req, updateCart);
 
 req.open("POST", "cart123.do", true);
 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 req.send("action=remove&item="+itemCode);
 
}
/*
 * Update shopping-cart area of page to reflect contents of cart
 * described in JSON document. This function is responsible to update the data in the cart.
 */
function updateCart(cartXML)
{
    /**
     * 
     * @jsonParse(JSON String)is used to parse the JSON String. jsonParse(JSON) uses json_sans_eval library to parse 
     * the string which comes in the form of an array.
     */
    var jjson=jsonParse(cartXML);   
    var cart = jjson.cart;
 var generated = cart.generated;
 if (generated > lastCartUpdate)
 {
   lastCartUpdate = generated;
   var contents = document.getElementById("contents");
   contents.innerHTML = "";
   var items = cart.item;
   for (var I = 0 ; I < items.length ; I++) {
     var item = items[I];
     var name = item.name;
     var quantity = item.quantity;
     var listItem = document.createElement("li");
     listItem.appendChild(document.createTextNode(name+" x "+quantity));
     contents.appendChild(listItem);
   }
 }
 total.innerHTML = cart.total;
}

