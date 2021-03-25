package solidTech;

public class Ingredient {
	private String name;
	private Double price;

	public Ingredient(String name, String price) {
		this.name = name;
		this.price = Double.valueOf(price.replaceAll(",", ""));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
