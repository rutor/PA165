package cz.fi.muni.pa165.tasks;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

 
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;
        
        Category electro;
        Category kitchen;

        Product flashlight;
        Product kitchenRobot;
        Product plate;

	
	private void assertContainsCategoryWithName(Set<Category> categories,
			String expectedCategoryName) {
		for(Category cat: categories){
			if (cat.getName().equals(expectedCategoryName))
				return;
		}
			
		Assert.fail("Couldn't find category "+ expectedCategoryName+ " in collection "+categories);
	}
	private void assertContainsProductWithName(Set<Product> products,
			String expectedProductName) {
		
		for(Product prod: products){
			if (prod.getName().equals(expectedProductName))
				return;
		}
			
		Assert.fail("Couldn't find product "+ expectedProductName+ " in collection "+products);
	}
        
        @BeforeClass
        public void prepare() {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            electro = new Category();
            electro.setName("Electro");
            kitchen = new Category();
            electro.setName("Kitchen");
            
            flashlight = new Product();
            kitchenRobot = new Product();
            plate = new Product();
            
            flashlight.setName("Flashlight");
            kitchenRobot.setName("Kitchen robot");
            plate.setName("Plate");
            
            em.persist(electro);
            em.persist(kitchen);
            em.persist(flashlight);
            em.persist(kitchenRobot);
            em.persist(plate);
            
            electro.addProduct(flashlight);
            electro.addProduct(kitchenRobot);
            
            kitchen.addProduct(kitchenRobot);
            kitchen.addProduct(plate);
            
            em.getTransaction().commit();
            em.close();
            
            //In each test create a new entity manager, search for the entity and assert that it has correct content of e.g. java.util.Set.
            
        }
        
        @Test
        public void testElectro() {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Category cat = em.find(Category.class, electro.getId());
            assertContainsProductWithName(cat.getProducts(), flashlight.getName());
            assertContainsProductWithName(cat.getProducts(), kitchenRobot.getName());
            em.getTransaction().commit();
            em.close();
        }

	@Test
        public void testKitchen() {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Category cat = em.find(Category.class, kitchen.getId());
            assertContainsProductWithName(cat.getProducts(), plate.getName());
            assertContainsProductWithName(cat.getProducts(), kitchenRobot.getName());
            em.getTransaction().commit();
            em.close();
        }
        
        @Test
        public void testFlashlight() {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Product prod = em.find(Product.class, flashlight.getId());
            assertContainsCategoryWithName(prod.getCategories(), electro.getName());
            em.getTransaction().commit();
            em.close();
        }
        
        @Test
        public void testPlate() {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Product prod = em.find(Product.class, plate.getId());
            assertContainsCategoryWithName(prod.getCategories(), kitchen.getName());
            em.getTransaction().commit();
            em.close();
        }
        
        @Test
        public void testKitchenRobot() {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Product prod = em.find(Product.class, kitchenRobot.getId());
            assertContainsCategoryWithName(prod.getCategories(), electro.getName());
            assertContainsCategoryWithName(prod.getCategories(), kitchen.getName());
            em.getTransaction().commit();
            em.close();
        }
}
