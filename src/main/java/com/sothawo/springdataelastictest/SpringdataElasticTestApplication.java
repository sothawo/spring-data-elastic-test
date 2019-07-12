package com.sothawo.springdataelastictest;

import static java.util.Arrays.*;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeValidationException;
import org.elasticsearch.transport.Netty4Plugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.client.NodeClientFactoryBean;

@SpringBootApplication
public class SpringdataElasticTestApplication {

	private Node node;

	public static void main(String[] args) {
		SpringApplication.run(SpringdataElasticTestApplication.class, args);
	}

//	@PostConstruct
	private void startNode() throws NodeValidationException {

		String pathHome = "src/test/resources/test-home-dir";
		String pathData = "target/elasticsearchTestData";
		String clusterName = UUID.randomUUID().toString();

		node = new NodeClientFactoryBean.TestNode(Settings.builder().put("transport.type", "netty4")
				.put("http.type", "netty4").put("path.home", pathHome).put("path.data", pathData)
				.put("cluster.name", "elasticsearch").put("node.max_local_storage_nodes", 100).build(), asList(Netty4Plugin.class));

		node.start();
	}

//	@PreDestroy
	private void stopNode() throws IOException {
		node.close();
	}

}
