package store;

public class PrimaryControllerState {
    // Private static instance
    private static PrimaryControllerState instance;

    // Instance variable for state management
    private boolean isAdmin = false; // Default state

    // Private constructor to prevent instantiation
    private PrimaryControllerState() {}

    // Public method to get the single instance
    public static PrimaryControllerState getInstance() {
        if (PrimaryControllerState.instance == null) {
            PrimaryControllerState.instance = new PrimaryControllerState();
        }
        return PrimaryControllerState.instance;
    }

    // Getter and Setter for isAdmin
    public boolean isAdmin() {
        return this.isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }
}
