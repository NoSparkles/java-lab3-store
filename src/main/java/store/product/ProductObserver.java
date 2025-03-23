package store.product;

public class ProductObserver {
    private static ProductObserver instance;
    private int productCount = 0;

    private ProductObserver() { }

    public static synchronized ProductObserver getInstance() {
        if (ProductObserver.instance == null) {
            ProductObserver.instance = new ProductObserver();
        }
        return ProductObserver.instance;
    }

    public void productAdded() {
        ++this.productCount;
    }

    public void resetProductCount() {
        this.productCount = 0;
    }
    public int getProductCount() {
        return this.productCount;
    }
}
