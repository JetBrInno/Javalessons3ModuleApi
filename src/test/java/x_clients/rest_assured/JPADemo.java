package x_clients.rest_assured;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.spi.PersistenceUnitInfo;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import x_clients.rest_assured.jpa.entity.CompanyEntity;
import x_clients.rest_assured.jpa.manager.MyPUI;

public class JPADemo {

    private static EntityManager entityManager;

    @BeforeAll
    public static void setUp() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "env.properties";
        Properties properties = new Properties();
        properties.load(new FileInputStream(appConfigPath));

        PersistenceUnitInfo myPUI = new MyPUI(properties);

        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        EntityManagerFactory entityManagerFactory = hibernatePersistenceProvider.createContainerEntityManagerFactory(myPUI, myPUI.getProperties());
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    public void getCompanyFromDB() {
        CompanyEntity company = entityManager.find(CompanyEntity.class, 542);
        System.out.println(company);
    }

    @Test
    public void getCompanyFromDBByName() {
        String name = "bars";
        TypedQuery<CompanyEntity> query = entityManager
            .createQuery("SELECT ce FROM CompanyEntity ce WHERE ce.name = :name", CompanyEntity.class);
        query.setParameter("name", name);
        CompanyEntity singleResult = query.getSingleResult();
        System.out.println(singleResult);
    }

    @Test
    public void createNewCompany() {
        CompanyEntity company = new CompanyEntity();
        company.setName("Объект");
        company.setDescription("Описание объекта");
        company.setActive(true);

        entityManager.getTransaction().begin();
        entityManager.persist(company);
        entityManager.getTransaction().commit();

        System.out.println(company);
    }

    @Test
    public void deleteNewCompany() {
        CompanyEntity company = new CompanyEntity();
        company.setName("Объект");
        company.setDescription("Описание объекта");
        company.setActive(true);
        entityManager.getTransaction().begin();
        entityManager.persist(company);
        entityManager.getTransaction().commit();

        int id = company.getId();
        assertNotNull(entityManager.find(CompanyEntity.class, id));

        entityManager.getTransaction().begin();
        entityManager.remove(company);
        entityManager.getTransaction().commit();

        assertNull(entityManager.find(CompanyEntity.class, id));
    }
}
