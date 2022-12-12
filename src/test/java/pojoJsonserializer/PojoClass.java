package pojoJsonserializer;

import org.apache.juneau.annotation.Beanc;

public class PojoClass {
	//POJO--------------Plain Old Java Object
	
		private String name;
		private int price;
		private String color;
		private String[] sellerName;
			
		@Beanc(properties = "name,price,color,sellerName")
		public PojoClass(String name, int price, String color, String[] sellerName) {
			
			this.name = name;
			this.price = price;
			this.color = color; 
			this.sellerName = sellerName;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public String[] getSellerName() {
			return sellerName;
		}
		public void setSellerName(String[] sellerName) {
			this.sellerName = sellerName;
		}
		 


}
