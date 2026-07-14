package mx.edu.tecdesoftware.market_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MarketBackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void savesProductWithoutExplicitId() throws Exception {
		mockMvc.perform(post("/products/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "name": "Yogurt",
								  "categoryId": 2,
								  "price": 19.99,
								  "stock": 12,
								  "active": true
								}
								"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.productId", greaterThan(4)))
				.andExpect(jsonPath("$.price").value(19.99))
				.andExpect(jsonPath("$.category.categoryId").value(2));
	}

	@Test
	void savesPurchaseWithoutExplicitId() throws Exception {
		mockMvc.perform(post("/purchases/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "clientId": "CLI001",
								  "date": "2026-07-14T10:20:00",
								  "paymentMethod": "E",
								  "comment": "Nueva compra",
								  "state": "A",
								  "items": [
								    {
								      "productId": 2,
								      "quantity": 1,
								      "total": 14.0,
								      "active": true
								    }
								  ]
								}
				"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.purchaseId", greaterThan(1)))
				.andExpect(jsonPath("$.items[0].productId").value(2));
	}

	@Test
	void deletesReferencedProductWithoutForeignKeyFailure() throws Exception {
		mockMvc.perform(delete("/products/delete/1"))
				.andExpect(status().isOk());

		mockMvc.perform(get("/products/1"))
				.andExpect(status().isNotFound());

		mockMvc.perform(post("/purchases/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "clientId": "CLI001",
								  "date": "2026-07-14T10:20:00",
								  "paymentMethod": "E",
								  "comment": "Producto inactivo",
								  "state": "A",
								  "items": [
								    {
								      "productId": 1,
								      "quantity": 1,
								      "total": 18.0,
								      "active": true
								    }
								  ]
								}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400));
	}

	@Test
	void returnsNotFoundForMissingResources() throws Exception {
		mockMvc.perform(get("/products/999"))
				.andExpect(status().isNotFound());

		mockMvc.perform(get("/products/category/999"))
				.andExpect(status().isNotFound());

		mockMvc.perform(get("/purchases/client/NOPE"))
				.andExpect(status().isNotFound());

		mockMvc.perform(delete("/products/delete/999"))
				.andExpect(status().isNotFound());
	}

	@Test
	void returnsBadRequestForInvalidRequests() throws Exception {
		mockMvc.perform(get("/products/not-a-number"))
				.andExpect(status().isBadRequest());

		mockMvc.perform(post("/products/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "name": "Producto sin categoria",
								  "categoryId": 999,
								  "price": 10.0,
								  "stock": 1,
								  "active": true
								}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400));

		mockMvc.perform(post("/purchases/save")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "clientId": "NOPE",
								  "date": "2026-07-14T10:20:00",
								  "paymentMethod": "E",
								  "comment": "Cliente inexistente",
								  "state": "A",
								  "items": [
								    {
								      "productId": 1,
								      "quantity": 1,
								      "total": 18.0,
								      "active": true
								    }
								  ]
								}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400));
	}

}
