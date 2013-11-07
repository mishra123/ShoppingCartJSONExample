package developers.ajax1.stores;

import java.math.BigDecimal;
import java.util.*;

/**
 * A very simple shopping Cart
 */
public class Cart {

  private HashMap<Item,Integer> contents;

  /**
   * Creates a new Cart instance
   */
  public Cart() {
    contents = new HashMap<Item,Integer>();
  }

  /**
   * Adds a named item to the cart
   * @param itemName The name of the item to add to the cart
   */
  public void addItem(String itemCode) {

    Catalog catalog = new Catalog();

    if (catalog.containsItem(itemCode)) {
      Item item = catalog.getItem(itemCode);

      int newQuantity = 1;
      if (contents.containsKey(item)) {
        Integer currentQuantity = contents.get(item);
        newQuantity += currentQuantity.intValue();
      }

      contents.put(item, new Integer(newQuantity));
    }
  }

  /**
   * Removes the named item from the cart
   * @param itemName Name of item to remove. if the item's quantity is more than 1, then it reduces the quantity by 1,
   * but if it is already equal to 1, then it directly remove the item form the cart.
   */
  public void removeItems(String itemCode) 
  {
      Catalog catalog = new Catalog();

      Item item = catalog.getItem(itemCode);
      int newQuantity = 1;
      
      if (contents.containsKey(item)) {
        Integer currentQuantity = contents.get(item);
      if(currentQuantity.intValue() > 1)
      {    
         newQuantity =currentQuantity.intValue()-1;
         contents.put(item, new Integer(newQuantity));
      }   
      else
      {
    contents.remove(new Catalog().getItem(itemCode));
  }
      }
  }
  /**
   * @return JSON representation of cart contents in the form of an String array. 
   */
public String toXml() {
    StringBuffer xml = new StringBuffer();
    xml.append("<?xml version=\"1.0\"?>\n");
    xml.append("<cart generated=\""+System.currentTimeMillis()+"\" total=\""+getCartTotal()+"\">\n");

    
    for (Iterator<Item> I = contents.keySet().iterator() ; I.hasNext() ; ) {
      Item item = I.next();
      int itemQuantity = contents.get(item).intValue();

      xml.append("<item code=\""+item.getCode()+"\">\n");
      xml.append("<name>");
      xml.append(item.getName());
      xml.append("</name>\n");
      xml.append("<quantity>");
      xml.append(itemQuantity);
      xml.append("</quantity>\n");
      xml.append("</item>\n");
    }
    
    xml.append("</cart>\n");
    return xml.toString();
  }

   public String toJSON() 
   {
    StringBuffer json = new StringBuffer();
    json.append("{");
    json.append("\"cart\"" + ":" + "{" + "\"generated\" : " +System.currentTimeMillis()+ ", \"total\":\"" +getCartTotal()+"\" ,");
      json.append("\"item\":" + "[");
    for (Iterator<Item> I = contents.keySet().iterator() ; I.hasNext() ; ) 
    {
      Item item = I.next();
      int itemQuantity = contents.get(item).intValue();
      json.append("{" + "\"code\" :\""+item.getCode()+"\",");
      json.append("\"name\":\"" + item.getName()+ "\",");
      json.append("\"quantity\":" + itemQuantity + "}");
      if (I.hasNext()){
          json.append(",");
      }
    }
      json.append("]");
      json.append("}}");
    return json.toString();
  }

  private String getCartTotal() {
    int total = 0;
    for (Iterator<Item> I = contents.keySet().iterator() ; I.hasNext() ; ) {
      Item item = I.next();
      int itemQuantity = contents.get(item).intValue();

      total += (item.getPrice() * itemQuantity);
    }

    return "$"+new BigDecimal(total).movePointLeft(2);
  }
}
