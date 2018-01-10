/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.quark.search.repository;

import com.quark.search.entity.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {

	/**
	 * 通过and  or 。。关键词可以区分查询条件（spring data已经封装 参照spring-data-elasticsearch.pdf  p42-p43）
	 * @param firstName
	 * @return
     */
	public Customer findByFirstName(String firstName);

	public List<Customer> findByLastName(String lastName);


}
