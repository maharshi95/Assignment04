import com.eMart.Application;
import com.eMart.model.Product;
import com.eMart.repo.ProductRepository;
import com.eMart.services.ProductService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by maharshigor on 11/07/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)   // 1
@SpringApplicationConfiguration(classes = Application.class)   // 2
@WebAppConfiguration   // 3
@IntegrationTest("server.port:0")   // 4
public class Test {

	@Autowired
	ProductRepository repository;

	@Before
	public void setUp() {
		Product product = new Product ();
		product.setProductCode ("pcode1");
		repository.save (product);
	}

}
