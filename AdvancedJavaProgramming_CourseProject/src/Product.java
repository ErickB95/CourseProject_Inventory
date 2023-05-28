import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Product {

	String pName;
	String pType;
	int quantity;
	LocalDateTime dateArrived;
	LocalDateTime dateExpired;

	Product(String pName, String pType, int quantity) {
		this.pName = pName;
		this.pType = pType;
		this.quantity = quantity;
		this.dateArrived = LocalDateTime.now();
		this.dateExpired = LocalDateTime.now().plusHours(8760);
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpType() {
		return pType;
	}

	public void setpType(String pType) {
		this.pType = pType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateArrived, dateExpired, pName, pType, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(dateArrived, other.dateArrived) && Objects.equals(dateExpired, other.dateExpired)
				&& Objects.equals(pName, other.pName) && Objects.equals(pType, other.pType)
				&& quantity == other.quantity;
	}

	@Override
	public String toString() {
		return "Product [pName=" + pName + ", pType=" + pType + ", quantity=" + quantity + ", dateArrived="
				+ dateArrived + ", dateExpired=" + dateExpired + "]";
	}

}
