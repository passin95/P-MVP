/*
 * Copyright 2017 JessYan
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
package com.passin.pmvp.di.scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import javax.inject.Scope;

/**
 * 框架建议将 Activity、Fragment、View 等页面当做一个个 Page 看待，
 * 使它们所持有的所有对象生命周期一致，
 * 这样能够拥有较大的代码自由度，方便对 M 层和 P 层进行复用。
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface PageScope {}
