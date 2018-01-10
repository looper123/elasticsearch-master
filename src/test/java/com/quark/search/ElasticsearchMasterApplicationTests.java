package com.quark.search;

import com.quark.search.entity.Customer;
import com.quark.search.repository.CustomerRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		this.repository.save(new Customer("looper_2", "Smith_2"));
		this.repository.save(new Customer("andy", "Smith"));
//		elasticseach是不能对文档进行修改的 ，只能重建索引 或者 替换（当文档名、type、id相同时就会进行替换 这时候会返回当前数据的版本号（version））
//		this.repository.save(new Customer("1","name_f", "name_l"));
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

	@Test
	public void  speedQuery(){
		Optional<Customer> customer = repository.findById("AWDefR7kakmymxcaKPzi");
		Iterable<Customer> customers = repository.search(new NativeSearchQueryBuilder().withQuery(
//				这里的querybuilder都是以fieldname为查询条件
				new MatchAllQueryBuilder()).withQuery(new BoolQueryBuilder().must(new TermQueryBuilder("_index","customer"))).withQuery(new BoolQueryBuilder().must(new TermQueryBuilder("_type","customer"))).build());
		System.out.println("cutomers"+customer);

	}

	@Test
	public void speedQueryWithScanAndScroll() {
		List<String> ids = new ArrayList<>();
		ids.add("1");
		Page<Customer> customerPage = repository.search(new NativeSearchQueryBuilder().withQuery(new MatchAllQueryBuilder())
				.withIndices("customer")
				.withTypes("customer")
				.withIds(ids)
				.withPageable(PageRequest.of(2,1))
				.build());
		System.out.println("pagecustomer" + customerPage);
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
