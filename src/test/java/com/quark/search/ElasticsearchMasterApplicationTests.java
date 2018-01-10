package com.quark.search;

import com.quark.search.entity.Customer;
import com.quark.search.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchMasterApplicationTests {

	@Autowired
	private CustomerRepository repository;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void saveCustomers() {
		this.repository.save(new Customer("looper", "Smith"));
		this.repository.save(new Customer("andy", "Smith"));
	}

	@Test
	public void fetchAllCustomers() {
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		Iterable<Customer> all = this.repository.findAll();
		for (Customer customer : all) {
			System.out.println(customer);
		}
		System.out.println();
	}

	@Test
	public void fetchIndividualCustomers() {
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(this.repository.findByFirstName("Bob"));
		System.out.println(this.repository.findAll());
		System.out.println("Customers found with findByLastName('Smith'):");
	}

//	@Test
//	public void speedQuery(){
//		ElasticsearchTemplate elasticsearchTemplate = new ElasticsearchTemplate();
//		SearchQuery searchQuery = new NativeSearchQueryBuilder()
//				.withQuery(new BoolQueryBuilder())
//				.withFilter(new BoolQueryBuilder().must(new TermQueryBuilder("id", "AWDYpUL0X12NWDRh7iN6")))
//				.build();
//		Page<Customer> sampleEntities = elasticsearchTemplate.queryForPage(searchQuery,Customer.class);
//	}

//	@Test
//	public void speedQueryWithScanAndScroll(){
//		ElasticsearchTemplate elasticsearchTemplate = new ElasticsearchTemplate();
//		SearchQuery searchQuery = new NativeSearchQueryBuilder()
////				.withQuery(matchAllQuery())
//				.withIndices("test-index")
//				.withTypes("test-type")
//				.withPageable( PageRequest.of(0,1))
//				.build();
//		String scrollId = elasticsearchTemplate.scan(searchQuery,1000,false);
//		List<Customer> sampleEntities = new ArrayList<Customer>();
//		boolean hasRecords = true;
//		while (hasRecords){
//			Page<Customer> page = elasticsearchTemplate.scroll(scrollId, 5000L , new
//					ResultsMapper<Customer>()
//					{
//						@Override
//						public Page<Customer> mapResults(SearchResponse response) {
//							List<Customer> chunk = new ArrayList<Customer>();
//							for(SearchHit searchHit : response.getHits()){
//								if(response.getHits().getHits().length <= 0) {
//									return null;
//								}
//								Customer user = new Customer();
//								user.setId(searchHit.getId());
//								user.setMessage((String)searchHit.getSource().get("message"));
//								chunk.add(user);
//							}
//							return new PageImpl<Customer>(chunk);
//						}
//					});
//			if(page != null) {
//				sampleEntities.addAll(page.getContent());
//				hasRecords = page.hasNextPage();
//			}
//			else{
//				hasRecords = false;
//			}
//		}
//	}




}
