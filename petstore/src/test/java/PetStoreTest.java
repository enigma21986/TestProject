import model.Category;
import model.Pet;
import model.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static helpers.PetStoreApiHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PetStoreTest {

    // "Кейс #1 - Добавление питомца"
    @Test
    public void shouldSuccessfullyCreatePet(){
        // Create new pet
        Pet pet = getPetResource();
        Pet createResponse = createPet(pet);

        // Check create response
        checkPetResponse(pet, createResponse);

        // Find pet by id
        Pet getByIdResponse = getPetById(createResponse.getId());

        // Check get by id response
        checkPetResponse(createResponse, getByIdResponse);
    }

    // Кейс #2 - Удаление питомца
    @Test
    public void shouldSuccessfullyDeletePet(){
        // Create new pet
        Pet pet = getPetResource();
        Pet createResponse = createPet(pet);

        // Check create response
        checkPetResponse(pet, createResponse);

        // Delete pet
        deletePet(createResponse.getId());

        // Ensure that pet was deleted
        failGetPetById(createResponse.getId());
    }

    private void checkPetResponse(Pet expected, Pet actual) {
        if (expected.getId() == null) {
            assertNotNull(actual.getId());
        } else {
            assertEquals(expected.getId(), actual.getId());
        }
        assertEquals(expected.getCategory(), actual.getCategory());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPhotoUrls(), actual.getPhotoUrls());
        assertEquals(expected.getTags(), actual.getTags());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    protected Pet getPetResource(){
        Category category = new Category(999, "categoryName");
        List<String> urls = Arrays.asList("url1", "url2");
        List<Tag> tags = Arrays.asList(
                new Tag(1,"tag1"),
                new Tag(2,"tag2")
        );
        Pet pet = new Pet(category, "doggie999", urls, tags, Pet.PetStatus.available);
        return pet;
    }
}
