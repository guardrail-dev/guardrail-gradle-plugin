package example;

import com.foobar.generated.pet_store_v3.pets.PetsClient;

public class TestMain {
    public static void main(String[] args) {
        PetsClient client = new PetsClient.Builder().build();
        client.listPets();
        System.out.println("hi");
    }
}