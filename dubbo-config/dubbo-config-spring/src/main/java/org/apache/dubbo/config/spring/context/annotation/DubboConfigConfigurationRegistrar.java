/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.config.spring.context.annotation;

import org.apache.dubbo.config.AbstractConfig;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import static org.apache.dubbo.config.spring.util.AnnotatedBeanDefinitionRegistryUtils.registerBeans;

/**
 * Dubbo {@link AbstractConfig Config} {@link ImportBeanDefinitionRegistrar register}, which order can be configured
 *
 * @see EnableDubboConfig
 * @see DubboConfigConfiguration
 * @see Ordered
 * @since 2.5.8
 * 实现 ImportBeanDefinitionRegistrar 接口，处理 @EnableDubboConfig 注解，注册相应的 DubboConfigConfiguration 到 Spring 容器中
 */
public class DubboConfigConfigurationRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        // 获得 @EnableDubboConfig 注解的属性
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableDubboConfig.class.getName()));

        // 获得 multiple 属性
        boolean multiple = attributes.getBoolean("multiple");

        // Single Config Bindings注册相应的 DubboConfigConfiguration 到 Spring 容器中
        registerBeans(registry, DubboConfigConfiguration.Single.class);

        if (multiple) { // Since 2.6.6 https://github.com/apache/dubbo/issues/3193
            registerBeans(registry, DubboConfigConfiguration.Multiple.class);
        }
    }

}
