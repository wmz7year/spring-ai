/*
 * Copyright 2023 - 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.ai.autoconfigure.bedrock.api;

import org.springframework.ai.autoconfigure.bedrock.BedrockAwsConnectionConfiguration;
import org.springframework.ai.autoconfigure.bedrock.BedrockAwsConnectionProperties;
import org.springframework.ai.autoconfigure.retry.SpringAiRetryAutoConfiguration;
import org.springframework.ai.bedrock.api.BedrockConverseApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.retry.support.RetryTemplate;

import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

/**
 * {@link AutoConfiguration Auto-configuration} for Bedrock Converse API.
 *
 * @author Wei Jiang
 * @since 1.0.0
 */
@AutoConfiguration(after = SpringAiRetryAutoConfiguration.class)
@EnableConfigurationProperties({ BedrockAwsConnectionProperties.class })
@ConditionalOnClass({ BedrockConverseApi.class, BedrockRuntimeClient.class, BedrockRuntimeAsyncClient.class })
@Import(BedrockAwsConnectionConfiguration.class)
public class BedrockConverseApiAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean({ BedrockRuntimeClient.class, BedrockRuntimeAsyncClient.class })
	public BedrockConverseApi bedrockConverseApi(BedrockRuntimeClient bedrockRuntimeClient,
			BedrockRuntimeAsyncClient bedrockRuntimeAsyncClient, RetryTemplate retryTemplate) {
		return new BedrockConverseApi(bedrockRuntimeClient, bedrockRuntimeAsyncClient, retryTemplate);
	}

}