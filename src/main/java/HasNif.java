/**
 * Interface representing an entity that possesses a tax identification number
 * (NIF).
 * This can be applicable to both clients and drivers.
 */
public interface HasNif {
    /**
     * Retrieves the NIF (Tax Identification Number) of the client.
     *
     * @return The client's NIF as an int.
     */
    int getClientNif();

    /**
     * Retrieves the NIF (Tax Identification Number) of the driver.
     *
     * @return The driver's NIF as an int.
     */
    int getDriverNif();
}
